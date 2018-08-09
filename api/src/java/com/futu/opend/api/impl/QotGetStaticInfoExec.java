package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotCommon.SecurityType;
import com.futu.opend.api.protobuf.QotGetStaticInfo;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetStaticInfoExec implements IExecutor{

	private QotMarket market;
	private String[] symbols;
	private SecurityType secType;
	private QotGetStaticInfo.Response response;
	
	public final static int nProtoID = 3202; 
	
	public QotGetStaticInfoExec(QotMarket market,String[] symbols,SecurityType secType){
		this.market = market;
		this.symbols = symbols;
		this.secType = secType;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetStaticInfo.Request.Builder request = QotGetStaticInfo.Request.newBuilder();
		QotGetStaticInfo.C2S.Builder c2s = QotGetStaticInfo.C2S.newBuilder();
		c2s.setMarket(market.getNumber());
		c2s.setSecType(secType.getNumber());
		if (symbols!=null&&symbols.length>0){
			for(String symbol : symbols)
				c2s.addSecurityList(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		}
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetStaticInfo.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetStaticInfo.Response getValue() {
		return response;
	}

}
