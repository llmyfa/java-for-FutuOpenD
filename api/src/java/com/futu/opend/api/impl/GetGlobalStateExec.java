package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.GetGlobalState;
import com.google.protobuf.InvalidProtocolBufferException;

class GetGlobalStateExec implements IExecutor{
	
	public final static int nProtoID = 1002;
	
	private long futuUserID;
	
	GetGlobalState.Response response;
	
	public GetGlobalStateExec(long futuUserID){
		this.futuUserID = futuUserID;
	}
	
	public ProtoBufPackage buildPackage(){
		GetGlobalState.Request.Builder request = GetGlobalState.Request.newBuilder();
		GetGlobalState.C2S.Builder c2s = GetGlobalState.C2S.newBuilder();
		c2s.setUserID(futuUserID);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack)throws InvalidProtocolBufferException {
		response = GetGlobalState.Response.parseFrom(pack.getBodys());
	}

	@Override
	public GetGlobalState.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}