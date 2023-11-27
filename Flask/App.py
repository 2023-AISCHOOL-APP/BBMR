import datetime
from datetime import datetime
from io import BytesIO
import io
# from tkinter import Image
from PIL import Image # 2번 Image 모듈이 tkinter 패키지에 포함되어 3번으로 수정
from connection import get_connection
from mysql.connector import Error
from flask import Flask, request, jsonify, send_file
from flask_restful import Resource, Api, reqparse, abort
from tensorflow import keras
from keras import models, layers
from keras.layers import Dense
from keras.preprocessing import image
from keras.applications.vgg16 import preprocess_input
from werkzeug.utils import secure_filename # 231116 filename불러오기 위한 import
import joblib # 231115 모델 로딩 라이브러리 사용 - 정희석(8-12)
import tensorflow as tf
import numpy as np
import os # 231116 추가 - 이미지를 PIL Image로 변환

app = Flask(__name__)
api = Api(app)


# DB에서 메뉴정보 가져오기
class TodoList(Resource):
    def fetch_menu(self, category):
        conn = get_connection()
        query = f"SELECT * FROM menu WHERE category = '{category}'"
        cursor = conn.cursor(dictionary=True)
        cursor.execute(query)
        db_result = cursor.fetchall()
        menu = {}
        for item in db_result:
            menu[item['menu_id']] = [item['name'],item['price'],item['menu_con'],item['size'],item['imageUrl']]
        return menu

    def get(self):
        coffee = self.fetch_menu('coffee')
        dessert = self.fetch_menu('dessert')
        tea = self.fetch_menu('tea')
        md = self.fetch_menu('md')
        flatccino = self.fetch_menu('flatccino')
        beverage = self.fetch_menu('beverage')
        etc = self.fetch_menu('etc')

        return {"coffee": coffee, "dessert": dessert, "etc": etc,
                "tea":tea, "md":md, "flatccino": flatccino,"beverage" : beverage}



# 입력한 쿠폰 사용가능여부 / 사용가능 할 때 교환권, 금액권 구별하고 금액권이면 남은 금액, 교환권이면 해당 음료정보 리턴
class checkCoupon(Resource):
    def post(self):
        # 입력한 코드 받아와서 해당 코드에 대한 정보 DB에서 가져오기
        coupon_code = request.form['coupon'] 
        conn = get_connection()
        cursor = conn.cursor()
        cursor.execute(f"select * from coupon where coupon_code='{coupon_code}'")
        couponList = cursor.fetchall()
        # 쿠폰 입력할 때 날짜
        current_date = datetime.now().strftime('%Y-%m-%d')

        # 입력한 코드에 대한 정보가 DB에서 있을 때
        if couponList:  
            conn = get_connection()
            cursor = conn.cursor()
            cursor.execute(f"select menu_id, C_use, expiry_date, amount from coupon where coupon_code='{coupon_code}'")
            C_check = cursor.fetchall()[0]
            menu_id = C_check[0]
            C_use = C_check[1]
            expiry_date = C_check[2]
            expiry_date = expiry_date.strftime('%Y-%m-%d')
            amount = C_check[3]
            conn.close()

            # 사용기한 체크
            if current_date <= expiry_date:
                # 금액권 쿠폰일때(교환권 쿠폰은 amount = 0)
                if amount > 0 :
                    return {"result": amount}
                elif amount == 0 and menu_id is None:
                    return {"result" : "잔액이 없습니다."}
                else:
                    # 사용가능 할 때 return 해줄 result 값 DB에서 가져오기 
                    conn = get_connection()
                    cursor = conn.cursor()
                    cursor.execute(f"select menu_id, name, price, menu_con, size from menu where menu_id='{menu_id}'")
                    result = cursor.fetchall()[0]
                    conn.close()
                    # 사용여부 체크 C_use==0 이면 사용가능
                    if C_use==0:
                        return {"result" : result}
                    else:
                        return {"result" : "이미 사용한 쿠폰입니다."}
            else:
                return {"result" : "사용기한이 지났습니다."}    
            
        # 쿠폰번호를 잘못입력했거나 없을때(입력한 코드에 대한 정보가 DB에 없을때)           
        else:
            result = "잘못 입력 또는 없는 쿠폰입니다."
            return {"result": result}




