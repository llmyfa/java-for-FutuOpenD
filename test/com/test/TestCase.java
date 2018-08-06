package com.test;

import java.util.List;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.Session;
import com.futu.opend.api.impl.FutuOpenD;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.SubType;

public class TestCase {

	public static void main(String[] avgs) {
		Session session = FutuOpenD.openSession("localhost", 11111);
		try {
			
			//订阅股票
			QotSub.Response value = session.qotSub(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker});
			System.out.println(value);
			
			//基本报价，不处理推送
			QotGetBasicQot.Response value2 = session.stockBasicInfo(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},null);
			System.out.println(value2);
			
			//逐笔，并处理推送
			QotGetTicker.Response value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00700", 2,new IUpdateCallBack<List<Ticker>>() {
				@Override
				public void callback(List<Ticker> res) {
					System.out.println("逐笔推送:00700");
					for(Ticker qot : res)
						System.out.println(qot.toString());
					
				}
			});
			System.out.println(value3);
			
			//逐笔，无处理推送
			value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00005", 2,null);
			System.out.println(value3);
			
			//订阅推送(基本报价与逐笔)
			QotRegQotPush.Response value4 = session.QotRegQotPushExec(QotMarket.QotMarket_HK_Security, new String[]{"00700"}, new SubType[]{SubType.SubType_Ticker});
			System.out.println(value4);
			
			Thread.sleep(60*1000);
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
