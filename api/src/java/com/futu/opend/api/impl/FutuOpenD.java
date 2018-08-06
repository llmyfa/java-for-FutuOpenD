package com.futu.opend.api.impl;

import com.futu.opend.api.Session;

public class FutuOpenD {
	
	public static Session openSession(String ip,int port){
		Session session = new DefaultSession();
		try {
			session.openSession(ip, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	
	public static Session openSession(String ip,int port,boolean keepAlive){
		Session session = new DefaultSession();
		try {
			session.openSession(ip, port,keepAlive);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
}
