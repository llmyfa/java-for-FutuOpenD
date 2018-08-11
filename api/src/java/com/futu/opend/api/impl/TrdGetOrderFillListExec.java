package com.futu.opend.api.impl;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdGetOrderFillList;
import com.futu.opend.api.protobuf.TrdUpdateOrderFill;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetOrderFillListExec implements IUpdateExecutor<TrdUpdateOrderFill.Response>{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private TrdFilterConditions filterConditions;

	private TrdGetOrderFillList.Response response;
	
	private IUpdateCallBack<TrdUpdateOrderFill.Response> callback;
	
	public final static int nProtoID = 2211;
	
	public final static int nUpdateProtoID = 2218;

	
	public TrdGetOrderFillListExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,TrdFilterConditions filterConditions,IUpdateCallBack<TrdUpdateOrderFill.Response> callback){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.filterConditions = filterConditions;
		this.callback = callback;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetOrderFillList.Request.Builder request = TrdGetOrderFillList.Request.newBuilder();
		TrdGetOrderFillList.C2S.Builder c2s = TrdGetOrderFillList.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		if (filterConditions!=null)
			c2s.setFilterConditions(filterConditions);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetOrderFillList.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetOrderFillList.Response getValue() {
		return response;
	}

	@Override
	public TrdUpdateOrderFill.Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return TrdUpdateOrderFill.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(TrdUpdateOrderFill.Response res) {
		if (callback!=null){
			callback.callback(res);
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}