package com.futu.opend.api;

import java.util.List;

import com.futu.opend.api.protobuf.QotCommon.OrderBook;

public class OrderBooks{
	private List<OrderBook> orderBookBidList;//买盘
	private List<OrderBook> orderBookAskList;//卖盘
	public List<OrderBook> getOrderBookBidList() {
		return orderBookBidList;
	}
	public void setOrderBookBidList(List<OrderBook> orderBookBidList) {
		this.orderBookBidList = orderBookBidList;
	}
	public List<OrderBook> getOrderBookAskList() {
		return orderBookAskList;
	}
	public void setOrderBookAskList(List<OrderBook> orderBookAskList) {
		this.orderBookAskList = orderBookAskList;
	}	
}
