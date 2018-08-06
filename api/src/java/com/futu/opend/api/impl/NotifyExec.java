package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.Notify;
import com.google.protobuf.InvalidProtocolBufferException;

class NotifyExec implements IExecutor{

	private Notify.Response response;
	
	public final static int nProtoID =  1003;
	
	@Override
	public ProtoBufPackage buildPackage() {
		return null;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = Notify.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public Notify.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

}
