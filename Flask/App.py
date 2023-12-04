import datetime
from datetime import datetime
from io import BytesIO
import io
import secrets
from tkinter import Image
from PIL import Image # 2번 Image 모듈이 tkinter 패키지에 포함되어 3번으로 수정
from connection import get_connection
from mysql.connector import Error
from flask import Flask, redirect, render_template, request, jsonify, send_file, session, url_for
from flask_restful import Resource, Api, reqparse, abort
from tensorflow import keras
from keras import models, layers
from keras.layers import Dense
from keras.preprocessing import image
from keras.applications.vgg16 import preprocess_input
import tensorflow as tf
import cv2
from deepface import DeepFace # 라이브러리 가져오기
from werkzeug.utils import secure_filename # 231116 filename불러오기 위한 import
import joblib # 231115 모델 로딩 라이브러리 사용 - 정희석(8-12)
import numpy as np
import os # 231116 추가 - 이미지를 PIL Image로 변환

app = Flask(__name__)
api = Api(app)
# 시크릿 키
app.secret_key = secrets.token_hex(16)




# 메뉴, 쿠폰 입력, 매출 확인버튼 페이지
@app.route('/', methods=['GET','POST'])
def home():
    return render_template('home.html')
    
# 관리자 로그인(임시로 id는 admin, password는 1234로 설정 해둠)
@app.route('/login', methods=['GET','POST'])
def login():
    id = request.form.get('name')
    password = request.form.get('password')
    print(id, password)
    if id == 'admin' and password == '1234':  
        session['logged_in'] = True
        return render_template('select.html')
    else:
        message = "ID 또는 Password를 잘못 입력하셨습니다."
        return render_template('home.html',message=message)
    
# 로그아웃
@app.route('/logout')
def logout():
    # 세션 제거
    session.pop('logged_in', None)
    return redirect(url_for('home'))

# 선택창(추가 or 조회)
@app.route('/select')
def select():
    # 세션을 확인하여 로그인 여부를 확인
    if 'logged_in' in session:
        return render_template('select.html')
    else:
        # 로그인되지 않은 사용자는 홈페이지로 리다이렉트
        return redirect(url_for('home'))
    
# 메뉴, 쿠폰 추가페이지
@app.route('/create')
def create():
    if 'logged_in' in session:
        return render_template('create.html')
    else:
        return redirect(url_for('home'))

# 메뉴, 쿠폰, 매출 조회페이지
@app.route('/search')
def search():
    if 'logged_in' in session:
        return render_template('search.html')
    else:
        return redirect(url_for('home'))

# 메뉴 조회 
@app.route('/menuSearch',  methods=['GET', 'POST'])
def menuSearch():
    if 'logged_in' in session:
        menu_search = request.form.get('menu_search')
        conn = get_connection()
        cursor = conn.cursor()        
        if menu_search == 'name':
            menu_name = request.form.get('menu_name')
            query = f"select * from menu where name = '{menu_name}'"
        elif menu_search == 'cate':
            cate = request.form.get('cate_search')
            query = f"select * from menu where category = '{cate}'"
        elif menu_search == 'all':
            query = "select * from menu"
        cursor.execute(query)
        menu = cursor.fetchall()
        return render_template('menuSearch.html',menu=menu)
    else:
        return redirect(url_for('home'))


# 쿠폰조회
@app.route('/couponSearch', methods=['GET', 'POST'])
def couponSearch():
    if 'logged_in' in session:
        search_type = request.form.get('search_type')
        if search_type == 'single':
            couponcode = request.form.get('couponcode')
            conn = get_connection()
            cursor = conn.cursor()
            result_query = f"SELECT C.coupon_code, C.expiry_date, M.name, C.C_use, C.amount FROM coupon C LEFT JOIN menu M ON C.menu_id = M.menu_id where coupon_code='{couponcode}'"
            cursor.execute(result_query)
            coupon = cursor.fetchall()
            conn.close()
            return render_template('couponSearch.html', coupon=coupon)
        elif search_type == 'all':
            conn = get_connection()
            cursor = conn.cursor()
            result_query = "SELECT C.coupon_code, C.expiry_date, M.name, C.C_use, C.amount FROM coupon C LEFT JOIN menu M ON C.menu_id = M.menu_id"
            cursor.execute(result_query)
            coupon = cursor.fetchall()
            conn.close()
            return render_template('couponSearch.html', coupon=coupon)
    else:
        return redirect(url_for('home'))



