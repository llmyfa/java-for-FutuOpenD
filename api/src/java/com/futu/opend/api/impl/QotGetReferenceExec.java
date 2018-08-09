package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetReference;
import com.futu.opend.api.protobuf.QotGetReference.ReferenceType;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetReferenceExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	
	private ReferenceType referenceType;
	
	private QotGetReference.Response response;
	
	public final static int nProtoID = 3206;

	public QotGetReferenceExec(QotMarket market,String symbol,ReferenceType referenceType){
		this.market = market;
		this.symbol = symbol;
		this.referenceType = referenceType;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetReference.Request.Builder request = QotGetReference.Request.newBuilder();
		QotGetReference.C2S.Builder c2s = QotGetReference.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setReferenceType(referenceType.getNumber());
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetReference.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetReference.Response getValue() {
		return response;
	}

}
