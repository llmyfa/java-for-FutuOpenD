package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdGetHistoryOrderList;
import com.futu.opend.api.protobuf.TrdGetOrderList;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetHistoryOrderListExec implements IExecutor{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private TrdFilterConditions filterConditions;
	private Integer[] filterStatusList;
	
	private TrdGetHistoryOrderList.Response response;
	
	public final static int nProtoID = 2221;

	
	public TrdGetHistoryOrderListExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,TrdFilterConditions filterConditions,Integer[] filterStatusList){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.filterConditions = filterConditions;
		this.filterStatusList = filterStatusList;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetOrderList.Request.Builder request = TrdGetOrderList.Request.newBuilder();
		TrdGetOrderList.C2S.Builder c2s = TrdGetOrderList.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		c2s.setFilterConditions(filterConditions);
		if (filterStatusList!=null){
			for(int orderID : filterStatusList)
				c2s.addFilterStatusList(orderID);
		}
		
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetHistoryOrderList.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetHistoryOrderList.Response getValue() {
		return response;
	}

}


