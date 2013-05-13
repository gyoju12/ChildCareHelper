package kr.mimic.childcarehelper.common;

import android.util.Log;

public class LogUtil {
	public static void log(int method, Object obj, String msg1, String msg2){
		switch(method){
		case Log.ERROR:
			Log.e(obj.getClass().getSimpleName(), "[" + msg1 + "] =======> " + msg2);
			break;
		case Log.WARN:
			Log.w(obj.getClass().getSimpleName(), "[" + msg1 + "] =======> " + msg2);
			break;
		case Log.DEBUG:
			Log.d(obj.getClass().getSimpleName(), "[" + msg1 + "] =======> " + msg2);
			break;
		case Log.INFO:
			Log.i(obj.getClass().getSimpleName(), "[" + msg1 + "] =======> " + msg2);
			break;
		case Log.VERBOSE:
			Log.v(obj.getClass().getSimpleName(), "[" + msg1 + "] =======> " + msg2);
			break;
		default:
			System.out.println("[" + obj.getClass().getSimpleName() + "] =======> " + msg2);
			break;
		}
	}
}