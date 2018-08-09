package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.KeepAlive;
import com.google.protobuf.InvalidProtocolBufferException;

class KeepAliveExec implements IExecutor{
	
	public final static int nProtoID = 1004;
	
	public ProtoBufPackage buildPackage(){
		KeepAlive.Request.Builder request = KeepAlive.Request.newBuilder();
		KeepAlive.C2S.Builder c2s = KeepAlive.C2S.newBuilder();
		c2s.setTime(System.currentTimeMillis());
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		KeepAlive.Response response = KeepAlive.Response.parseFrom(pack.getBodys());
		System.out.println(response.toString());
	}

	@Override
	public KeepAlive.Response getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