# 메뉴 추가 페이지
@app.route('/insert', methods=['GET','POST'])
def insert():
    try:
        name = request.form.get('name')
        category = request.form.get('category')
        price = request.form.get('price')
        menu_con = request.form.get('menu_con')
        size = request.form.get('size')
        imgUrl = request.form.get('imgUrl')
        conn = get_connection()
        cursor = conn.cursor()
        query = "INSERT INTO menu (name, category, price,menu_con,size,imageUrl) VALUES (%s, %s, %s,%s,%s,%s)"
        cursor.execute(query, (name, category, price,menu_con,size,imgUrl))
        conn.commit()
        cursor.execute("select * from menu")
        menu = cursor.fetchall()        
        return render_template('insert.html',menu=menu)
    except Exception as e:
        response = {'error': str(e)}
        return jsonify(response)
    finally:
        if conn:
            conn.close()

# 쿠폰코드 추가 페이지
@app.route('/coupon', methods=['POST'])
def coupon():
    try:
        conn = get_connection()
        cursor = conn.cursor()
        coupon_code = request.form.get('coupon')
        name = request.form.get('name')
        date = request.form.get('date')
        amount = request.form.get('amount')
        if amount:
            conn = get_connection()
            cursor = conn.cursor()
            query = "INSERT INTO coupon (coupon_code, expiry_date, amount) VALUES (%s, %s, %s)"
            cursor.execute(query, (coupon_code, date, amount))
            conn.commit()
        if name:
            id_query = f"select menu_id from menu where name='{name}'"
            cursor.execute(id_query)
            menu_id = cursor.fetchone()[0]
            print(menu_id)            
            conn = get_connection()
            cursor = conn.cursor()
            query = "INSERT INTO coupon (coupon_code, expiry_date, menu_id) VALUES (%s, %s, %s)"
            cursor.execute(query, (coupon_code, date, menu_id))
            conn.commit()
        conn = get_connection()
        cursor = conn.cursor()
        result_query = "SELECT C.coupon_code, C.expiry_date, M.name, C.C_use, C.amount FROM coupon C LEFT JOIN menu M ON C.menu_id = M.menu_id"
        cursor.execute(result_query)
        coupon = cursor.fetchall()
        conn.close()
        return render_template('coupon.html', coupon=coupon)
    except:
        return render_template('coupon.html')


# 매출 확인

@app.route('/sales',methods = ['POST'])
def sales():
    date1 = request.form.get('date1')
    date2 = request.form.get('date2')
    conn = get_connection()
    cursor = conn.cursor()
    if date1 and date2:
        dayQuery= "select date(date) as day, sum(total_amount) as total from orders WHERE date >= %s AND date < %s group by day"
        cursor.execute(dayQuery,(date1,date2))
        day_sales = cursor.fetchall()
        monthQuery = "select SUBSTRING(date, 1, 7) as month, sum(total_amount) as total from orders where date >= %s AND date < %s group by month"
        cursor.execute(monthQuery,(date1,date2))
        month_sales = cursor.fetchall()
    else:
        dayQuery= "select date(date) as day, sum(total_amount) as total from orders  group by day"
        cursor.execute(dayQuery)
        day_sales = cursor.fetchall()
        monthQuery = "select SUBSTRING(date, 1, 7) as month, sum(total_amount) as total from orders group by month"
        cursor.execute(monthQuery)
        month_sales = cursor.fetchall()
    conn.close()
    return render_template('sales.html', day_sales=day_sales ,month_sales=month_sales) 

# 메뉴별 판매량
@app.route('/sales_quantity', methods=['GET','POST'])
def sales_quantity():
    date1 = request.form.get('date1')
    date2 = request.form.get('date2')
    conn = get_connection()
    cursor = conn.cursor()
    if date1 and date2:
        query = "SELECT B.menu_id, B.name, SUM(A.quantity) AS total_quantity, B.imageUrl FROM order_detail A LEFT JOIN menu B ON A.menu_id = B.menu_id LEFT JOIN orders C ON A.order_id = C.order_id WHERE C.Date >= %s and C.Date <= %s GROUP BY B.menu_id ORDER BY total_quantity DESC"
        cursor.execute(query,(date1,date2))    
    else:
        query = "SELECT B.menu_id, B.name, SUM(A.quantity) AS total_quantity, B.imageUrl FROM order_detail A left join menu B ON A.menu_id=B.menu_id GROUP BY menu_id ORDER BY total_quantity DESC"
        cursor.execute(query)
    sales_quantity = cursor.fetchall()
    conn.close()
    return render_template('sales_quantity.html', sales_quantity=sales_quantity, date1=date1,date2=date2)




###############################################################


