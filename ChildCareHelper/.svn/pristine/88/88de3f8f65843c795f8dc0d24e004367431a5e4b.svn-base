package kr.mimic.childcarehelper.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.HttpClient;

import android.content.ContentValues;
import android.util.Log;

public class HttpUtil {
	public static final int HTTP_POST = 0;
	public static final int HTTP_GET  = 1;
	
	private static HttpUtil _instance = null;

	private HttpURLConnection httpConn = null;
	private HttpClient httpClient      = null;

	public HttpUtil(){
		// 아직 내용 없음.
	}

	public HttpUtil(String paramUrl){
		try {
			URL url = new URL(paramUrl);
			this.httpConn = (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException mue) {
			LogUtil.log(Log.ERROR, this, "Constructor", mue.getStackTrace().toString());
		} catch (IOException ie) {
			LogUtil.log(Log.ERROR, this, "Constructor", ie.getStackTrace().toString());
		}
	}

	public void setHttpURLConnection(String paramUrl){
		try {
			URL url = new URL(paramUrl);
			this.httpConn = (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException mue) {
			LogUtil.log(Log.ERROR, this, "setHttpURLConnection()", mue.getStackTrace().toString());
		} catch (IOException ie) {
			LogUtil.log(Log.ERROR, this, "setHttpURLConnection()", ie.getStackTrace().toString());
		}
	}

	public void setRequestProperty(String field, String newValue){
		this.httpConn.setRequestProperty(field, newValue);
	}

	public void setRequestMethod(int method){
		try {
			switch(method){
			case HTTP_POST:
				this.httpConn.setRequestMethod("POST");
				break;
			case HTTP_GET:
				this.httpConn.setRequestMethod("GET");
				break;
			default:
				this.httpConn.setRequestMethod("POST");
				break;
			}
		} catch (ProtocolException pe) {
			LogUtil.log(Log.ERROR, this, "setRequestMethod(" + method + ")", pe.getStackTrace().toString());
		}
	}

	public void setDoOutput(boolean newValue){
		httpConn.setDoOutput(newValue);
	}

	public void setDoIntput(boolean newValue){
		httpConn.setDoInput(newValue);
	}

	public HttpURLConnection getHttpURLConnection(){
		return this.httpConn;
	}

	public int getResponseCode() throws IOException{
		return this.httpConn.getResponseCode();
	}

	public OutputStream getOutputStream(){
		OutputStream os = null;
		
		try {
			os = this.httpConn.getOutputStream();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return os;
	}

	public InputStream getInputStream(){
		InputStream inputStream = null;

		try {
			inputStream = httpConn.getInputStream();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return inputStream;
	}

	public void setHeader(ContentValues params){
		Set<Entry<String, Object>> keys          = params.valueSet();
		Iterator<Entry<String, Object>> iterator = keys.iterator();

		while(iterator.hasNext()){
			String key = iterator.next().getKey();
			httpConn.addRequestProperty(key, params.getAsString(key));
		}
	}

	public String responseBodyData(byte[] buffer, int length){
		OutputStream os            = getOutputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		LogUtil.log(Log.ERROR, this, "responseBodyData()", "OutputStream : " + os);
		LogUtil.log(Log.ERROR, this, "responseBodyData()", "buffer : " + buffer.toString());

		try{
			os.write(buffer);
			os.close();
	
			if(getResponseCode() == HttpURLConnection.HTTP_OK){
				byte[] byteBuffer = new byte[length];
				int nLength       = 0;
	
				while((nLength = getInputStream().read(byteBuffer, 0, byteBuffer.length)) != -1){
					baos.write(byteBuffer, 0, nLength);
				}
	
				return new String(baos.toByteArray());
			}else{
				LogUtil.log(Log.ERROR, this, "responseBodyData()", "Not Responsed!!(ERROR CODE : " + getResponseCode() + ")");
				return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header><ns2:ComMsgHeaderResponse xmlns:ns2=\"http://apiiseoul.seoul.go.kr/SearchLocalChildcareInformationService/\"><RequestMsgID></RequestMsgID><ResponseTime></ResponseTime><ResponseMsgID></ResponseMsgID><SuccessYN>N</SuccessYN><ReturnCode>04</ReturnCode><ErrMsg>HTTP_ERROR.</ErrMsg></ns2:ComMsgHeaderResponse></soap:Header><soap:Body></soap:Body></soap:Envelope>";
			}
		}catch(NullPointerException ne){
			return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header><ns2:ComMsgHeaderResponse xmlns:ns2=\"http://apiiseoul.seoul.go.kr/SearchLocalChildcareInformationService/\"><RequestMsgID></RequestMsgID><ResponseTime></ResponseTime><ResponseMsgID></ResponseMsgID><SuccessYN>N</SuccessYN><ReturnCode>99</ReturnCode><ErrMsg>" + ne.getLocalizedMessage() + ".</ErrMsg></ns2:ComMsgHeaderResponse></soap:Header><soap:Body></soap:Body></soap:Envelope>";
		}catch (IOException ie) {
			return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header><ns2:ComMsgHeaderResponse xmlns:ns2=\"http://apiiseoul.seoul.go.kr/SearchLocalChildcareInformationService/\"><RequestMsgID></RequestMsgID><ResponseTime></ResponseTime><ResponseMsgID></ResponseMsgID><SuccessYN>N</SuccessYN><ReturnCode>99</ReturnCode><ErrMsg>" + ie.getLocalizedMessage() + ".</ErrMsg></ns2:ComMsgHeaderResponse></soap:Header><soap:Body></soap:Body></soap:Envelope>";
		}
	}
}