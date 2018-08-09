package com.futu.opend.api;

import java.util.List;

import com.futu.opend.api.protobuf.QotCommon.Broker;

public class Brokers{
	private List<Broker> brokerAskList;//经纪Ask(卖)盘
	private List<Broker> brokerBidList;//经纪Bid(买)盘
	public List<Broker> getBrokerAskList() {
		return brokerAskList;
	}
	public void setBrokerAskList(List<Broker> brokerAskList) {
		this.brokerAskList = brokerAskList;
	}
	public List<Broker> getBrokerBidList() {
		return brokerBidList;
	}
	public void setBrokerBidList(List<Broker> brokerBidList) {
		this.brokerBidList = brokerBidList;
	}
	
}