package util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class idGenerator {

	public static String genGUID() {
		// TODO Auto-generated method stub
		//生成随机id
		return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}
	public static String genOrderId(){
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(d);
		return date+System.nanoTime();//date+nanotime
	}

}
