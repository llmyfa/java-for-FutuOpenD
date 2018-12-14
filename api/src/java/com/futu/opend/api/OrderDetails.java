package com.futu.opend.api;

import com.futu.opend.api.protobuf.QotCommon.OrderDetail;

public class OrderDetails {
	private OrderDetail OrderDetailBid;//买盘
	private OrderDetail OrderDetailAsk;//卖盘
	public OrderDetail getOrderDetailBid() {
		return OrderDetailBid;
	}
	public void setOrderDetailBid(OrderDetail orderDetailBid) {
		OrderDetailBid = orderDetailBid;
	}
	public OrderDetail getOrderDetailAsk() {
		return OrderDetailAsk;
	}
	public void setOrderDetailAsk(OrderDetail orderDetailAsk) {
		OrderDetailAsk = orderDetailAsk;
	}
	
}
