## java-for-FutuOpenD
FutuQuant量化接口的Java版本

## 运行
- jdk1.8+
- 导入eclipse
- 运行TestCase


## 功能
- FutuOpenD客户端为1.0.2
- 完成基础定义与行情协议部分
- 交易协议(待完成)

## 更新
- 2018-08-09 完成基础定义与行情协议接口
- 2018-08-06 完成基础功能，跑通流程 
	
## API举例
API详细调用例程参见 com.test.TestCase
		
Session session = FutuOpenD.openSession("localhost", 11111);

//订阅股票
session.qotSub(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},new SubType[]	{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});

//订阅推送
session.qotRegQotPush(QotMarket.QotMarket_HK_Security, new String[]{"00700"}, new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});

//分时(含回调)
session.qotGetRT(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<List<TimeShare>>(){
	@Override
		public void callback(List<TimeShare> res) {
	}
});
		
session.close();
		
## 参考
[FutuQuant协议接口指南](https://futunnopen.github.io/futuquant/protocol/intro.html)

