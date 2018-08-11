package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdCommon.OrderType;
import com.futu.opend.api.protobuf.TrdGetMaxTrdQtys;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetMaxTrdQtysExec implements IExecutor{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private OrderType orderType;
	private String code;
	private double price;
	private Long orderID;
	private Boolean adjustPrice;
	private Double adjustSideAndLimit;
	
	private TrdGetMaxTrdQtys.Response response;
	
	public final static int nProtoID =  2111;

	
	public TrdGetMaxTrdQtysExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,OrderType orderType,String code,double price,Long orderID,Boolean adjustPrice,Double adjustSideAndLimit){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.orderType = orderType;
		this.code = code;
		this.price = price;
		this.orderID = orderID;
		this.adjustPrice = adjustPrice;
		this.adjustSideAndLimit = adjustSideAndLimit;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetMaxTrdQtys.Request.Builder request = TrdGetMaxTrdQtys.Request.newBuilder();
		TrdGetMaxTrdQtys.C2S.Builder c2s = TrdGetMaxTrdQtys.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		c2s.setOrderType(orderType.getNumber());
		c2s.setCode(code);
		c2s.setPrice(price);
		if (orderID!=null)
			c2s.setOrderID(orderID);
		if (adjustPrice!=null)
			c2s.setAdjustPrice(adjustPrice);
		if (adjustSideAndLimit!=null)
			c2s.setAdjustSideAndLimit(adjustSideAndLimit);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetMaxTrdQtys.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetMaxTrdQtys.Response getValue() {
		return response;
	}

}