# DB에서 메뉴정보 가져오기
class TodoList(Resource):
    def get(self):
        conn = get_connection()
        query = "SELECT * FROM menu"
        cursor = conn.cursor()
        cursor.execute(query)
        db_result = cursor.fetchall()

        menu = {}
        for item in db_result:
            menu[item[0]] = {
                "name": item[1],
                "price": item[3],
                "menu_con": item[4],
                "size": item[5],
                "imageUrl": item[6],
                "category": item[2]
            }

        return jsonify({"menu": menu})



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
                    return {"result": {0:{"amount":amount}}}
                elif amount == 0 and menu_id is None:
                    return {"result" : {1:{"sub":"잔액이 없습니다."}}}
                else:
                    # 사용가능 할 때 return 해줄 result 값 DB에서 가져오기 
                    conn = get_connection()
                    cursor = conn.cursor()
                    cursor.execute(f"select menu_id, name, price, menu_con, size, imageUrl from menu where menu_id='{menu_id}'")
                    result_coupon = cursor.fetchall()[0]
                    result = {"menu_id":result_coupon[0],
                              "name":result_coupon[1],
                              "price":result_coupon[2],
                              "menu_con":result_coupon[3],
                              "size":result_coupon[4],
                              "imageUrl":result_coupon[5]}
                    conn.close()
                    # 사용여부 체크 C_use==0 이면 사용가능
                    if C_use==0:
                        return {"result" : {2:result}}
                    else:
                        return {"result" : {1:{"sub":"이미 사용한 쿠폰입니다."}}}
            else:
                return {"result" : {1:{"sub":"사용기한이 지났습니다."}}}    
            
        # 쿠폰번호를 잘못입력했거나 없을때(입력한 코드에 대한 정보가 DB에 없을때)           
        else:
            result = "잘못 입력 또는\n없는 쿠폰입니다."
            return {"result": {1:{"sub":result}}}




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
            print(menu_ids)
            print(total_amount)

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
                # discount = data['discount']

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
                # else:
                #     amount_result = amount # -discount
                #     print(amount_result)
                #     conn = get_connection()
                #     cursor = conn.cursor()
                #     cursor.execute("UPDATE coupon SET amount = %s WHERE coupon_code = %s", (amount_result, coupon_code))
                #     conn.commit()
                conn.close()

            
            response = {"response":order_id}
            
            return {"response":order_id}

        except Exception as e:
            print(str(e))
            response = jsonify({"error": "Error saving order"})
            
            return response

   

#  ------ 모델 코드 시작 ------     

class Face(Resource):
    def post(self):
        if 'image' not in request.files:
            return jsonify({'error': 'No image part'}), 400

        file = request.files['image']
        if file:
            image = Image.open(io.BytesIO(file.read()))
            image = image.rotate(90, expand=True)
            image = image.convert('RGB')
            image.save('C:/Users/aischool185/Desktop/BBMR/Flask/image/image1.png')
            print("이미지 -> ", image)

            img_path = 'C:/Users/aischool185/Desktop/BBMR/Flask/image/image1.png'# 이미지 읽기
            img = cv2.imread(img_path)
            # DeepFace.analyze에 이미지 객체를 전달
            deep_result = DeepFace.analyze(img_path=img, actions=['age'], enforce_detection=False)
            print("result ->", deep_result)
            age = deep_result[0]["age"]
            print("age ->", age)
            if age < 40:
                result = 1
            else:
                result = 0
            return {'result': result}



# #  ------ 모델 코드 시작 ------

# # 학습된 모델 로드
# # 231121
# model = tf.keras.models.load_model('face_cnn_model.h5')
# @app.route('/upload', methods=['POST'])
# def upload_file():
#     if 'image' not in request.files:
#         return jsonify({'error': 'No image part'}), 400

#     file = request.files['image']
#     print(file)

#     if file:
#         # 이미지 읽기 및 리사이즈
#         image = Image.open(io.BytesIO(file.read()))
#         image = image.rotate(90, expand=True)

#         # RGBA에서 RGB로 변환
#         image = image.convert('RGB')
        
#         print("image -> ",image)
#         image.save('image/image1.png')
#         image = image.resize((360, 480))  # 리사이즈
#         image.save('image/image2.png')
#         # 필요한 추가 전처리 과정
#         # 예시: 이미지를 numpy 배열로 변환
#         image = np.array(image)
#         image = image / 255.0  # 정규화
#         image = np.expand_dims(image, axis=0)  # 모델 입력 형태에 맞게 차원 확장

#         # 모델 예측
#         prediction = model.predict(image)
#         print("prediction -> " , prediction[0][0])

#         # 예측 결과 처리 및 반환
#         # 예시: 예측 결과의 최대값 인덱스 반환
#         if prediction[0][0] < 0.5:
#             result = 0
#         else:
#             result = 1
#         # result = 0
#         print("result ->", result)
#         return {'result': result}

# #  ------ 모델 코드 끝 -------

# ------ 모델 코드 끝 -------

api.add_resource(Face,"/face/")
api.add_resource(SaveOrder,"/saveorder/")
api.add_resource(checkCoupon,"/checkcoupon/")
api.add_resource(TodoList,'/todos/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 