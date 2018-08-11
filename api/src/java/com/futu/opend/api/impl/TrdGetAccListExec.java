package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdGetAccList;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetAccListExec implements IExecutor{

	private long futuUserID;
	
	private TrdGetAccList.Response response;
	
	public final static int nProtoID = 2001;
	
	public TrdGetAccListExec(long futuUserID){
		this.futuUserID = futuUserID;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetAccList.Request.Builder request = TrdGetAccList.Request.newBuilder();
		TrdGetAccList.C2S.Builder c2s = TrdGetAccList.C2S.newBuilder();
		c2s.setUserID(futuUserID);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetAccList.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetAccList.Response getValue() {
		return response;
	}

}
