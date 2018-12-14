package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotGetHoldingChangeList;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotGetHoldingChangeListExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	private int holderCategory;
	private String beginTime;
	private String endTime;
	
	private QotGetHoldingChangeList.Response response;
	
	public final static int nProtoID = 3208;
	
	public QotGetHoldingChangeListExec(QotMarket market,String symbol,int holderCategory,String beginTime,String endTime){
		this.market = market;
		this.symbol = symbol;
		this.holderCategory = holderCategory;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetHoldingChangeList.Request.Builder request = QotGetHoldingChangeList.Request.newBuilder();
		QotGetHoldingChangeList.C2S.Builder c2s = QotGetHoldingChangeList.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setHolderCategory(holderCategory);
		if (beginTime!=null)
			c2s.setBeginTime(beginTime);
		if (endTime!=null)
			c2s.setEndTime(endTime);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetHoldingChangeList.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetHoldingChangeList.Response getValue() {
		return response;
	}

}
