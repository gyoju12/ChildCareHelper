package kr.mimic.childcarehelper.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import kr.mimic.childcarehelper.MainActivity;
import kr.mimic.childcarehelper.fragment.MainFragment;
import kr.mimic.childcarehelper.fragment.MapFragment;
import kr.mimic.childcarehelper.fragment.ResultFragment;

import net.daum.mf.map.api.MapView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;


public class HttpAsyncTask extends AsyncTask<String, Void, String> {
	HttpURLConnection httpConn = null;
	InputStream is             = null;
	OutputStream os            = null;
	ByteArrayOutputStream baos = null;
	
	HttpUtil httpUtil;
	HttpClient httpclient      = null;
	HttpResponse httpResponse  = null;

	MainActivity activity;
	MainFragment mainFragment;
	MapFragment mapFragment;
	ResultFragment resultFragment;

	String type;

	public HttpAsyncTask(MainActivity activity){
		this.activity = activity;
	}

	public HttpAsyncTask(MainFragment fragment){
		this.mainFragment = fragment;
	}

	public HttpAsyncTask(MapFragment fragment){
		this.mapFragment = fragment;
	}

	public HttpAsyncTask(ResultFragment fragment){
		this.resultFragment = fragment;
	}

	/**
	 * 
	 */
	@Override
	protected String doInBackground(String... params) {
		ContentValues content = initSearchCondotion(params);
		byte[] buffer;
		type = params[5];

		LogUtil.log(Log.ERROR, this, "doInBackground(String... params)", "URL : " + params[1]);

		try {
			httpUtil = new HttpUtil();
			httpUtil.setHttpURLConnection(params[1]);
			httpUtil.setRequestMethod(HttpUtil.HTTP_POST);
			if(Constants.LOCAL_CHILD_COORD.equals(type)){
				httpUtil.setRequestProperty(
					"Content-Type",
					"application/x-www-form-urlencoded"
				);
				buffer = XMLUtil.getInstance().generatedParams(params[0], content).getBytes("UTF-8");
			}else{
				httpUtil.setRequestProperty("Content-Type","text/xml; charset=utf-8;");
				httpUtil.setRequestProperty("Accept","application/xml;");
				buffer = XMLUtil.getInstance().generatedXML(params[0], content).getBytes("UTF-8");
			}
			httpUtil.setDoOutput(true);
			httpUtil.setDoIntput(true);
			

			return httpUtil.responseBodyData(buffer, 512);

		} catch (IOException ie) {
			ie.printStackTrace();
			return ie.getMessage();
		}
	}

	@Override
	protected void onPostExecute(String result) {
		ArrayList<ContentValues> contents = XMLUtil.getInstance().parsedXML(result);

		if(Constants.LOCAL_CHILD_LISTS.equals(type))		activity.showFragment(contents);
		else if(Constants.LOCAL_CHILD_COORD.equals(type))	mapFragment.initLayout(new MapView(mapFragment.getActivity()), contents);
		else if(Constants.LOCAL_CHILD_ITEMS.equals(type))	resultFragment.initData(contents);

		super.onPostExecute(result);
	}

	protected ContentValues initSearchCondotion(String... params){
		ContentValues content = new ContentValues();

		if(Constants.LOCAL_CHILD_LISTS.equals(params[5])){
			String[] sc = params[4].split(",");							// 검색 조건

			content.put("Region", convertString(params[2]));			// 서울시 자치구
			content.put("District", convertString(params[3]));			// 자치구 내 행정동
			content.put("CRTypePublic", convertString(sc[0]));			// 시설유형-국공립
			content.put("CRTypeCorporate", convertString(sc[1]));		// 시설유형-법인
			content.put("CRTypePrivate", convertString(sc[2]));			// 시설유형-민간
			content.put("CRTypeHome", convertString(sc[3]));			// 시설유형-가정
			content.put("CRTypeParent", convertString(sc[4]));			// 시설유형-부모협동
			content.put("CRTypeJob", convertString(sc[5]));				// 시설유형-직장
			content.put("CRSpecSeoul", convertString(sc[6]));			// 시설특성-서울형어린합집
			content.put("CRSpecDisableTotal", convertString(sc[7]));	// 시설특성-장애아통합
			content.put("CRSpecDisableFull", convertString(sc[8]));		// 시설특성-장애아전담
			content.put("CRSpecMultiCul", convertString(sc[9]));		// 시설특성-다문화시설
			content.put("CRSpecBaby", convertString(sc[10]));			// 시설특성-영아전담시설
			content.put("CRSpecTime", convertString(sc[11]));			// 시설특성-시간연장
			content.put("CRSpecTimeAS", convertString(sc[12]));			// 시설특성-방과후전담
			content.put("Certification", convertString(sc[13]));		// 평가인증유무
			content.put("Vehicle", convertString(sc[14]));				// 차량운행여부
			content.put("FacilityName", "");							// 시설명
			content.put("PageNum", sc[15]);				// 페이지번호
		}else if(Constants.LOCAL_CHILD_ITEMS.equals(params[5])){
			content.put("FacilityID", params[2]);
		}else if(Constants.LOCAL_CHILD_COORD.equals(params[5])){
			content.put("q", params[2]);
			content.put("output", "xml");
		}

		return content;
	}

	public String convertString(String str){
		String convertStr = "";

		if("전체".equals(str)){
			convertStr = "";
		}else if(" ".equals(str)){
			convertStr = "";
		}else{
			convertStr = str;
		}

		return convertStr;
	}

}