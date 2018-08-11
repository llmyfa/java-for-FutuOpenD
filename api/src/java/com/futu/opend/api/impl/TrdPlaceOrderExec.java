package com.futu.opend.api.impl;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.Common.PacketID;
import com.futu.opend.api.protobuf.TrdCommon.OrderType;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdCommon.TrdSide;
import com.futu.opend.api.protobuf.TrdPlaceOrder;
import com.futu.opend.api.protobuf.TrdUpdateOrder;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdPlaceOrderExec implements IUpdateExecutor<TrdUpdateOrder.Response>{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	private TrdSide trdSide;
	private OrderType orderType;
	private String code;
	private double qty;
	private double price;
	private Boolean adjustPrice;
	private Double adjustSideAndLimit;
	private long connID;
	
	private TrdPlaceOrder.Response response;
	
	private IUpdateCallBack<TrdUpdateOrder.Response> callback;
	
	public final static int nProtoID = 2202;
	
	public final static int nUpdateProtoID = 2208;
	
	public TrdPlaceOrderExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,TrdSide trdSide,OrderType orderType,String code,double qty,double price,Boolean adjustPrice,Double adjustSideAndLimit,long connID,IUpdateCallBack<TrdUpdateOrder.Response> callback){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.trdSide = trdSide;
		this.orderType = orderType;
		this.code = code;
		this.qty = qty;
		this.price = price;
		this.adjustPrice = adjustPrice;
		this.adjustSideAndLimit = adjustSideAndLimit;
		this.connID = connID;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdPlaceOrder.Request.Builder request = TrdPlaceOrder.Request.newBuilder();
		TrdPlaceOrder.C2S.Builder c2s = TrdPlaceOrder.C2S.newBuilder();
		
		ProtoBufPackage pack = new ProtoBufPackage();
		
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		
		PacketID.Builder packetID = PacketID.newBuilder();
		packetID.setConnID(connID);
		packetID.setSerialNo(pack.getnSerialNo());
		
		c2s.setPacketID(packetID.build());
		c2s.setTrdSide(trdSide.getNumber());
		c2s.setOrderType(orderType.getNumber());
		c2s.setCode(code);
		c2s.setQty(qty);
		c2s.setPrice(price);
		if (adjustPrice!=null)
			c2s.setAdjustPrice(adjustPrice);
		if (adjustSideAndLimit!=null)
			c2s.setAdjustSideAndLimit(adjustSideAndLimit);
		
		
		request.setC2S(c2s);
		
		pack.setnProtoID(nProtoID);
		
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdPlaceOrder.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdPlaceOrder.Response getValue() {
		return response;
	}

	@Override
	public TrdUpdateOrder.Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return TrdUpdateOrder.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(TrdUpdateOrder.Response res) {
		if (callback!=null){
			callback.callback(res);
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}


