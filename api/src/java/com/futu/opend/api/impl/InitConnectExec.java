package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.InitConnect;
import com.google.protobuf.InvalidProtocolBufferException;

class InitConnectExec implements IExecutor{
	
	public final static int nProtoID = 1001;
	
	public ProtoBufPackage buildPackage(){
		InitConnect.Request.Builder request = InitConnect.Request.newBuilder();
		InitConnect.C2S.Builder c2s = InitConnect.C2S.newBuilder();
		c2s.setClientID("test123456");
		c2s.setClientVer(307);
		c2s.setRecvNotify(true);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack)throws InvalidProtocolBufferException {
		InitConnect.Response response = InitConnect.Response.parseFrom(pack.getBodys());
		System.out.println(response.toString());
	}

	@Override
	public InitConnect.Response getValue() {
		return null;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
