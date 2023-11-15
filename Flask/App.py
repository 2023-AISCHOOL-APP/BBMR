from io import BytesIO
from tkinter import Image
from test_connection import get_connection
from mysql.connector import Error
from flask import Flask, request, jsonify, send_file
from flask_restful import Resource, Api, reqparse, abort
from resources.cafe_test import cafe_test

app = Flask(__name__)
api = Api(app)



# 아직 수정 해야할 부분 TodoList, DessertList
class TodoList(Resource):
    def get(self):
        conn = get_connection()
        query = "select * from test"
        cursor = conn.cursor(dictionary=True)
        cursor.execute(query)
        dbResult = cursor.fetchall()
        result = dbResult[0]['price']
        Todos = {}
        for i in range(len(dbResult)):
            Todos[dbResult[i]['name']] =  dbResult[i]['price']

        print(Todos)

        conn = get_connection()
        query2 = "select * from dessert"
        cursor = conn.cursor(dictionary=True)
        cursor.execute(query2)
        dbResult2 = cursor.fetchall()
        result2 = dbResult2[0]['price']
        dessert = {}
        for i in range(len(dbResult2)):
            dessert[dbResult2[i]['name']] =  dbResult2[i]['price']

        print(dessert)


        return {"coffee":Todos, "dessert":dessert}
    
class DessertList(Resource):
    def get(self):
        conn = get_connection()
        query = "select * from dessert"
        cursor = conn.cursor(dictionary=True)
        cursor.execute(query)
        dbResult = cursor.fetchall()
        result = dbResult[0]['price']
        dessert = {}
        for i in range(len(dbResult)):
            dessert[dbResult[i]['name']] =  dbResult[i]['price']

        print(dessert)
        return dessert

    

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
api.add_resource(DessertList,'/dessert/')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000) 



