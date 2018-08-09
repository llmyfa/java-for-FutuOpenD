package com.futu.opend.api.impl;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.OrderBooks;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetOrderBook;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetOrderBookExec implements IUpdateExecutor<QotGetOrderBook.Response>{

	public final static int nProtoID = 3012;
	
	public final static int nUpdateProtoID = 3013;
	
	private QotMarket market;
	private String symbol;
	private int num;
	
	private QotGetOrderBook.Response response;
	
	private IUpdateCallBack<OrderBooks> callback;
	
	public QotGetOrderBookExec(QotMarket market,String symbol,int num,IUpdateCallBack<OrderBooks> callback){
		this.market = market;
		this.symbol = symbol;
		this.num = num;
		if (this.num>10)
			this.num = 10;
		this.callback = callback;
	}

	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetOrderBook.Request.Builder request = QotGetOrderBook.Request.newBuilder();
		QotGetOrderBook.C2S.Builder c2s = QotGetOrderBook.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setNum(num);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetOrderBook.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetOrderBook.Response getValue() {
		return response;
	}

	@Override
	public QotGetOrderBook.Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return QotGetOrderBook.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(QotGetOrderBook.Response res) {
		if (this.callback!=null){
			if (res.getS2C().getSecurity().getMarket()==this.market.getNumber()&&res.getS2C().getSecurity().getCode().equals(this.symbol)){
				OrderBooks orderBooks = new OrderBooks();
				orderBooks.setOrderBookBidList(res.getS2C().getOrderBookBidListList());
				orderBooks.setOrderBookAskList(res.getS2C().getOrderBookAskListList());
				callback.callback(orderBooks);
			}
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}
}


