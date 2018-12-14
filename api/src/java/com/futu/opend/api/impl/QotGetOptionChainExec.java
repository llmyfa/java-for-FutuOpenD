package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetOptionChain;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotGetOptionChainExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	private String beginTime;
	private String endTime;
	private int type;
	private int condition;
	
	private QotGetOptionChain.Response response;
	
	public final static int nProtoID = 3209;
	
	public QotGetOptionChainExec(QotMarket market,String symbol,String beginTime,String endTime,int type,int condition){
		this.market = market;
		this.symbol = symbol;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.type = type;
		this.condition = condition;
	}
	
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetOptionChain.Request.Builder request = QotGetOptionChain.Request.newBuilder();
		QotGetOptionChain.C2S.Builder c2s = QotGetOptionChain.C2S.newBuilder();
		c2s.setOwner(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setBeginTime(beginTime);
		c2s.setEndTime(endTime);
		if (type>=0)
			c2s.setType(type);
		if (this.condition>=0)
			c2s.setCondition(condition);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetOptionChain.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetOptionChain.Response getValue() {
		return response;
	}

}
