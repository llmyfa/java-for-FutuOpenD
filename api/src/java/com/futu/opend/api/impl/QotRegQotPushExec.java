package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.google.protobuf.InvalidProtocolBufferException;

class QotRegQotPushExec implements IExecutor{

	private QotRegQotPush.Response response;
	
	private QotMarket market;
	private SubType[] subTypes;
	private String[] symbols;
	private boolean isRegOrUnReg = true;
	private boolean isFirstPush = true;
	
	public final static int nProtoID = 3002;
	
	public QotRegQotPushExec(QotMarket market,String[] symbols,SubType[] subTypes){
		this.market = market;
		this.subTypes = subTypes;
		this.symbols = symbols;
	}
	
	public QotRegQotPushExec(QotMarket market,String[] symbols,SubType[] subTypes,boolean isFirstPush,boolean isRegOrUnReg){
		this.market = market;
		this.subTypes = subTypes;
		this.symbols = symbols;
		this.isFirstPush = isFirstPush;
		this.isRegOrUnReg = isRegOrUnReg;
	}
	
	
	@Override
	public ProtoBufPackage buildPackage() {
		QotRegQotPush.Request.Builder request = QotRegQotPush.Request.newBuilder();
		QotRegQotPush.C2S.Builder c2s = QotRegQotPush.C2S.newBuilder();
		for(String symbol : symbols){
			Security.Builder security = Security.newBuilder();
			security.setMarket(market.getNumber());
			security.setCode(symbol);
			c2s.addSecurityList(security);
		}
		for(SubType subType : subTypes)
			c2s.addSubTypeList(subType.getNumber());
		c2s.setIsRegOrUnReg(isRegOrUnReg);
		c2s.setIsFirstPush(isFirstPush);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotRegQotPush.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotRegQotPush.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
