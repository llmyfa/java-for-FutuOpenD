package com.futu.opend.api.impl;

import java.util.List;

import com.futu.opend.api.protobuf.QotGetOwnerPlate;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotGetOwnerPlateExec implements IExecutor{
	
	private QotMarket market;
	private String[] symbols;
	
	private QotGetOwnerPlate.Response response;
	
	public final static int nProtoID = 3207;
	
	public QotGetOwnerPlateExec(QotMarket market,String[] symbols){
		this.market = market;
		this.symbols = symbols;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}
	
	@Override
	public ProtoBufPackage buildPackage() {
		QotGetOwnerPlate.Request.Builder request = QotGetOwnerPlate.Request.newBuilder();
		QotGetOwnerPlate.C2S.Builder c2s = QotGetOwnerPlate.C2S.newBuilder();
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
		response = QotGetOwnerPlate.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetOwnerPlate.Response getValue() {
		return response;
	}

}
