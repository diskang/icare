 # -*- coding: UTF-8 -*-
import requests
import json

def post(domain, headers, params, uri):
	url = domain+uri
	print (params)
	r = requests.post(url,data = params,headers = headers)
	print (r.status_code)

domain = "http://localhost:8080"
headers = {"Cookie":"JSESSIONID=F094FD62DA300FA064E8FB5A6770FEE1"}

elderFile = open("post_elder",'r',encoding= 'utf-8')
uri = elderFile.readline().split(" ")[1]
for elder in elderFile.read().split("|"):
	elder = elder.encode('utf-8')
	post(domain,headers,elder,uri)

staffFile = open("post_staff",'r',encoding= 'utf-8')
uri = staffFile.readline().split(" ")[1]
for staff in staffFile.read().split("|"):
	staff = staff.encode('utf-8')
	post(domain,headers,staff,uri)