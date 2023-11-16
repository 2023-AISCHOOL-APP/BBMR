from io import BytesIO
from tkinter import Image
from connection import get_connection
from mysql.connector import Error
from flask import Flask, request, jsonify, send_file
from flask_restful import Resource, Api, reqparse, abort

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
            menu[item['name']] = item['price']
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
    
   

# 앱에서 촬영된 사진 이미지 byteArray로 받아오고 result return 해주는 함수
class ImageUpload(Resource):
    def post(self):
        try:
            # 이미지를 바이트 배열로 받음
            image_bytes = request.get_data()
            
            # 여기서 이미지 처리 로직을 추가하면 됨
            # 예를 들어, 받은 이미지를 저장하거나 다른 처리를 수행
            
            # 결과값으로 '1'을 리턴
            result = '123123'
            print(image_bytes)
            return jsonify({'result': result})
        except Exception as e:
            return jsonify({'error': 'error'})


api.add_resource(ImageUpload, "/model/")
api.add_resource(TodoList,'/todos/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 



