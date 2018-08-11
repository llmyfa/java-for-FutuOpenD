package com.futu.opend.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.futu.opend.api.protobuf.InitConnect;
import com.google.protobuf.InvalidProtocolBufferException;

class Client {
	
	private Map<Integer,IExecutor> handlers = new java.util.concurrent.ConcurrentHashMap<Integer, IExecutor>();//包序列号，对应请求包和回包
	
	private long connID;
	private String ip;
	private int port;
	private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;
    private boolean keepAlive = true;
    private boolean over = false;//socket.close
    private boolean connected = false;
	
	public Client(){
		
	}
	
	public void open(String ip,int port){
		this.ip = ip;
		this.port = port;
		try {
			initConnection();
		}  catch (Exception e) {
		}
		new SocketThread().start();
		
		IExecutor handler = null;
		for(IExecutor exec : handlers.values()){
			if (exec.getnProtoID()==InitConnectExec.nProtoID){
				handler = exec;
				break;
			}
		}
		
		long startTime = System.currentTimeMillis();
		while(handler.getValue()==null){
			if ((System.currentTimeMillis() - startTime) > 30 * 1000)//超时
				break;
			sleepMillis(1);
		}
		InitConnect.Response res = (InitConnect.Response)handler.getValue();
		connID = res.getS2C().getConnID();
	}
	
	public Object execute(IExecutor handler) throws IOException {
		ProtoBufPackage pack = handler.buildPackage();
		handlers.put(pack.getnSerialNo(), handler);
		while(!over){
			if (connected){
				try {
					os.write(pack.pack());
					os.flush();
				} catch (NoSuchAlgorithmException e) {
				
				}
		    	break;
			}
			sleepMillis(1);
		}
		long startTime = System.currentTimeMillis();
		while(handler.getValue()==null){
			if ((System.currentTimeMillis() - startTime) > 30 * 1000)//超时
				break;
			sleepMillis(1);
		}
		return handler.getValue();
	}
	
	private void addHander(Integer nSerialNo,IExecutor handler){
		handlers.put(nSerialNo, handler);
	}
	
	public void close(){
		over = true;
	}
	
	/**
	 * 初始化协议
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void initConnection() throws UnknownHostException, IOException{
		socket = new Socket(ip, port);
        os = socket.getOutputStream();
        is = socket.getInputStream();
		InitConnectExec init =  new InitConnectExec();
		try {
			ProtoBufPackage pack = init.buildPackage();
			this.addHander(pack.getnSerialNo(), init);
			os.write(pack.pack());
			os.flush();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	/**
     * 发送心跳包
     */
    public void sendHeartbeat() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!over&&keepAlive) {
                        try {
                        	sleepMillis(10 * 1000);// 10s发送一次心跳
                            KeepAliveExec keepAlive = new KeepAliveExec();
                            ProtoBufPackage pack = keepAlive.buildPackage();
                            addHander(pack.getnSerialNo(), keepAlive);
                            os.write(pack.pack());
                            os.flush();
                        } catch (Exception e) {
                           
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	private class SocketThread extends Thread {
        @Override
        public void run() {
        	sendHeartbeat();
            long startTime = System.currentTimeMillis();
           
            while (!over) {
                try {
                    int size = is.available();
                    if (size <= 0) {
                        if ((System.currentTimeMillis() - startTime) > 30 * 1000) { // 超过30秒没有收到服务器信息，关闭
                            socket.close();
                            startTime = System.currentTimeMillis();
                        }
                        sleepMillis(1);
                        continue;
                    } else {
                        startTime = System.currentTimeMillis();
	                    ProtoBufPackage pack = ProtoBufPackage.unpack(is);
	                    if (pack.getnProtoID()==InitConnectExec.nProtoID)
	                    	connected = true;
	                    handler(pack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    over = true;
                }
            }
            try {
        		handlers = null;
                socket.close();
                is.close();
                os.close();
            } catch (IOException e1) {
            	e1.printStackTrace();
            }
        }
    }
	
	public void handler(ProtoBufPackage pack){
		new Thread(){
			public void run(){

				IExecutor handler = handlers.get(pack.getnSerialNo());
				
				try {
					if (handler!=null){
						handler.execute(pack);
					}else{
						for(IExecutor exec : handlers.values()){
							if (exec instanceof IUpdateExecutor ){
								IUpdateExecutor upexec = ((IUpdateExecutor)exec);
								if (((IUpdateExecutor) exec).getnUpdateProtoID()==pack.getnProtoID()){
									upexec.handler(upexec.parse(pack));
									if (pack.getnProtoID()==TrdGetOrderFillListExec.nUpdateProtoID||pack.getnProtoID()==TrdPlaceOrderExec.nUpdateProtoID)//与交易相关的推送是全局推送，不需要根据条件过滤。只需要推送一次就可以了。2208推送订单更新,2218推送新成交
										break;
								}
							}
						}
					}
				} catch (InvalidProtocolBufferException e) {
					over = true;
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void sleepMillis(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public long getConnID() {
		return connID;
	}
}
