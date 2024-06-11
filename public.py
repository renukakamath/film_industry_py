from flask import *
from database import *
import uuid

public=Blueprint('public',__name__)

@public.route('/')
def home():
	 return render_template('index.html')
@public.route('/login',methods=['get','post'])
def login():
    if 'submit' in request.form:
        uname=request.form['username']
        passs=request.form['password']

        q="select * from login where username='%s'and password='%s'"%(uname,passs)

        res=select(q)

        if res:
            lid=res[0]['login_id']
            session['lid']=lid
            if res[0]['user_type']=="admin":
                return redirect(url_for('admin.adminhome'))
            if res[0]['user_type']=="member":
                q="select * from members where login_id='%s'" %(lid)
                res=select(q)
                session['member_id']=res[0]['member_id']
                return redirect(url_for('member.memberhome'))
            if res[0]['user_type']=="pay_pending":

                return render_template('payment.html')
        else:
            flash('invalid username and password')
    

    return render_template('login.html')
@public.route('/register',methods=['get','post'])
def register():
    if 'submit' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        hname=request.form['hname']
        image=request.files['image']
        path='static/images/'+str(uuid.uuid4())+image.filename
        image.save(path)
        place=request.form['place']
        phone=request.form['phone']
        pincode=request.form['pincode']
        gender=request.form['gender']
        dob=request.form['dob']
        
        email=request.form['email']
        uname=request.form['uname']
        password=request.form['password']
        q="select * from login where username='%s' and password='%s'"%(uname,password)
        res=select(q)
        if res:
            flash('already exist')
        else:
            q="insert into login values(NULL,'%s','%s','pay_pending')"%(uname,password)
            id=insert(q)
            q="insert into members values(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,path,hname,place,pincode,gender,dob,phone,email)
            insert(q)
            flash('successfully')
    return render_template('register.html')
@public.route('/payment',methods=['get','post'])
def payment():
    q="select * from members where login_id='%s'"%(session['lid'])
    res=select(q)
    id=res[0]['member_id']
    q="insert into payments values(NULL,'%s',curdate(),'2389')"%(id)
    insert(q)
    q="update login set user_type='pending' where login_id='%s'"%(session['lid'])
    update(q)
    flash('successfully')
    return """"<script>alert('Payment Succesfull!!!');window.location='/';</script>"""

