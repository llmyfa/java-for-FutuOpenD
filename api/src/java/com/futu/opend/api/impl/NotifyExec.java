package com.futu.opend.api.impl;


import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.Notify;
import com.futu.opend.api.protobuf.Notify.Response;
import com.google.protobuf.InvalidProtocolBufferException;


class NotifyExec implements IUpdateExecutor<Notify.Response>{

	private Notify.Response response;
	
	private IUpdateCallBack<Notify.Response> callback;
	
	public final static int nProtoID =  1003;
	
	public final static int nUpdateProtoID = 1003;
	
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

	@Override
	public Response parse(ProtoBufPackage pack)throws InvalidProtocolBufferException {
		return Notify.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(Notify.Response res) {
		if (callback!=null)
			callback.callback(res);
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}
