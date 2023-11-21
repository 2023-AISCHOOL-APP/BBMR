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
            menu[item['menu_id']] = [item['name'],item['price'],item['menu_con'],item['size']]
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



   



# 학습된 모델 로드
model = tf.keras.models.load_model('test_face_cnn_model.h5')

################################################################################

### 1번
def preprocess_image(image): # 231120 -- 이미지 전처리를 위한 코드 수정
    # 이미지를 (360, 480) 크기로 재조정하고 채널을 3으로 설정
    img = image.resize((480, 360))
    img_array = np.array(img)

    # 이미지 배열이 (360, 480, 3) 형태인지 확인
    if img_array.shape != (360, 480, 3):
        raise ValueError("이미지의 차원이 (360, 480, 3)이 아닙니다.")

    img_array = img_array / 255.0  # 픽셀 값을 [0, 1] 범위로 정규화
    img_array = np.expand_dims(img_array, axis=0)  # 모델 입력을 위한 차원 추가
    return img_array

@app.route('/upload_and_predict', methods=['POST'])  ## 231120 해당 부분은 RetrofitAPI에 POST와 같은 경로로 설정해야함
def upload_and_predict():
    # 클라이언트로부터 이미지 파일 받기
    if 'image' not in request.files:
        return jsonify({'error': 'No image part'})

    image_file = request.files['image']
    image_file.seek(0)  # 231120 스트림 위치 초기화 --> 이미지 파일을 읽기 위해
    
    # 이미지를 PIL Image로 변환
    image = Image.open(io.BytesIO(image_file.read())).convert("RGB")
    
    # 전처리
    preprocessed_image = preprocess_image(image)

    # 모델 예측
    input_array = preprocessed_image # np.array([np.array(preprocessed_image)]) -- 231120 코드 수정 : 필요한 형태로 전처리 되어 있어 배열 형태 코드를 사용x
    predictions = model.predict(input_array)

    # 결과 전송
    result = {'prediction': predictions.tolist()}
    return jsonify(result)



api.add_resource(checkCoupon,"/checkcoupon/")
api.add_resource(TodoList,'/todos/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 



