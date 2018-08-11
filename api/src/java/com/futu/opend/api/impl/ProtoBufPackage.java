package com.futu.opend.api.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.joou.Unsigned;

class ProtoBufPackage {

	private String szHeaderFlag = "FT";//包头起始标志，固定为“FT”
	private int nProtoID;//协议ID
	private byte nProtoFmtType = 0;//协议格式类型，0为Protobuf格式，1为Json格式
	private byte nProtoVer = 0;//协议版本，用于迭代兼容, 目前填0
	private int nSerialNo;//包序列号，用于对应请求包和回包, 要求递增
	private int nBodyLen;//包体长度
	private byte[] arrBodySHA1;//byte[20]
	private byte[] arrReserved = new byte[8];//byte[8]
	private byte[] bodys;//包体
	
	public ProtoBufPackage(){
		this.nSerialNo = (int)System.currentTimeMillis();
	}
	
	public byte[] pack() throws IOException, NoSuchAlgorithmException{
		ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bytearray);
		
		outputStream.write(szHeaderFlag.getBytes());//包头起始标志，固定为“FT”
		outputStream.write(toLH(Unsigned.uint(nProtoID).intValue()));//协议ID
		outputStream.writeByte(Unsigned.ubyte(nProtoFmtType).byteValue());//协议格式类型，0为Protobuf格式，1为Json格式
		outputStream.writeByte(Unsigned.ubyte(nProtoVer).byteValue());//协议版本，用于迭代兼容, 目前填0
		outputStream.write(toLH(Unsigned.uint(nSerialNo).intValue()));//包序列号，用于对应请求包和回包, 要求递增
		outputStream.write(toLH(Unsigned.uint(nBodyLen).intValue()));//包体长度
	    
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(bodys);
        arrBodySHA1 = messageDigest.digest();
        outputStream.write(arrBodySHA1);
        outputStream.write(arrReserved);//保留8字节扩展
        outputStream.write(bodys);

		return bytearray.toByteArray();
	}
	
	public static ProtoBufPackage unpack(InputStream inputStream) throws IOException{
		ProtoBufPackage pack = new ProtoBufPackage();
		
		byte[] bytes = new byte[2];
		inputStream.read(bytes, 0, 2);
		
		bytes = new byte[4];
		inputStream.read(bytes, 0, 4);
		pack.setnProtoID((int)unintbyte2long(bytes));
		
		bytes = new byte[2];
		inputStream.read(bytes, 0, 2);
		
		bytes = new byte[4];
		inputStream.read(bytes, 0, 4);
		pack.setnSerialNo((int)unintbyte2long(bytes));
		
		bytes = new byte[4];
		inputStream.read(bytes, 0, 4);
		pack.setnBodyLen((int)unintbyte2long(bytes));
		
		bytes = new byte[20];
		inputStream.read(bytes, 0, 20);
		pack.setArrBodySHA1(bytes);
		
		bytes = new byte[8];
		inputStream.read(bytes, 0, 8);
		
		ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bytearray);
		bytes = new byte[1];
		for(int i=0;i<pack.getnBodyLen();i++){
			inputStream.read(bytes, 0, 1);
			outputStream.writeByte(bytes[0]);
		}
		pack.setBodys(bytearray.toByteArray());
	    return pack;
	}
	
	public String getSzHeaderFlag() {
		return szHeaderFlag;
	}

	public void setSzHeaderFlag(String szHeaderFlag) {
		this.szHeaderFlag = szHeaderFlag;
	}

	public int getnProtoID() {
		return nProtoID;
	}

	public void setnProtoID(int nProtoID) {
		this.nProtoID = nProtoID;
	}

	public byte getnProtoFmtType() {
		return nProtoFmtType;
	}

	public void setnProtoFmtType(byte nProtoFmtType) {
		this.nProtoFmtType = nProtoFmtType;
	}

	public byte getnProtoVer() {
		return nProtoVer;
	}

	public void setnProtoVer(byte nProtoVer) {
		this.nProtoVer = nProtoVer;
	}

	public int getnSerialNo() {
		return nSerialNo;
	}

	public void setnSerialNo(int nSerialNo) {
		this.nSerialNo = nSerialNo;
	}

	public int getnBodyLen() {
		return nBodyLen;
	}

	public void setnBodyLen(int nBodyLen) {
		this.nBodyLen = nBodyLen;
	}

	public byte[] getArrBodySHA1() {
		return arrBodySHA1;
	}

	public void setArrBodySHA1(byte[] arrBodySHA1) {
		this.arrBodySHA1 = arrBodySHA1;
	}

	public byte[] getArrReserved() {
		return arrReserved;
	}

	public void setArrReserved(byte[] arrReserved) {
		this.arrReserved = arrReserved;
	}

	public byte[] getBodys() {
		return bodys;
	}
	public void setBodys(byte[] bodys) {
		this.bodys = bodys;
		this.nBodyLen = this.bodys.length;
	}
	
	public static byte[] toLH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}
	

	public static long unintbyte2long(byte[] res) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		int index = 0;
		firstByte = (0x000000FF & ((int) res[index]));
		secondByte = (0x000000FF & ((int) res[index + 1]));
		thirdByte = (0x000000FF & ((int) res[index + 2]));
		fourthByte = (0x000000FF & ((int) res[index + 3]));
		return ((long) (firstByte | secondByte << 8 | thirdByte << 16 | fourthByte << 24)) & 0xFFFFFFFFL;
	}
	
	public static int byte2char(byte[] res) {
		DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(res));
		int a = 0;
		try {
			a = dataInputStream.readUnsignedByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 
		return a;
	}
	
	public static int shortbyte2int(byte[] res) {
		DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(res));
		int a = 0;
		try {
			a = dataInputStream.readUnsignedShort();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
}
