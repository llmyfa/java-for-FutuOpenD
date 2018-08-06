package com.futu.opend.api.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.Session;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.futu.opend.api.protobuf.QotCommon.Ticker;

class DefaultSession implements Session{

	private Client request;
	
	@Override
	public void openSession(String ip, int port) throws UnknownHostException, IOException {
		request = new Client();
		request.open(ip, port);
	}
	
	@Override
	public void openSession(String ip, int port,boolean keepAlive) throws UnknownHostException, IOException {
		request = new Client();
		request.setKeepAlive(keepAlive);
		request.open(ip, port);
	}
	
	@Override
	public QotSub.Response qotSub(QotMarket market, String[] symbols, SubType[] subTypes) throws IOException {
		QotSubExec exec = new QotSubExec(market, symbols,  subTypes,true);
		request.execute(exec);
		return exec.getValue();
	}

	@Override
	public QotSub.Response qotUnSub(
			QotMarket market, String[] symbols, SubType[] subTypes)
			throws IOException {
		QotSubExec exec = new QotSubExec(market, symbols,  subTypes, false);
		request.execute(exec);
		return exec.getValue();
	}

	
	public QotGetBasicQot.Response stockBasicInfo(QotMarket market,String[] symbol,IUpdateCallBack<List<BasicQot>> callback) throws IOException{
		QotGetBasicQotExec exec = new QotGetBasicQotExec(market, symbol,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotGetTicker.Response qotGetTicker(QotMarket market, String symbol,IUpdateCallBack<List<Ticker>> callback) throws IOException {
		QotGetTickerExec exec = new QotGetTickerExec(market,symbol,100,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetTicker.Response qotGetTicker(QotMarket market,String symbol,int maxRetNum,IUpdateCallBack<List<Ticker>> callback) throws IOException{
		QotGetTickerExec exec = new QotGetTickerExec(market,symbol,maxRetNum,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	
	@Override
	public QotRegQotPush.Response QotRegQotPushExec(QotMarket market, String[] symbols,SubType[] subTypes, boolean isFirstPush, boolean isRegOrUnReg) throws IOException {
		QotRegQotPushExec exec = new QotRegQotPushExec(market,symbols,subTypes,isFirstPush,isRegOrUnReg);
		request.execute(exec);
		return exec.getValue();
	}

	
	@Override
	public QotRegQotPush.Response QotRegQotPushExec(QotMarket market, String[] symbols,SubType[] subTypes) throws IOException {
		QotRegQotPushExec exec = new QotRegQotPushExec(market,symbols,subTypes);
		request.execute(exec);
		return exec.getValue();
	}

	@Override
	public void close() throws IOException {
		request.close();
	}
}
