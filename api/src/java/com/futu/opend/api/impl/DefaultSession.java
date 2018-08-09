package com.futu.opend.api.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import com.futu.opend.api.Brokers;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.OrderBooks;
import com.futu.opend.api.Session;
import com.futu.opend.api.protobuf.GetGlobalState;
import com.futu.opend.api.protobuf.QotCommon.KLine;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetBroker;
import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints;
import com.futu.opend.api.protobuf.QotGetKL;
import com.futu.opend.api.protobuf.QotGetOrderBook;
import com.futu.opend.api.protobuf.QotGetPlateSecurity;
import com.futu.opend.api.protobuf.QotGetPlateSet;
import com.futu.opend.api.protobuf.QotGetRT;
import com.futu.opend.api.protobuf.QotGetReference;
import com.futu.opend.api.protobuf.QotGetRehab;
import com.futu.opend.api.protobuf.QotGetSecuritySnapshot;
import com.futu.opend.api.protobuf.QotGetStaticInfo;
import com.futu.opend.api.protobuf.QotGetSubInfo;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotGetTradeDate;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.PlateSetType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.SecurityType;
import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotCommon.TimeShare;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints.NoDataMode;
import com.futu.opend.api.protobuf.QotGetReference.ReferenceType;

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
	
	public GetGlobalState.Response getGlobalState(long futuUserID) throws IOException{
		GetGlobalStateExec exec = new GetGlobalStateExec(futuUserID);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetSubInfo.Response qotGetSubInfo(boolean isReqAllConn) throws IOException{
		QotGetSubInfoExec exec = new QotGetSubInfoExec(isReqAllConn);
		request.execute(exec);
		return exec.getValue();
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
	
	public QotGetKL.Response qotGetKL(QotMarket market,String symbol,RehabType rehabType,KLType klType,int reqNum,IUpdateCallBack<List<KLine>> callback) throws IOException{
		QotGetKLExec exec = new QotGetKLExec(market,symbol,rehabType,klType,reqNum,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetRT.Response qotGetRT(QotMarket market,String symbol,IUpdateCallBack<List<TimeShare>> callback)  throws IOException{
		QotGetRTExec exec = new QotGetRTExec(market,symbol,callback);
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
	
	public QotGetOrderBook.Response qotGetOrderBook(QotMarket market,String symbol,int num,IUpdateCallBack<OrderBooks> callback) throws IOException{
		QotGetOrderBookExec exec = new QotGetOrderBookExec(market,symbol,num,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetBroker.Response qotGetBroker(QotMarket market,String symbol,IUpdateCallBack<Brokers> callback)  throws IOException{
		QotGetBrokerExec exec = new QotGetBrokerExec(market,symbol,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetHistoryKL.Response qotGetHistoryKL(QotMarket market,String symbol,RehabType rehabType,KLType klType,String beginTime,String endTime,int maxAckKLNum,long needKLFieldsFlag) throws IOException{
		QotGetHistoryKLExec exec = new QotGetHistoryKLExec(market,symbol,rehabType,klType,beginTime,endTime,maxAckKLNum,needKLFieldsFlag);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetHistoryKLPoints.Response qotGetHistoryKLPoints(QotMarket market,String[] symbols,RehabType rehabType,KLType klType,String[] timeLists,NoDataMode noDataMode,int maxReqSecurityNum,long needKLFieldsFlag)  throws IOException{
		QotGetHistoryKLPointsExec exec = new QotGetHistoryKLPointsExec( market,symbols,rehabType,klType,timeLists,noDataMode, maxReqSecurityNum,needKLFieldsFlag);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetRehab.Response qotGetRehab(QotMarket market,String[] symbols)  throws IOException{
		QotGetRehabExec exec = new QotGetRehabExec(market,symbols);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetStaticInfo.Response qotGetStaticInfo(QotMarket market,SecurityType secType,String[] symbols)   throws IOException{
		QotGetStaticInfoExec exec = new QotGetStaticInfoExec(market,symbols,secType);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetSecuritySnapshot.Response qotGetSecuritySnapshot(QotMarket market,String[] symbols)   throws IOException{
		QotGetSecuritySnapshotExec exec = new QotGetSecuritySnapshotExec(market,symbols);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetPlateSet.Response qotGetPlateSet(QotMarket market,PlateSetType plateSetType)   throws IOException{
		QotGetPlateSetExec exec = new QotGetPlateSetExec(market,plateSetType);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetPlateSecurity.Response qotGetPlateSecurity(QotMarket market,String symbol) throws IOException{
		QotGetPlateSecurityExec exec = new QotGetPlateSecurityExec(market,symbol);
		request.execute(exec);
		return exec.getValue();
	}
	
	public QotGetReference.Response qotGetReference(QotMarket market,String symbol,ReferenceType referenceType) throws IOException{
		QotGetReferenceExec exec = new QotGetReferenceExec(market,symbol,referenceType);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotRegQotPush.Response qotRegQotPush(QotMarket market, String[] symbols,SubType[] subTypes, boolean isFirstPush, boolean isRegOrUnReg) throws IOException {
		QotRegQotPushExec exec = new QotRegQotPushExec(market,symbols,subTypes,isFirstPush,isRegOrUnReg);
		request.execute(exec);
		return exec.getValue();
	}

	public QotGetTradeDate.Response qotGetTradeDate(QotMarket market,String beginTime,String endTime) throws IOException{
		QotGetTradeDateExec exec = new QotGetTradeDateExec(market,beginTime,endTime);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotRegQotPush.Response qotRegQotPush(QotMarket market, String[] symbols,SubType[] subTypes) throws IOException {
		QotRegQotPushExec exec = new QotRegQotPushExec(market,symbols,subTypes);
		request.execute(exec);
		return exec.getValue();
	}

	@Override
	public void close() throws IOException {
		request.close();
	}
}
