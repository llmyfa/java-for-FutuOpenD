package com.futu.opend.api;

import java.io.IOException;
import com.futu.opend.api.protobuf.TrdCommon.OrderType;
import com.futu.opend.api.protobuf.TrdGetFunds;
import com.futu.opend.api.protobuf.TrdGetHistoryOrderFillList;
import com.futu.opend.api.protobuf.TrdGetHistoryOrderList;
import com.futu.opend.api.protobuf.TrdGetMaxTrdQtys;
import com.futu.opend.api.protobuf.TrdGetOrderFillList;
import com.futu.opend.api.protobuf.TrdGetOrderList;
import com.futu.opend.api.protobuf.TrdGetPositionList;
import com.futu.opend.api.protobuf.TrdModifyOrder;
import com.futu.opend.api.protobuf.TrdPlaceOrder;
import com.futu.opend.api.protobuf.TrdUnlockTrade;
import com.futu.opend.api.protobuf.TrdUpdateOrder;
import com.futu.opend.api.protobuf.TrdUpdateOrderFill;
import com.futu.opend.api.protobuf.TrdCommon.ModifyOrderOp;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdCommon.TrdSide;

public interface TraderSession {
	
	/**
	 * 获取账户资金
	 * @param trdenv
	 * @param trdMarket
	 * @return
	 * @throws IOException
	 */
	TrdGetFunds.Response trdGetFunds(TrdMarket trdMarket) throws IOException;
	
	/**
	 * 获取持仓列表
	 * @param trdenv
	 * @param trdMarket
	 * @param filterConditions
	 * @param filterPLRatioMin
	 * @param filterPLRatioMax
	 * @return
	 * @throws IOException
	 */
	TrdGetPositionList.Response trdGetPositionList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Double filterPLRatioMin,Double filterPLRatioMax) throws IOException;
	
	/**
	 * 获取最大交易数量,模拟盘不支持
	 * @param trdenv
	 * @param trdMarket
	 * @param orderType
	 * @param code
	 * @param price
	 * @param orderID
	 * @param adjustPrice
	 * @param adjustSideAndLimit
	 * @return
	 * @throws IOException
	 */
	TrdGetMaxTrdQtys.Response trdGetMaxTrdQtys(TrdMarket trdMarket,OrderType orderType,String code,double price,Long orderID,Boolean adjustPrice,Double adjustSideAndLimit) throws IOException;
	
	/**
	 * 获取订单列表
	 * @param trdMarket
	 * @param filterConditions
	 * @param filterStatusList
	 * @return
	 * @throws IOException
	 */
	TrdGetOrderList.Response trdGetOrderList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Integer[] filterStatusList) throws IOException;
	
	/**
	 * 获取历史订单列表
	 * @param trdMarket
	 * @param filterConditions
	 * @param filterStatusList
	 * @return
	 * @throws IOException
	 */
	TrdGetHistoryOrderList.Response trdGetHistoryOrderList(TrdMarket trdMarket,TrdFilterConditions filterConditions,Integer[] filterStatusList) throws IOException;
	
	/**
	 * 下单(含推送订单更新)
	 * @param trdMarket
	 * @param trdSide
	 * @param orderType
	 * @param code
	 * @param qty
	 * @param price
	 * @param adjustPrice
	 * @param adjustSideAndLimit
	 * @param callback
	 * @return
	 * @throws IOException
	 */
	TrdPlaceOrder.Response trdPlaceOrder(TrdMarket trdMarket,TrdSide trdSide,OrderType orderType,String code,double qty,double price,Boolean adjustPrice,Double adjustSideAndLimit,IUpdateCallBack<TrdUpdateOrder.Response> callback) throws IOException;
	
	/**
	 * 修改订单(改价、改量、改状态等)
	 * @param trdMarket
	 * @param orderID
	 * @param modifyOrderOp
	 * @param forAll
	 * @param qty
	 * @param price
	 * @param adjustPrice
	 * @param adjustSideAndLimit
	 * @return
	 * @throws IOException
	 */
	TrdModifyOrder.Response trdModifyOrder(TrdMarket trdMarket,long orderID,ModifyOrderOp modifyOrderOp,Boolean forAll,Double qty,Double price,Boolean adjustPrice,Double adjustSideAndLimit) throws IOException;
	
	/**
	 * 获取成交列表
	 * @param trdMarket
	 * @param filterConditions
	 * @return
	 * @throws IOException
	 */
	TrdGetOrderFillList.Response trdGetOrderFillList(TrdMarket trdMarket,TrdFilterConditions filterConditions,IUpdateCallBack<TrdUpdateOrderFill.Response> callback) throws IOException;
	
	/**
	 * 获取历史成交列表
	 * @param trdMarket
	 * @param filterConditions
	 * @return
	 * @throws IOException
	 */
	TrdGetHistoryOrderFillList.Response trdGetHistoryOrderFillList(TrdMarket trdMarket,TrdFilterConditions filterConditions) throws IOException;
	
	/**
	 * 锁定交易
	 * @return
	 * @throws IOException
	 */
	TrdUnlockTrade.Response trdlockTrade() throws IOException;
}
