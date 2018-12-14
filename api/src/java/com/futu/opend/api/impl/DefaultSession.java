package com.futu.opend.api.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.futu.opend.api.Brokers;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.OrderBooks;
import com.futu.opend.api.OrderDetails;
import com.futu.opend.api.Session;
import com.futu.opend.api.TraderSession;
import com.futu.opend.api.protobuf.GetGlobalState;
import com.futu.opend.api.protobuf.QotCommon.KLine;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetBroker;
import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints;
import com.futu.opend.api.protobuf.QotGetHoldingChangeList;
import com.futu.opend.api.protobuf.QotGetKL;
import com.futu.opend.api.protobuf.QotGetOptionChain;
import com.futu.opend.api.protobuf.QotGetOrderBook;
import com.futu.opend.api.protobuf.QotGetOrderDetail;
import com.futu.opend.api.protobuf.QotGetOwnerPlate;
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
import com.futu.opend.api.protobuf.QotRequestHistoryKL;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.TrdGetHistoryOrderFillList;
import com.futu.opend.api.protobuf.TrdGetHistoryOrderList;
import com.futu.opend.api.protobuf.TrdGetOrderFillList;
import com.futu.opend.api.protobuf.TrdUpdateOrder;
import com.futu.opend.api.protobuf.TrdUpdateOrderFill;
import com.futu.opend.api.protobuf.TrdGetOrderList;
import com.futu.opend.api.protobuf.TrdModifyOrder;
import com.futu.opend.api.protobuf.TrdPlaceOrder;
import com.futu.opend.api.protobuf.TrdCommon.OrderType;
import com.futu.opend.api.protobuf.TrdGetMaxTrdQtys;
import com.futu.opend.api.protobuf.TrdGetPositionList;
import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.PlateSetType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotCommon.SecurityType;
import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotCommon.TimeShare;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints.NoDataMode;
import com.futu.opend.api.protobuf.QotGetReference.ReferenceType;
import com.futu.opend.api.protobuf.TrdCommon.ModifyOrderOp;
import com.futu.opend.api.protobuf.TrdCommon.TrdAcc;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdCommon.TrdSide;
import com.futu.opend.api.protobuf.TrdGetAccList;
import com.futu.opend.api.protobuf.TrdGetFunds;
import com.futu.opend.api.protobuf.TrdUnlockTrade;

class DefaultSession implements Session,TraderSession{

	private Client request;
	private TrdEnv trdenv;
	private List<TrdAcc> trdAccs;
	
