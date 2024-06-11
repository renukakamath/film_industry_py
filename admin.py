from flask import *
from database import *
import random
admin=Blueprint('admin',__name__)
@admin.route('/adminhome')
def adminhome():
	return render_template('admin_home.html')
@admin.route('/view_featured_member_registration',methods=['get','post'])
def view_featured_member_registration():
	data={}
	q="SELECT * FROM members INNER JOIN login USING(login_id) INNER JOIN payments USING(member_id) WHERE login.user_type='pending'"
	res=select(q)
	data['members']=res
	print(len(res),'/////////////////////')
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		lid=request.args['lid']
	else:
		action=None
	if action=='payment':
		q="select * from payments inner join members using(member_id) where payment_id='%s'"%(id)
		res=select(q)
		data['payment']=res
	if 'submit' in request.form:
		q="update login set user_type='member' where login_id='%s'"%(lid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.view_featured_member_registration'))

	return render_template('view_member_registration.html',data=data)

@admin.route('/manage_notifications',methods=['get','post'])
def manage_notifications():
	data={}
	if 'submit' in request.form:
		title=request.form['title']
		description=request.form['description']
		q="insert into notifications values(NULL,'%s','%s',curdate())"%(title,description)
		insert(q)
		flash('successfully')
     	
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		
	else:
		action=None
	if action=='delete':
		q="delete from notifications where notification_id='%s'"%(id)
		delete(q)
		flash('successfully')
		return redirect(url_for('admin.manage_notifications'))
	if action=='update':
		q="select * from notifications where notification_id='%s'"%(id)
		res=select(q)
		data['updater']=res
	if 'update' in request.form:
		description=request.form['description']
		title=request.form['title']
		q="update notifications set title='%s', description='%s' where notification_id='%s' "%(title,description,id)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.manage_notifications'))
	q="select * from notifications"
	res=select(q)
	data['notifications']=res


	return render_template('admin_manage_notifications.html',data=data)
@admin.route('/manage_categories',methods=['get','post'])
def manage_categories():
	data={}
	if 'submit' in request.form:
		name=request.form['name']
		description=request.form['description']
		q="insert into industry_types values(NULL,'%s','%s')"%(name,description)
		insert(q)
		flash('successfully')
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		
	else:
		action=None
	if action=='delete':
		q="delete from industry_types where type_id='%s'"%(id)
		delete(q)
		flash('successfully')
		return redirect(url_for('admin.manage_categories'))
	if action=='update':
		q="select * from industry_types where type_id='%s'"%(id)
		res=select(q)
		data['updater']=res
	if 'update' in request.form:
		name=request.form['name']
     
		description=request.form['description']
		q="update industry_types set type_name='%s', type_description='%s' where type_id='%s' "%(name,description,id)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.manage_categories'))
	q="select * from industry_types"
	res=select(q)
	data['types']=res
	return render_template('admin_manage_industry_types.html',data=data)
@admin.route('/view_reviews',methods=['get','post'])
def view_reviews():
	data={}
	q="select * from reviews inner join members using(member_id)"
	res=select(q)
	data['reviews']=res

	return render_template('admin_view_users_reviews.html',data=data)
@admin.route('/view_complaints',methods=['get','post'])
def view_complaints():
	data={}
	q="select * from complaints inner join members using(member_id)"
	res=select(q)
	data['complaints']=res
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		session['cid']=id
		
	else:
		action=None
	if action=='reply':
		q="select * from complaints inner join members using(member_id) where complaint_id='%s'"%(id)
		res=select(q)
		data['complaint']=res
		return render_template('admin_send_reply.html',data=data)
	return render_template('admin_view_complaints.html',data=data)
@admin.route('/reply_complaints',methods=['get','post'])
def reply_complaints():
	reply=request.form['reply']
	q="update complaints set reply_description='%s' where complaint_id='%s'"%(reply,session['cid']) 
	update(q)
	flash('successfully')
	return redirect(url_for('admin.view_complaints'))
@admin.route('/view_users',methods=['get','post'])
def view_users():
	data={}
	q="select * from members inner join login using(login_id)"
	res=select(q)
	data['users']=res
	return render_template('admin_view_users.html',data=data)