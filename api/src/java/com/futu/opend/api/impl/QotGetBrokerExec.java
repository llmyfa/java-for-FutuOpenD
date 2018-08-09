package com.futu.opend.api.impl;

import com.futu.opend.api.Brokers;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetBroker;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetBrokerExec implements IUpdateExecutor<QotGetBroker.Response>{

	private QotMarket market;
	private String symbol;
	
	private QotGetBroker.Response response;
	
	private IUpdateCallBack<Brokers> callback;
	
	public final static int nProtoID = 3014;
	
	public final static int nUpdateProtoID = 3015;
	
	public QotGetBrokerExec(QotMarket market,String symbol,IUpdateCallBack<Brokers> callback){
		this.market = market;
		this.symbol = symbol;
		this.callback = callback;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetBroker.Request.Builder request = QotGetBroker.Request.newBuilder();
		QotGetBroker.C2S.Builder c2s = QotGetBroker.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetBroker.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetBroker.Response getValue() {
		return response;
	}

	@Override
	public QotGetBroker.Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return QotGetBroker.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(QotGetBroker.Response res) {
		if (callback!=null){
			if (res.getS2C().getSecurity().getMarket()==this.market.getNumber()&&res.getS2C().getSecurity().getCode().equals(this.symbol)){
				Brokers brokers = new Brokers();
				brokers.setBrokerAskList(res.getS2C().getBrokerAskListList());
				brokers.setBrokerBidList(res.getS2C().getBrokerBidListList());
				callback.callback(brokers);
			}
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}
	
}
