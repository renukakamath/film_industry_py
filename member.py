from flask import *
from database import *
member=Blueprint('member',__name__)
@member.route('/memberhome')
def memberhome():
	return render_template('member_home.html')
@member.route('/manage_vaccancy',methods=['get','post'])
def manage_vaccancy():
	data={}
	q="select * from industry_types"
	res=select(q)
	data['types']=res
	if 'submit' in request.form:
		type_id=request.form['type_id']
		position=request.form['position']
		details=request.form['details']
		number=request.form['number']
		q="insert into vaccancies values(NULL,'%s','%s','%s','%s','%s','active',curdate())"%(session['member_id'],type_id,position,details,number)
		insert(q)
		flash('successfully')
	q="select * from vaccancies inner join industry_types using(type_id) where member_id='%s'"%(session['member_id'])
	res=select(q)
	data['vaccancies']=res
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']

		
	else:
		action=None
	if action=='delete':
		q="delete from vaccancies where vaccancy_id='%s'"%(id)
		delete(q)
		flash('successfully')
		return redirect(url_for('member.manage_vaccancy'))
	if action=='update':
		q="select * from vaccancies inner join industry_types using(type_id) where vaccancy_id='%s'"%(id)
		res=select(q)
		data['updater']=res
	if 'update' in request.form:
		type_id=request.form['type_id']
		position=request.form['position']
		details=request.form['details']
		number=request.form['number']
		q="update vaccancies set type_id='%s',position_vacant='%s', details='%s',no_of_vaccancy='%s' where vaccancy_id='%s'"%(type_id,position,details,number,id)
		update(q)
		flash('successfully')
		return redirect(url_for('member.manage_vaccancy'))

	if action=='application':
		q="SELECT * FROM `vaccancy_applications` INNER JOIN vaccancies USING(vaccancy_id) JOIN members ON members.member_id=`vaccancy_applications`.member_id WHERE `vaccancy_applications`.vaccancy_id='%s'"%(id)
		res=select(q)
		print(q)
		data['application']=res


	if action=="hrr":
		q="SELECT * FROM `hire` WHERE `member_id`='%s' AND `hired_member_id`='%s'"%(session['member_id'],id)
		print(q)
		res=select(q)
		if res:
			return """"<script>alert('alredy hired!!!!');window.location='/member/manage_vaccancy';</script>"""

		else:	
			q="INSERT INTO `hire` VALUE(NULL,'%s','%s',CURDATE())"%(session['member_id'],id)
			print(q)
			insert(q)
			print(id,",,,,,,,,,,,,,,,,,,,,,,,,,,,,")
			q1="UPDATE `vaccancy_applications` SET `application_status`='Hired' WHERE `member_id`='%s'"%(id)
			print(q1)
			update(q1)
			return """"<script>alert('Hired!!!!');window.location='/member/manage_vaccancy';</script>"""
			# return redirect(url_for("member.manage_vaccancy"))

	

	return render_template('member_manage_vaccancy.html',data=data)
@member.route('/manage_auditions',methods=['get','post'])
def manage_auditions():
	data={}
	if 'submit' in request.form:
		title=request.form['title']
		number=request.form['number']
		venue=request.form['venue']
		date=request.form['date']
		q="insert into auditions values(NULL,'%s','%s','%s','%s','%s','upcoming')"%(session['member_id'],title,number,venue,date)
		insert(q)
		flash('successfully')
	q="select * from auditions where member_id='%s'"%(session['member_id'])
	res=select(q)
	data['auditions']=res
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		
	else:
		action=None
	if action=='delete':
		q="delete from auditions where audition_id='%s'"%(id)
		delete(q)
		flash('successfully')
		return redirect(url_for('member.manage_auditions'))
	if action=='update':
		q="select * from auditions where audition_id='%s'"%(id)
		res=select(q)
		data['updater']=res
	if 'update' in request.form:
		title=request.form['title']
		number=request.form['number']
		venue=request.form['venue']
		date=request.form['date']
		q="update auditions set title='%s', required_numbers='%s',venue='%s',date_time='%s' where audition_id='%s'"%(title,number,venue,date,id)
		update(q)
		flash('successfully')
		return redirect(url_for('member.manage_auditions'))
	if action=='over':
		q="update auditions set audition_status='completed' where audition_id='%s'"%(id)
		update(q)
		flash('successfully')
		return redirect(url_for('member.manage_auditions'))



	return render_template('user_manage_auditions.html',data=data)
@member.route('/view_notifications',methods=['get','post'])
def view_notifications():
	data={}
	q="select * from notifications"
	res=select(q)
	data['notification']=res
	return render_template('member_view_notifications.html',data=data)
@member.route('/send_complaints',methods=['get','post'])
def send_complaints():
	data={}
	if 'submit' in request.form:
		complaint=request.form['complaint']
		q="insert into complaints values(NULL,'%s','%s','pending',curdate())"%(session['member_id'],complaint)
		insert(q)
		flash('successfully')
	q="select * from complaints where member_id='%s'"%(session['member_id'])
	res=select(q)
	data['complaints']=res
	return render_template('member_send_complaint.html',data=data)
@member.route('/view_portfolio',methods=['get','post'])
def view_portfolio():
	data={}
	q="select * from portfolio inner join members using(member_id)"
	res=select(q)
	data['portfolio']=res
	if 'submit' in request.form:
		name=request.form['name']+"%"
		q="select * from members inner join portfolio using(member_id) where (first_name like '%s' or last_name like '%s')"%(name,name)
		res=select(q)
		data['members']=res
		if res:
			data['members']=res
		else:
			return """"<script>alert('No Matching Resultsss!!!!');window.location='/member/view_portfolio';</script>"""
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		
	else:
		action=None
	if action=='hire':
		q="select * from hire where(member_id='%s' and hired_member_id='%s')"%(session['member_id'],id)
		res=select(q)
		if res:
			return """"<script>alert('Already Hired By You!!');window.location='/member/view_portfolio';</script>"""
		else:

			q="insert into hire values (NULL,'%s','%s',curdate())"%(session['member_id'],id)
			insert(q)
			return """"<script>alert('Succesfully Hired!!');window.location='/member/view_portfolio';</script>"""


	return render_template('member_view_portfolio.html',data=data)
@member.route('/hirings',methods=['get','post'])
def hirings():
	data={}
	q="select * from hire  join members on members.member_id=hire.hired_member_id where hire.member_id='%s'"%(session['member_id'])
	res=select(q)
	data['hire']=res
	return render_template('member_view_hirings.html',data=data)


