package kr.mimic.childcarehelper;

import java.util.ArrayList;

import kr.mimic.childcarehelper.common.Constants;
import kr.mimic.childcarehelper.fragment.MainFragment;
import kr.mimic.childcarehelper.fragment.MapFragment;
import kr.mimic.childcarehelper.fragment.ResultFragment;
import kr.mimic.childcarehelper.fragment.ResultListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nbpcorp.mobilead.sdk.MobileAdListener;
import com.nbpcorp.mobilead.sdk.MobileAdView;

public class MainActivity extends SherlockFragmentActivity implements MobileAdListener{
	Context context;
	Intent intent;

	FragmentTransaction ft;
	Fragment fragment;

	ArrayAdapter<String> adapter;
	String[] areaName;
	String[] areaCode;

	private MobileAdView adView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLayout();
	}
	
	public void initLayout(){
		adView = (MobileAdView) findViewById(R.id.adview1);
		adView.setListener(this);

		ft = getSupportFragmentManager().beginTransaction();
		fragment = new MainFragment();

		ft.replace(R.id.fragment_arae, fragment)
		  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		  .addToBackStack(null)
		  .commit();
	}

	public void showFragment(ArrayList<ContentValues> result){
		ft = getSupportFragmentManager().beginTransaction();
		fragment = new ResultListFragment();
		Bundle bundle = new Bundle();

		bundle.putParcelableArrayList("RESULT", result);
		fragment.setArguments(bundle);

		ft.replace(R.id.fragment_arae, fragment)
		  .addToBackStack(null)
		  .commit();
	}

	public void showFragment(int flag, ContentValues content){
		Bundle bundle = new Bundle();
		ft = getSupportFragmentManager().beginTransaction();

		switch(flag){
		case Constants.MAIN_FRAGMENT:
			fragment = new MainFragment();
			break;
		case Constants.LIST_FRAGMENT:
			fragment = new ResultListFragment();
			break;
		case Constants.RESLUT_FRAGMENT:
			fragment = new ResultFragment();
			break;
		case Constants.MAP_FRAGMENT:
			fragment = new MapFragment();
			break;
		default:
			fragment = new MainFragment();
			break;
		}

		bundle.putParcelable(
			"RESULT", 
			(content == null)?new ContentValues():content
		);
		fragment.setArguments(bundle);

		ft.replace(R.id.fragment_arae, fragment)
		  .addToBackStack(null)
		  .commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);

		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}


	@Override
	public void onReceive(int err) {
		// event for receive ad
	}
}