	@Override
	public void openSession(String ip, int port) throws UnknownHostException, IOException {
		openSession(ip,port,true);
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
	
	public QotRequestHistoryKL.Response qotRequestHistoryKL(QotMarket market,String symbol,RehabType rehabType,KLType klType,String beginTime,String endTime,int maxAckKLNum,long needKLFieldsFlag,String nextReqKey) throws IOException{
		QotRequestHistoryKLExec exec = new QotRequestHistoryKLExec(market,symbol,rehabType,klType,beginTime,endTime,maxAckKLNum,needKLFieldsFlag,nextReqKey);
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
	public QotGetOwnerPlate.Response qotGetOwnerPlate(QotMarket market,String[] symbol) throws IOException{
		QotGetOwnerPlateExec exec = new QotGetOwnerPlateExec(market,symbol);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotGetHoldingChangeList.Response qotGetHoldingChangeList(QotMarket market,String symbol,int holderCategory,String beginTime,String endTime) throws IOException{
		QotGetHoldingChangeListExec exec = new QotGetHoldingChangeListExec(market,symbol,holderCategory,beginTime,endTime);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotGetOptionChain.Response qotGetOptionChain(QotMarket market,String symbol,String beginTime,String endTime,int type,int condition) throws IOException{
		QotGetOptionChainExec exec = new QotGetOptionChainExec(market,symbol,beginTime,endTime,type,condition);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public QotGetOrderDetail.Response qotGetOrderDetail(QotMarket market,String symbol,IUpdateCallBack<OrderDetails> callback) throws IOException{
		QotGetOrderDetailExec exec = new QotGetOrderDetailExec(market,symbol,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TraderSession trdUnlockTradeForSimulate(long futuUserID,String pwdMD5) throws IOException {
		this.trdenv = TrdEnv.TrdEnv_Simulate;
		TrdGetAccListExec rrdexec = new TrdGetAccListExec(futuUserID);
		request.execute(rrdexec);
		TrdGetAccList.Response response = rrdexec.getValue();
		if (response.getRetType()==0){
			this.trdAccs = response.getS2C().getAccListList();
			return this;
		}
		throw new IOException(response.getRetMsg());
	}
	
	public TraderSession trdUnlockTradeForReal(long futuUserID,String pwdMD5) throws IOException {
		this.trdenv = TrdEnv.TrdEnv_Real;
		//获取交易账户列表
		TrdGetAccListExec rrdexec = new TrdGetAccListExec(futuUserID);
		request.execute(rrdexec);
		TrdGetAccList.Response response = rrdexec.getValue();
		if (response.getRetType()==0){
			trdAccs = response.getS2C().getAccListList();
			//解锁交易
			TrdUnlockTradeExec exec = new TrdUnlockTradeExec(true,pwdMD5);
			request.execute(exec);
			TrdUnlockTrade.Response res = exec.getValue();
			if (res.getRetType()==0){
				List<Long> accids = new ArrayList<Long>();
				for(TrdAcc accid : trdAccs){
					accids.add(accid.getAccID());
				}
				//订阅接收交易账户的推送数据
				TrdSubAccPushExec trdSubAccPushExec = new TrdSubAccPushExec(accids.toArray(new Long[]{}));
				request.execute(trdSubAccPushExec);
				return this;
			}
			throw new IOException(res.getRetMsg());
			
		}
		throw new IOException(response.getRetMsg());
	}

	public TrdUnlockTrade.Response trdlockTrade() throws IOException {
		TrdUnlockTradeExec exec = new TrdUnlockTradeExec(false,null);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetFunds.Response trdGetFunds(TrdMarket trdMarket) throws IOException {
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetFundsExec exec = new TrdGetFundsExec(trdenv,accID,trdMarket);
		request.execute(exec);
		return exec.getValue();
	}
	
	
	public TrdGetPositionList.Response trdGetPositionList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Double filterPLRatioMin,Double filterPLRatioMax) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetPositionListExec exec = new TrdGetPositionListExec(trdenv,accID,trdMarket,filterConditions,filterPLRatioMin,filterPLRatioMax);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetMaxTrdQtys.Response trdGetMaxTrdQtys(TrdMarket trdMarket,OrderType orderType,String code,double price,Long orderID,Boolean adjustPrice,Double adjustSideAndLimit) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetMaxTrdQtysExec exec = new TrdGetMaxTrdQtysExec(trdenv,accID,trdMarket,orderType,code,price,orderID,adjustPrice,adjustSideAndLimit);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetOrderList.Response trdGetOrderList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Integer[] filterStatusList) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetOrderListExec exec = new TrdGetOrderListExec(trdenv,accID,trdMarket,filterConditions,filterStatusList);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetHistoryOrderList.Response trdGetHistoryOrderList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Integer[] filterStatusList) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetHistoryOrderListExec exec = new TrdGetHistoryOrderListExec(trdenv,accID,trdMarket,filterConditions,filterStatusList);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdPlaceOrder.Response trdPlaceOrder(TrdMarket trdMarket,TrdSide trdSide,OrderType orderType,String code,double qty,double price,Boolean adjustPrice,Double adjustSideAndLimit,IUpdateCallBack<TrdUpdateOrder.Response> callback) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdPlaceOrderExec exec = new TrdPlaceOrderExec(trdenv,accID,trdMarket,trdSide,orderType,code,qty,price,adjustPrice,adjustSideAndLimit,request.getConnID(),callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdModifyOrder.Response trdModifyOrder(TrdMarket trdMarket,long orderID,ModifyOrderOp modifyOrderOp,Boolean forAll,Double qty,Double price,Boolean adjustPrice,Double adjustSideAndLimit) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdModifyOrderExec exec = new TrdModifyOrderExec(trdenv,accID,trdMarket,orderID,modifyOrderOp,forAll,qty,price,adjustPrice,adjustSideAndLimit,request.getConnID());
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetOrderFillList.Response trdGetOrderFillList(TrdMarket trdMarket,TrdFilterConditions filterConditions,IUpdateCallBack<TrdUpdateOrderFill.Response> callback) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetOrderFillListExec exec = new TrdGetOrderFillListExec(trdenv,accID,trdMarket,filterConditions,callback);
		request.execute(exec);
		return exec.getValue();
	}
	
	public TrdGetHistoryOrderFillList.Response trdGetHistoryOrderFillList(TrdMarket trdMarket,TrdFilterConditions filterConditions) throws IOException{
		long accID  = getAccId(trdenv,trdMarket);
		TrdGetHistoryOrderFillListExec exec = new TrdGetHistoryOrderFillListExec(trdenv,accID,trdMarket,filterConditions);
		request.execute(exec);
		return exec.getValue();
	}
	
	@Override
	public void close() throws IOException {
		request.close();
	}

	private long getAccId(TrdEnv trdenv,TrdMarket trdMarket) throws IOException{
		long accID  = -1;
		for(TrdAcc acc : trdAccs){
			if (acc.getTrdEnv()==trdenv.getNumber()&&acc.getTrdMarketAuthList(0)==trdMarket.getNumber())
				accID = acc.getAccID();
		}
		if (accID==-1)
			throw new IOException("无该市场交易帐号");
		return accID;
	}
}
