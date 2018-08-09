package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.QotGetSecuritySnapshot;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetSecuritySnapshotExec implements IExecutor{

	private QotMarket market;
	private String[] symbols;
	
	private QotGetSecuritySnapshot.Response response;
	
	public final static int nProtoID = 3203;
	
	public QotGetSecuritySnapshotExec(QotMarket market,String[] symbols){
		this.market = market;
		this.symbols = symbols;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetSecuritySnapshot.Request.Builder request = QotGetSecuritySnapshot.Request.newBuilder();
		QotGetSecuritySnapshot.C2S.Builder c2s = QotGetSecuritySnapshot.C2S.newBuilder();
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
		response = QotGetSecuritySnapshot.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetSecuritySnapshot.Response getValue() {
		return response;
	}

}
