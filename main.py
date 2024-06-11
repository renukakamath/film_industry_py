from flask import Flask 
from database import *
from public import public
from admin import admin
from member import member
from api import api

app=Flask(__name__)
app.secret_key="abc"

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(member,url_prefix='/member')
app.register_blueprint(api, url_prefix='/api')
app.run(debug = True, port = "5023", host = "0.0.0.0")