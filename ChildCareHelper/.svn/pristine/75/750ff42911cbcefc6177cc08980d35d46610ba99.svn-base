package kr.mimic.childcarehelper.fragment;

import kr.mimic.childcarehelper.MainActivity;
import kr.mimic.childcarehelper.R;
import kr.mimic.childcarehelper.common.Constants;
import kr.mimic.childcarehelper.common.HttpAsyncTask;
import kr.mimic.childcarehelper.common.LogUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;


public class MainFragment extends SherlockFragment {
	Context context;
	Activity activity;
	MainFragment fragment;
	Intent intent;

	public Spinner spArea;
	public Spinner spDistrict;

	public CheckBox cbTypePublic;
	public CheckBox cbTypeCorporate;
	public CheckBox cbTypePrivate;
	public CheckBox cbTypeHome;
	public CheckBox cbTypeParent;
	public CheckBox cbTypeJob;
	public CheckBox cbSpecSeoul;
	public CheckBox cbSpecTimeAs;
	public CheckBox cbSpecDisableTotal;
	public CheckBox cbSpecDisableFull;
	public CheckBox cbSpecMultiCul;
	public CheckBox cbSpecBaby;
	public CheckBox cbSpecTime;
	public CheckBox cbEtcCertification;
	public CheckBox cbEtcVehicle;

	public Button btnSend;

	HttpAsyncTask httpAsyncTask;
	ArrayAdapter<String> adapter;
	String[] areaName;
	String[] areaCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = getActivity();
		context  = activity.getApplicationContext();
		intent   = activity.getIntent();
		fragment = this;

		initLayout();
	}

	/**
	 * Layout을 초기화 한다.
	 */
	public void initLayout(){
		spArea     = (Spinner)activity.findViewById(R.id.sp_area);
		spDistrict = (Spinner)activity.findViewById(R.id.sp_district);

		cbTypePublic       = (CheckBox)activity.findViewById(R.id.cb_type_public);
		cbTypeCorporate    = (CheckBox)activity.findViewById(R.id.cb_type_corporate);
		cbTypePrivate      = (CheckBox)activity.findViewById(R.id.cb_type_private);
		cbTypeHome         = (CheckBox)activity.findViewById(R.id.cb_type_home);
		cbTypeParent       = (CheckBox)activity.findViewById(R.id.cb_type_parent);
		cbTypeJob          = (CheckBox)activity.findViewById(R.id.cb_type_job);
		cbSpecSeoul        = (CheckBox)activity.findViewById(R.id.cb_spec_seoul);
		cbSpecDisableTotal = (CheckBox)activity.findViewById(R.id.cb_spec_disable_total);
		cbSpecDisableFull  = (CheckBox)activity.findViewById(R.id.cb_spec_disable_full);
		cbSpecMultiCul     = (CheckBox)activity.findViewById(R.id.cb_spec_multi_cul);
		cbSpecBaby         = (CheckBox)activity.findViewById(R.id.cb_spec_baby);
		cbSpecTime         = (CheckBox)activity.findViewById(R.id.cb_spec_time);
		cbSpecTimeAs       = (CheckBox)activity.findViewById(R.id.cb_spec_time_as);
		cbEtcCertification = (CheckBox)activity.findViewById(R.id.cb_etc_certification);
		cbEtcVehicle       = (CheckBox)activity.findViewById(R.id.cb_etc_vehicle);

		btnSend    = (Button)activity.findViewById(R.id.btn_send);

		areaName = getResources().getStringArray(R.array.area_name);
		areaCode = getResources().getStringArray(R.array.area_code);

		adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, areaName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spArea.setAdapter(adapter);
		spArea.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ArrayAdapter<String> dAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Constants.districtNames[arg2]);
				dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spDistrict.setAdapter(dAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int selectedAreaItemPosition     = spArea.getSelectedItemPosition();
				int selectedDistrictItemPosition = spDistrict.getSelectedItemPosition();
				new HttpAsyncTask((MainActivity)activity)
					.execute(
						Constants.getServiceKey(Constants.DATA_ORG), 
						Constants.SEARCH_LOCAL_CHILD_INFO_URL, 
						areaCode[selectedAreaItemPosition], 
						Constants.districtNames[selectedAreaItemPosition][selectedDistrictItemPosition],
						searchCondition(),
						Constants.LOCAL_CHILD_LISTS
						
					);
			}
		});
	}

	/**
	 * 검색 조건을 String형 Data로 변경한다.
	 * 
	 * @return
	 */
	public String searchCondition(){
		StringBuffer searchCondition = new StringBuffer();
		CheckBox[] arrCheckBox = {
									cbTypePublic, cbTypeCorporate, 
									cbTypePrivate, cbTypeHome, 
									cbTypeParent, cbTypeJob,
									cbSpecSeoul, cbSpecDisableTotal,
									cbSpecDisableFull, cbSpecMultiCul,
									cbSpecBaby, cbSpecTime,
									cbSpecTimeAs, cbEtcCertification,
									cbEtcVehicle
								};

		for(int i=0; i<arrCheckBox.length; i++){
			if(arrCheckBox[i].isChecked()){
				searchCondition.append("Y,");
			}else{
				searchCondition.append(" ,");
			}
		}
		searchCondition.append("1");

		LogUtil.log(Log.ERROR, this, "searchCondition()", "String : " + searchCondition.toString());

		return searchCondition.toString();
	}
}