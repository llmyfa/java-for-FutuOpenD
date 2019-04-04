package com.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.Session;
import com.futu.opend.api.TraderSession;
import com.futu.opend.api.impl.FutuOpenD;
import com.futu.opend.api.protobuf.TrdCommon.ModifyOrderOp;
import com.futu.opend.api.protobuf.TrdCommon.Order;
import com.futu.opend.api.protobuf.TrdCommon.OrderStatus;
import com.futu.opend.api.protobuf.TrdGetOrderList;
import com.futu.opend.api.protobuf.TrdUpdateOrder;
import com.futu.opend.api.protobuf.TrdCommon.OrderType;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdCommon.TrdSide;
import com.futu.opend.api.protobuf.TrdUpdateOrder.Response;

public class TraderTestCase {

private static Session session = null;
	
	long futuUserID = 297743;
	
	String pwdMD5 = "123456";//md5密码
	
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static TrdFilterConditions.Builder conditions = TrdFilterConditions.newBuilder();
	
	
	
	@BeforeClass
	public static void tearUp() throws IOException{
		session = FutuOpenD.openSession("localhost", 11111,false);//true,启用推送功能
		conditions.setBeginTime(format.format(new Date(2018,8,1)));
		conditions.setEndTime(format.format(new Date(2018,8,10)));
	}
	
	@AfterClass
	public static void tearDown() throws InterruptedException, IOException{
		//Thread.sleep(30*1000);   //取消注释启用推送功能
		session.close();
	}
	
	//获取账户资金
		@Test
		public void testTrdGetFunds()throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdGetFunds(TrdMarket.TrdMarket_HK));
			System.out.println(traderSession.trdGetFunds(TrdMarket.TrdMarket_CN));
			System.out.println(traderSession.trdGetFunds(TrdMarket.TrdMarket_US));
		}
		
		
		//获取持仓列表
		@Test
		public void testTrdGetPositionList()throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdGetPositionList(TrdMarket.TrdMarket_HK,null,null,null));
			System.out.println(traderSession.trdGetPositionList(TrdMarket.TrdMarket_CN,null,null,null));
			System.out.println(traderSession.trdGetPositionList(TrdMarket.TrdMarket_US,null,null,null));
		}
		
		//获取最大交易数量
		@Test
		public void testTrdGetMaxTrdQtys()throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdGetMaxTrdQtys(TrdMarket.TrdMarket_HK,OrderType.OrderType_Normal,"00700",200.0,null,null,null));
			System.out.println(traderSession.trdGetMaxTrdQtys(TrdMarket.TrdMarket_CN,OrderType.OrderType_Normal,"600519",200.0,null,null,null));
			System.out.println(traderSession.trdGetMaxTrdQtys(TrdMarket.TrdMarket_US,OrderType.OrderType_Normal,"AAPL",200.0,null,null,null));
		}
		
		
		//获取订单历史列表
		@Test
		public void testTrdGetHistoryOrderList() throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			
			
			System.out.println(traderSession.trdGetHistoryOrderList(TrdMarket.TrdMarket_HK,conditions.build(),null));
			System.out.println(traderSession.trdGetHistoryOrderList(TrdMarket.TrdMarket_CN,conditions.build(),null));
			System.out.println(traderSession.trdGetHistoryOrderList(TrdMarket.TrdMarket_US,conditions.build(),null));
		}
		//下单并推送
		@Test
		public void testTrdPlaceOrder() throws IOException, InterruptedException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdPlaceOrder(TrdMarket.TrdMarket_US,TrdSide.TrdSide_Buy,OrderType.OrderType_Normal,"AAPL",10,200,null,null, new IUpdateCallBack<TrdUpdateOrder.Response> (){
				@Override
				public void callback(Response res) {
					System.out.println(res);
				}
			}));
		}
		
		//下单，查单,改单，撤单
		@Test
		public void testTrdModifyOrder() throws IOException, InterruptedException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			
			System.out.println(traderSession.trdPlaceOrder(TrdMarket.TrdMarket_HK,TrdSide.TrdSide_Buy,OrderType.OrderType_Normal,"00700",100,200,null,null,null));
			System.out.println(traderSession.trdPlaceOrder(TrdMarket.TrdMarket_CN,TrdSide.TrdSide_Buy,OrderType.OrderType_Normal,"600519",200,690,null,null,null));
			System.out.println(traderSession.trdPlaceOrder(TrdMarket.TrdMarket_US,TrdSide.TrdSide_Buy,OrderType.OrderType_Normal,"AAPL",10,200,null,null,null));
			
			
			System.out.println(traderSession.trdGetOrderList(TrdMarket.TrdMarket_HK,null,null));
			System.out.println(traderSession.trdGetOrderList(TrdMarket.TrdMarket_CN,null,null));
			System.out.println(traderSession.trdGetOrderList(TrdMarket.TrdMarket_US,null,null));
			
			
			TrdGetOrderList.Response res = traderSession.trdGetOrderList(TrdMarket.TrdMarket_HK,null,null);
			for(Order order : res.getS2C().getOrderListList()){
				if (order.getOrderStatus()==OrderStatus.OrderStatus_Submitted_VALUE){
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_HK,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Normal,false,200.0,190.0,null,null));
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_HK,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Cancel,false,null,null,null,null));
				}
			}
			res = traderSession.trdGetOrderList(TrdMarket.TrdMarket_CN,null,null);
			for(Order order : res.getS2C().getOrderListList()){
				if (order.getOrderStatus()==OrderStatus.OrderStatus_Submitted_VALUE){
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_CN,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Normal,false,100.0,670.0,null,null));
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_CN,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Cancel,false,null,null,null,null));
				}
			}
			res = traderSession.trdGetOrderList(TrdMarket.TrdMarket_US,null,null);
			for(Order order : res.getS2C().getOrderListList()){
				if (order.getOrderStatus()==OrderStatus.OrderStatus_Submitted_VALUE){
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_US,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Normal,false,50.0,180.0,null,null));
					System.out.println(traderSession.trdModifyOrder(TrdMarket.TrdMarket_US,order.getOrderID(),ModifyOrderOp.ModifyOrderOp_Cancel,false,null,null,null,null));
				}
			}
		}
		
		//获取成交列表
		@Test
		public void testTrdGetOrderFillList() throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdGetOrderFillList(TrdMarket.TrdMarket_HK, null,null));
			System.out.println(traderSession.trdGetOrderFillList(TrdMarket.TrdMarket_CN, null,null));
			System.out.println(traderSession.trdGetOrderFillList(TrdMarket.TrdMarket_US, null,null));
		}
		
		//获取历史成交列表
		@Test
		public void testTrdGetHistoryOrderFillList() throws IOException{
			TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
			System.out.println(traderSession.trdGetHistoryOrderFillList(TrdMarket.TrdMarket_HK, conditions.build()));
			System.out.println(traderSession.trdGetHistoryOrderFillList(TrdMarket.TrdMarket_CN, conditions.build()));
			System.out.println(traderSession.trdGetHistoryOrderFillList(TrdMarket.TrdMarket_US, conditions.build()));
		}
}
