package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotGetSubInfo;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetSubInfoExec implements IExecutor{
	
	private boolean isReqAllConn;
	
	private QotGetSubInfo.Response response;
	
	public final static int nProtoID =  3003;
	
	QotGetSubInfoExec(boolean isReqAllConn){
		this.isReqAllConn = isReqAllConn;
	}
	
	public ProtoBufPackage buildPackage(){
		QotGetSubInfo.Request.Builder request = QotGetSubInfo.Request.newBuilder();
		QotGetSubInfo.C2S.Builder c2s = QotGetSubInfo.C2S.newBuilder();
		c2s.setIsReqAllConn(isReqAllConn);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack)throws InvalidProtocolBufferException {
		response = QotGetSubInfo.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetSubInfo.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