# 결제 완료 후 DB 저장 / 주문번호 안드로이드로 보내기
class SaveOrder(Resource):
    def post(self):
        try:
            # 안드로이드 앱에서 전송한 JSON 데이터를 받음
            data = request.get_json()
            print(data)
            # 메뉴리스트[(메뉴id,수량),(메뉴id,수량)...], 결제금액
            menu_ids = data['menu_ids']
            total_amount = data['total_amount']

            conn = get_connection()
            cursor = conn.cursor()
            # Orders 테이블에 주문 정보 삽입(order_id(주문번호)는 자동생성), 총결제금액(쿠폰사용금액 제외)
            cursor.execute("INSERT INTO orders (total_amount) VALUES (%s)", (total_amount,))
            conn.commit()

            # 삽입한 주문의 id값을 가져온다
            order_id = cursor.lastrowid 
            # order_detail테이블에 order_id, 메뉴id, 수량을 저장
            for menu_id in menu_ids:
                cursor.execute("INSERT INTO order_detail (order_id, menu_id, quantity) VALUES (%s, %s, %s)",
                            (order_id, menu_id['menu_id'], menu_id['quantity']))
                conn.commit()
            conn.close()

            # 쿠폰을 썼을때 JSON데이터에 coupon 값이 null이 아닌 값이 있을때
            if 'coupon' in data:
                # 쿠폰코드와 할인금액을 가져온다(할인금액은 금액권이나 교환권이나 상관없이 금액으로)
                coupon_code = data['coupon']
                discount = data['discount']

                # 쿠폰코드에 해당되는 쿠폰정보를 가져옴
                conn = get_connection()
                cursor = conn.cursor()
                cursor.execute("SELECT menu_id, C_use, amount FROM coupon WHERE coupon_code = %s", (coupon_code,))
                coupon_data = cursor.fetchall()[0]
                menu_id, C_use, amount = coupon_data
                # 쿠폰의 amount(금액)이 0이고 메뉴id가 있을때 (교환권)
                if amount ==0 and menu_id is not None:
                    conn = get_connection()
                    cursor = conn.cursor()
                    cursor.execute(f"update coupon set C_use = 1 where coupon_code = '{coupon_code}'")
                    conn.commit()
                # 금액권
                else:
                    amount_result = amount-discount
                    print(amount_result)
                    conn = get_connection()
                    cursor = conn.cursor()
                    cursor.execute("UPDATE coupon SET amount = %s WHERE coupon_code = %s", (amount_result, coupon_code))
                    conn.commit()
                conn.close()

            
            response = {"response":order_id}
            
            return {"response":order_id}

        except Exception as e:
            print(str(e))
            response = jsonify({"error": "Error saving order"})
            
            return response

   



# 학습된 모델 로드
# 231121
model = tf.keras.models.load_model('test_face_cnn_model.h5')
@app.route('/upload', methods=['POST'])
def upload_file():
    if 'image' not in request.files:
        return jsonify({'error': 'No image part'}), 400

    file = request.files['image']
    print(file)

    if file:
        # 이미지 읽기 및 리사이즈
        image = Image.open(io.BytesIO(file.read()))
        image = image.rotate(90, expand=True)

        # RGBA에서 RGB로 변환
        image = image.convert('RGB')
        
        print("image -> ",image)
        image.save('image/image1.png')
        image = image.resize((480, 360))  # 너비 480, 높이 360으로 리사이즈
        image.save('image/image2.png')
        # 필요한 추가 전처리 과정
        # 예시: 이미지를 numpy 배열로 변환
        image = np.array(image)
        image = image / 255.0  # 정규화
        image = np.expand_dims(image, axis=0)  # 모델 입력 형태에 맞게 차원 확장

        # 모델 예측
        prediction = model.predict(image)
        print("prediction -> " , prediction[0][0])

        # 예측 결과 처리 및 반환
        # 예시: 예측 결과의 최대값 인덱스 반환
        if prediction[0][0] < 0.5:
            result = 0
        else:
            result = 1
        # result = 0
        print("result ->", result)
        return {'result': result}


api.add_resource(SaveOrder,"/saveorder/")
api.add_resource(checkCoupon,"/checkcoupon/")
api.add_resource(TodoList,'/todos/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 



