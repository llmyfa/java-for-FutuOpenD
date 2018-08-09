package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetRehab;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetRehabExec implements IExecutor{

	private QotMarket market;
	private String[] symbols;
	
	public final static int nProtoID = 3102;
	
	private QotGetRehab.Response response;
	
	public QotGetRehabExec(QotMarket market,String[] symbols){
		this.market = market;
		this.symbols = symbols;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetRehab.Request.Builder request = QotGetRehab.Request.newBuilder();
		QotGetRehab.C2S.Builder c2s = QotGetRehab.C2S.newBuilder();
		for(String symbol : symbols)
			c2s.addSecurityList(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetRehab.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetRehab.Response getValue() {
		return response;
	}

}
