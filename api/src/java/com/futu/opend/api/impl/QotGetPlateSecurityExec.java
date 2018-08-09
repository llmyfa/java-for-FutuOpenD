package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.QotGetPlateSecurity;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetPlateSecurityExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	
	private QotGetPlateSecurity.Response response;
	
	public final static int nProtoID = 3205;
	
	 
	public QotGetPlateSecurityExec(QotMarket market,String symbol){
		this.market = market;
		this.symbol = symbol;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetPlateSecurity.Request.Builder request = QotGetPlateSecurity.Request.newBuilder();
		QotGetPlateSecurity.C2S.Builder c2s = QotGetPlateSecurity.C2S.newBuilder();
		c2s.setPlate(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetPlateSecurity.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetPlateSecurity.Response getValue() {
		return response;
	}

}
