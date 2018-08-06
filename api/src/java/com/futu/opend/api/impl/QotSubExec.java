package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotSub;
import com.google.protobuf.InvalidProtocolBufferException;

class QotSubExec implements IExecutor{

	private QotSub.Response response;
	
	private QotMarket market;
	private String[] symbols;
	private SubType[] subTypes;
	private boolean isSubOrUnSub;
	
	public final static int nProtoID = 3001;
	
	public QotSubExec(QotMarket market,String[] symbols,SubType[] subTypes,boolean isSubOrUnSub){
		this.market = market;
		this.symbols = symbols;
		this.subTypes = subTypes;
		this.isSubOrUnSub = isSubOrUnSub;
	}
	
	public ProtoBufPackage buildPackage(){
		QotSub.Request.Builder request = QotSub.Request.newBuilder();
		QotSub.C2S.Builder c2s = QotSub.C2S.newBuilder();
		for(String symbol : symbols)
			c2s.addSecurityList(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setIsSubOrUnSub(isSubOrUnSub);
		for(SubType subType : subTypes)
			c2s.addSubTypeList(subType.getNumber());
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}


	@Override
	public void execute(ProtoBufPackage pack)
			throws InvalidProtocolBufferException {
		response = QotSub.Response.parseFrom(pack.getBodys());
	}


	public QotSub.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
