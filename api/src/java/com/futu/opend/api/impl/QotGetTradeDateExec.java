package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotGetTradeDate;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotGetTradeDateExec implements IExecutor{

	public final static int nProtoID = 3200;
	
	private QotGetTradeDate.Response response;
	
	private QotMarket market;
	
	private String beginTime;
	private String endTime;
	
	public QotGetTradeDateExec(QotMarket market,String beginTime,String endTime){
		this.market = market;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetTradeDate.Request.Builder request = QotGetTradeDate.Request.newBuilder();
		QotGetTradeDate.C2S.Builder c2s = QotGetTradeDate.C2S.newBuilder();
		c2s.setMarket(market.getNumber());
		c2s.setBeginTime(beginTime);
		c2s.setEndTime(endTime);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetTradeDate.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetTradeDate.Response getValue() {
		return response;
	}

}
