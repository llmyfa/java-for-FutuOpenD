package com.futu.opend.api.impl;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.OrderDetails;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetOrderDetail;
import com.futu.opend.api.protobuf.QotUpdateOrderDetail;
import com.futu.opend.api.protobuf.QotUpdateOrderDetail.Response;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotGetOrderDetailExec implements IUpdateExecutor<QotUpdateOrderDetail.Response>{

	private QotMarket market;
	private String symbol;
	
	private QotGetOrderDetail.Response response;
	
	private IUpdateCallBack<OrderDetails> callback;
	
	public final static int nProtoID = 3016;
	public final static int nUpdateProtoID = 3017;
	
	public QotGetOrderDetailExec(QotMarket market,String symbol,IUpdateCallBack<OrderDetails> callback){
		this.market = market;
		this.symbol = symbol;
		this.callback = callback;
	}
	
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetOrderDetail.Request.Builder request = QotGetOrderDetail.Request.newBuilder();
		QotGetOrderDetail.C2S.Builder c2s = QotGetOrderDetail.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetOrderDetail.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetOrderDetail.Response getValue() {
		return response;
	}


	@Override
	public Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return QotUpdateOrderDetail.Response.parseFrom(pack.getBodys());
	}


	@Override
	public void handler(Response res) {
		if (callback!=null){
			if (res.getS2C().getSecurity().getMarket()==this.market.getNumber()&&res.getS2C().getSecurity().getCode().equals(this.symbol)){
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setOrderDetailAsk(res.getS2C().getOrderDetailAsk());
				orderDetails.setOrderDetailBid(res.getS2C().getOrderDetailBid());
				callback.callback(orderDetails);
			}
		}
	}


	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}

