package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdModifyOrder;
import com.futu.opend.api.protobuf.Common.PacketID;
import com.futu.opend.api.protobuf.TrdCommon.ModifyOrderOp;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdModifyOrderExec implements IExecutor{

	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	private long orderID;
	private ModifyOrderOp modifyOrderOp;
	private Boolean forAll;
	private Double qty;
	private Double price;
	private Boolean adjustPrice;
	private Double adjustSideAndLimit;
	private long connID;
	
	private TrdModifyOrder.Response response;
	
	public final static int nProtoID = 2205;
	
	public TrdModifyOrderExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,long orderID,ModifyOrderOp modifyOrderOp,Boolean forAll,Double qty,Double price,Boolean adjustPrice,Double adjustSideAndLimit,long connID){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.orderID = orderID;
		this.modifyOrderOp = modifyOrderOp;
		this.forAll = forAll;
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
		TrdModifyOrder.Request.Builder request = TrdModifyOrder.Request.newBuilder();
		TrdModifyOrder.C2S.Builder c2s = TrdModifyOrder.C2S.newBuilder();
		
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
		c2s.setOrderID(orderID);
		c2s.setModifyOrderOp(modifyOrderOp.getNumber());
		if (forAll!=null)
			c2s.setForAll(forAll);
		if (qty!=null)
			c2s.setQty(qty);
		if (price!=null)
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
		response = TrdModifyOrder.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdModifyOrder.Response getValue() {
		return response;
	}
}
