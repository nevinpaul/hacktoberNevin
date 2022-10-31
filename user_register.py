#!c:\python\python
from lib import DBConnection as c
import cgi, cgitb,json,os
print("Content-type: text/html\n\n")
cgitb.enable()
form = cgi.FieldStorage()
action=form.getvalue("action")
if action=="register":  # action of form
    data=json.loads(form.getvalue("data"))
    sql="insert into (your table name) values(%s,%s,%s,%s,%s)"
    vals=(None,data['c_name'],data['c_mail'],data['c_phno'],data['c_pwd'])
    result=c.db.setValues(sql,vals)
    print(result)



     
  
    