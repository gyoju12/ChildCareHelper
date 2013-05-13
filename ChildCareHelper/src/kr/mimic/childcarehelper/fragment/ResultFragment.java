package kr.mimic.childcarehelper.fragment;

import java.util.ArrayList;

import kr.mimic.childcarehelper.MainActivity;
import kr.mimic.childcarehelper.R;
import kr.mimic.childcarehelper.common.Constants;
import kr.mimic.childcarehelper.common.LogUtil;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ResultFragment extends SherlockFragment {
	Context context;
	Activity activity;
	ResultFragment fragment;

	TextView tvFacilityName;
	TextView tvNumbers;
	TextView tvPresidentName;
	TextView tvOpenDate;
	TextView tvCRType;
	TextView tvCRSpec;
	TextView tvTelephone;
	TextView tvFax;
	TextView tvAddress;
	TextView tvVehicle;
	TextView tvCertification;
	TextView tvGovSupport;
	TextView tvAccidentIns;
	TextView tvFireIns;
	TextView tvCompensationIns;

	ContentValues content;
	ContentValues map;
	String facilityID;

	String valFacilityName;
	String valNumbers;
	String valPresidentName;
	String valOpenDate;
	String valCRType;
	String valCRSpec;
	String valTelephone;
	String valFax;
	String valAddress;
	String valVehicle;
	String valCertification;
	String valGovSupport;
	String valAccidentIns;
	String valFireIns;
	String valCompensationIns;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_result, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = getActivity();
		context  = activity.getApplicationContext();
		fragment = this;

		init();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		setHasOptionsMenu(false);
	}

	protected void init(){
		content    = getArguments().getParcelable("RESULT");
		map        = new ContentValues();

		facilityID = content.getAsString("FacilityID");

		valFax             = content.getAsString("Fax");

		initData(new ArrayList<ContentValues>());
		initLayout();
	}

	public void initData(ArrayList<ContentValues> contents){
//		valFacilityName    = contents.get(1).getAsString("FacilityName");
		valFacilityName    = "구의동어린이집";
		valNumbers         = contents.get(1).getAsString("PresentNumber")
						   + "/"
						   + contents.get(1).getAsString("FixedNumber");
		valPresidentName   = contents.get(1).getAsString("PresidentName");
		valOpenDate        = contents.get(1).getAsString("OpenDate");
		valCRType          = contents.get(1).getAsString("CRType");
		valCRSpec          = contents.get(1).getAsString("CRSpec");
		valTelephone       = contents.get(1).getAsString("Telephone");
//		valAddress         = contents.get(1).getAsString("Address");
		valAddress         = "서울특별시 광진구 구의2동 36-20";
		valVehicle         = contents.get(1).getAsString("Vehicle");
		valCertification   = contents.get(1).getAsString("Certification");
		valGovSupport      = contents.get(1).getAsString("GovSupport");
		valAccidentIns     = contents.get(1).getAsString("AccidentInsurance");
		valFireIns         = contents.get(1).getAsString("FireInsurance");
		valCompensationIns = contents.get(1).getAsString("CompensationInsurance");

		map.put(Constants.FRAGMENT_MAP_FACILITY_NAME_KEY, valFacilityName);
		map.put(Constants.FRAGMENT_MAP_ADDRESS_KEY, valAddress);
	}

	protected void initLayout(){
		tvFacilityName    = (TextView)activity.findViewById(R.id.tv_result_facility_name);
		tvNumbers         = (TextView)activity.findViewById(R.id.tv_result_numbers);
		tvPresidentName   = (TextView)activity.findViewById(R.id.tv_result_president_name);
		tvOpenDate        = (TextView)activity.findViewById(R.id.tv_result_open_date);
		tvCRType          = (TextView)activity.findViewById(R.id.tv_result_type);
		tvCRSpec          = (TextView)activity.findViewById(R.id.tv_result_spec);
		tvTelephone       = (TextView)activity.findViewById(R.id.tv_result_tel);
		tvFax             = (TextView)activity.findViewById(R.id.tv_result_fax);
		tvAddress         = (TextView)activity.findViewById(R.id.tv_result_addr);
		tvVehicle         = (TextView)activity.findViewById(R.id.tv_result_vehicle);
		tvCertification   = (TextView)activity.findViewById(R.id.tv_result_cert);
		tvGovSupport      = (TextView)activity.findViewById(R.id.tv_result_gov_support);
		tvAccidentIns     = (TextView)activity.findViewById(R.id.tv_result_accident);
		tvFireIns         = (TextView)activity.findViewById(R.id.tv_result_fire);
		tvCompensationIns = (TextView)activity.findViewById(R.id.tv_result_comp);

		tvFacilityName.setText(valFacilityName);
		tvNumbers.setText(
			activity.getApplicationContext().getString(
				R.string.tv_result_numbers, 
				"10/20"
			)
		);
		tvPresidentName.setText("홍길동");
		tvOpenDate.setText("YYYY-MM-DD");
		tvCRType.setText("가정");
		tvCRSpec.setText("시설특징");
		tvTelephone.setText("###-####-####");
		tvFax.setText(valFax);
		tvAddress.setText(valAddress);
		tvVehicle.setText(("Y".equals(""))?"운행":"미운행");
		tvCertification.setText(("Y".equals(""))?"인증":"미인증");
		tvGovSupport.setText(("Y".equals(""))?"지원":"미지원");
		tvAccidentIns.setText(("Y".equals(""))?"가입":"미가입");
		tvFireIns.setText(("Y".equals(""))?"가입":"미가입");
		tvCompensationIns.setText(("Y".equals(""))?"가입":"미가입");
	}

	public void setDetails(ContentValues contentValues){
		tvFacilityName.setText(valFacilityName);
		tvNumbers.setText(
			activity.getApplicationContext().getString(
				R.string.tv_result_numbers, 
				"10/20"
			)
		);
		tvPresidentName.setText("홍길동");
		tvOpenDate.setText("YYYY-MM-DD");
		tvCRType.setText("가정");
		tvCRSpec.setText("시설특징");
		tvTelephone.setText("###-####-####");
		tvFax.setText(valFax);
		tvAddress.setText(valAddress);
		tvVehicle.setText(("Y".equals(""))?"운행":"미운행");
		tvCertification.setText(("Y".equals(""))?"인증":"미인증");
		tvGovSupport.setText(("Y".equals(""))?"지원":"미지원");
		tvAccidentIns.setText(("Y".equals(""))?"가입":"미가입");
		tvFireIns.setText(("Y".equals(""))?"가입":"미가입");
		tvCompensationIns.setText(("Y".equals(""))?"가입":"미가입");
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_main, menu);
		menu.findItem(R.id.menu_settings).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch(item.getItemId()){
		case R.id.menu_map:
			((MainActivity)activity).showFragment(Constants.MAP_FRAGMENT, map);
			LogUtil.log(Log.ERROR, this, "onOptionsItemSelected()", "R.menu.activity_main");
			break;
		}

		return false;
	}
}