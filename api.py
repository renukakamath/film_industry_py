from flask import Blueprint, request
import demjson
from database import *
import uuid

def encode(data):
	return demjson.encode(data)

api = Blueprint('api', __name__)

@api.route('/logins', methods=['get', 'post'])
def logins():
	data = {}
	username = request.args['username']
	password = request.args['password']
	q = "select * from login where username = '%s' and password = '%s' AND `user_type` = 'normal'" %(username, password)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'login'
	return encode(data)

@api.route('/register', methods=['get', 'post'])
def register():
	data = {}
	fname = request.form['fname']
	lname = request.form['lname']
	address = request.form['address']
	place = request.form['place']
	gender = request.form['gender']
	pincode = request.form['pincode']
	photo = request.files['image']
	path='static/images/'+str(uuid.uuid4())+photo.filename
	photo.save(path)
	dob = request.form['dob']
	phone = request.form['phone']
	email = request.form['email']
	username = request.form['username']
	password = request.form['password']



	# filename = str(uuid.uuid4()) + ".jpg"
	# image.save("static/images/" + filename)

	q_log = "INSERT INTO login (`username`, `password`, `user_type`) VALUES ('%s', '%s', 'normal')" %(username, password)
	res_log = insert(q_log)
	if res_log:
		q = "INSERT INTO `members` (`login_id`, `first_name`, `last_name`, `photo`, `house_name`, `place`, `pincode`, `gender`, `dob`, `phone`, `email`) VALUES ((SELECT MAX(login_id) FROM login), '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')" %(fname, lname, path, address, place, pincode, gender, dob, phone, email)
		res = insert(q)
		if res:
			data['status'] = 'success'
		else:
			data['status'] = 'failed'
	else:
		data['status'] = 'failed'
	data['method'] = 'register'
	return encode(data)

@api.route('/view_auditions', methods=['get', 'post'])
def view_auditions():
	data = {}
	q = "SELECT * FROM `auditions` INNER JOIN `members` USING (`member_id`) WHERE `audition_status` = 'upcoming' ORDER BY `audition_id` DESC"
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_auditions'
	return encode(data)

@api.route('/view_notification', methods=['get', 'post'])
def view_notification():
	data = {}
	q = "SELECT * FROM `notifications` ORDER BY `notification_id` DESC"
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_notification'
	return encode(data)

@api.route('/portfolio', methods=['get','post'])
def portfolio():
	data = {}
	title = request.form['title']
	description = request.form['descriptions']
	login_id = request.form['login_id']
	image = request.files['image']

	path='static/images/'+str(uuid.uuid4())+ ".jpg"
	image.save(path)

	print("*******************",login_id)

	q = "INSERT INTO `portfolio` (`member_id`, `title`, `descriptions`, `image_path`) VALUES ((SELECT `member_id` FROM `members` WHERE `login_id` = '%s'), '%s', '%s', '%s')" %(login_id, title, description, path)
	res = insert(q)
	if res:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'portfolio'
	return encode(data)

@api.route('/View_my_portfolio', methods=['get', 'post'])
def View_my_portfolio():
	data = {}
	login_id = request.args['log_id']
	q = "SELECT * FROM `portfolio` WHERE `member_id` = (SELECT `member_id` FROM `members` WHERE `login_id` = '%s') ORDER BY `portfolio_id` DESC" %(login_id)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'View_my_portfolio'
	return encode(data)

@api.route('/view_vacancies/', methods=['get', 'post'])
def view_vacancies():
	data = {}
	q = "SELECT * FROM `vaccancies` INNER JOIN `industry_types` USING (`type_id`) INNER JOIN `members` USING (`member_id`) WHERE `vaccancy_status` = 'active' ORDER BY `vaccancy_id` DESC"
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_vacancies'
	return encode(data)

@api.route('/apply_vacancy/', methods=['get','post'])
def apply_vacancy():
	data = {}
	vacancy_id = request.args['vacancy_id']
	login_id = request.args['login_id']
	q = "INSERT INTO `vaccancy_applications` (`member_id`, `vaccancy_id`, `description`, `application_status`, `date_time`) VALUES ((SELECT `member_id` FROM `members` WHERE `login_id` = '%s'), '%s', 'pending','pending', NOW())" %(login_id, vacancy_id)
	res = insert(q)
	if res:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'apply_vacancy'
	return encode(data)

@api.route('/view_vacancy_status/', methods=['get', 'post'])
def view_vacancy_status():
	data = {}
	login_id = request.args['login_id']
	q = "SELECT * FROM `vaccancy_applications` INNER JOIN `vaccancies` USING (`vaccancy_id`) WHERE `vaccancy_applications`.`member_id` = (SELECT `member_id` FROM `members` WHERE `login_id` = '%s') ORDER BY `vaccancy_application_id` DESC" %(login_id)
	print(q)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_vacancy_status'
	return encode(data)

@api.route('/view_hired', methods=['get', 'post'])
def view_hired():
	data = {}
	login_id = request.args['login_id']
	q = "SELECT * FROM `hire` INNER JOIN `members` USING (`member_id`) WHERE `hired_member_id` = (SELECT `member_id` FROM `members` WHERE `login_id` = '%s') ORDER BY `hire_id` DESC" %(login_id)
	res = select(q)
	print(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	
	return encode(data)

@api.route('/send_complaint', methods=['get','post'])
def send_complaint():
	data = {}
	complaint = request.args['complaint']
	login_id = request.args['login_id']
	q = "INSERT INTO `complaints` (`member_id`, `complaint_description`, `reply_description`, `date_time`) VALUES ((SELECT `member_id` FROM `members` WHERE `login_id` = '%s'), '%s', 'pending', NOW())" %(login_id, complaint)
	res = insert(q)
	print(q)
	if res:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'send_complaint'
	return encode(data)

@api.route('/view_complaints', methods=['get', 'post'])
def view_complaints():
	data = {}
	login_id = request.args['login_id']
	q = "SELECT * FROM `complaints` WHERE `member_id` = (SELECT `member_id` FROM `members` WHERE `login_id` = '%s') ORDER BY `complaint_id` DESC" %(login_id)
	res = select(q)
	if res:
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_complaints'
	return encode(data)

@api.route('/send_review', methods=['get','post'])
def send_review():
	data = {}
	title = request.args['review_title']
	desc = request.args['description']
	login_id = request.args['login_id']
	q = "INSERT INTO `reviews` (`member_id`, `review_title`, `description`, `date_time`) VALUES ((SELECT `member_id` FROM `members` WHERE `login_id` = '%s'), '%s', '%s', NOW())" %(login_id, title, desc)
	res = insert(q)
	print(q)
	if res:
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	
	return encode(data)


@api.route('/Makepayment',methods=['get','post'])
def Makepayment():
	data={}
	login_id=request.args['login_id']
	q="select * from members where login_id='%s'"%(login_id)
	res=select(q)
	id=res[0]['member_id']
	q="insert into payments values(NULL,'%s',curdate(),'2389')"%(id)
	insert(q)
	q="update login set user_type='member' where login_id='%s'"%(login_id)
	update(q)
	data['status'] = 'success'
	return encode(data)
    


