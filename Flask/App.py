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


# DB에서 메뉴(name, price) 가져오기
class TodoList(Resource):
    def fetch_menu(self, category):
        conn = get_connection()
        query = f"SELECT * FROM menu WHERE cate = '{category}'"
        cursor = conn.cursor(dictionary=True)
        cursor.execute(query)
        db_result = cursor.fetchall()
        menu = {}
        for item in db_result:
            menu[item['menu_id']] = [item['price'], item['name']]
        return menu

    def get(self):
        coffee = self.fetch_menu('coffee')
        dessert = self.fetch_menu('dessert')
        tea = self.fetch_menu('tea')
        md = self.fetch_menu('md')
        flatccino = self.fetch_menu('flatccino')
        beverage = self.fetch_menu('beverage')
        etc = self.fetch_menu('etc')

        print(coffee)

        return {"coffee": coffee, "dessert": dessert, "etc": etc,
                "tea":tea, "md":md, "flatccino": flatccino,"beverage" : beverage}
    
   



# 학습된 모델 로드
model = tf.keras.models.load_model('test_face_cnn_model.h5')

################################################################################

### 1번
def preprocess_image(image):
    # 이미지 전처리
    img = image.resize((360, 480))
    img_array = np.array(img) / 255.0
    img_array = np.expand_dims(img_array, axis=0)
    return img_array

@app.route('/upload_and_predict', methods=['POST'])
def upload_and_predict():
    # 클라이언트로부터 이미지 파일 받기
    if 'image' not in request.files:
        return jsonify({'error': 'No image part'})

    image_file = request.files['image']
    
    # 이미지를 PIL Image로 변환
    image = Image.open(io.BytesIO(image_file.read())).convert("RGB")
    
    # 전처리
    preprocessed_image = preprocess_image(image)

    # 모델 예측
    input_array = np.array([np.asarray(preprocessed_image)])
    predictions = model.predict(input_array)

    # 결과 전송
    result = {'prediction': predictions.tolist()}
    return jsonify(result)




api.add_resource(TodoList,'/todos/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 



