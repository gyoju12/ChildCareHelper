package kr.mimic.childcarehelper.fragment;

import java.util.ArrayList;

import kr.mimic.childcarehelper.common.Constants;
import kr.mimic.childcarehelper.common.HttpAsyncTask;
import kr.mimic.childcarehelper.common.LogUtil;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class MapFragment 
	extends SherlockFragment 
	implements MapView.OpenAPIKeyAuthenticationResultListener,
			   MapView.MapViewEventListener,
			   MapView.CurrentLocationEventListener,
			   MapView.POIItemEventListener
{
	Intent intnet;
	
	LinearLayout linearLayout;

	ContentValues contentValues;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		linearLayout = new LinearLayout(getActivity().getApplicationContext());

		return linearLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		contentValues = getArguments().getParcelable("RESULT");

		new HttpAsyncTask(this).execute(
			Constants.getServiceKey(Constants.DAUM_LOCAL_API),
			Constants.DAUM_API_ADDR_2_COORD_URL,
			encodingSpace(contentValues.getAsString(Constants.FRAGMENT_MAP_ADDRESS_KEY)),
			"",
			"",
			Constants.LOCAL_CHILD_COORD
		);
	}

	public void initLayout(MapView mapView, ArrayList<ContentValues> contents){
		mapView.setDaumMapApiKey(Constants.getServiceKey(Constants.DAUM_MAP_API));
		mapView.setOpenAPIKeyAuthenticationResultListener(this);
		mapView.setMapViewEventListener(this);
		mapView.setCurrentLocationEventListener(this);
		mapView.setPOIItemEventListener(this);
		mapView.setMapType(MapView.MapType.Standard);

		linearLayout.addView(mapView);
		LogUtil.log(Log.ERROR, this, "initLayout()", "DAUM Local API Response DAO : " + contents);
		setPin(mapView, contents);
	}

	public void setPin(MapView mapView, ArrayList<ContentValues> contents){
		MapPOIItem poiItem1 = new MapPOIItem();
		double latitude     = Double.parseDouble(contents.get(1).getAsString("lat"));
		double longitude    = Double.parseDouble(contents.get(1).getAsString("lng"));

		poiItem1.setItemName(contentValues.getAsString(Constants.FRAGMENT_MAP_FACILITY_NAME_KEY));
		poiItem1.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
		poiItem1.setMarkerType(MapPOIItem.MarkerType.RedPin);
		poiItem1.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
		poiItem1.setShowCalloutBalloonOnTouch(true);
//		poiItem1.setDraggable(false);
		poiItem1.setTag(153);
		mapView.addPOIItem(poiItem1);

		mapView.fitMapViewAreaToShowAllPOIItems();
	}

	public String encodingSpace(String space){
		return space.replaceAll(" ", "%20");
	}

	@Override
	public void onDaumMapOpenAPIKeyAuthenticationResult(MapView arg0, int arg1, String arg2) {}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {}

	@Override
	public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1, MapPoint arg2) {}

	@Override
	public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {}

	@Override
	public void onCurrentLocationDeviceHeadingUpdate(MapView arg0, float arg1) {}

	@Override
	public void onCurrentLocationUpdate(MapView arg0, MapPoint arg1, float arg2) {}

	@Override
	public void onCurrentLocationUpdateCancelled(MapView arg0) {}

	@Override
	public void onCurrentLocationUpdateFailed(MapView arg0) {}

	@Override
	public void onMapViewCenterPointMoved(MapView arg0, MapPoint arg1) {}

	@Override
	public void onMapViewDoubleTapped(MapView arg0, MapPoint arg1) {}

	@Override
	public void onMapViewInitialized(MapView arg0) {}

	@Override
	public void onMapViewLongPressed(MapView arg0, MapPoint arg1) {}

	@Override
	public void onMapViewSingleTapped(MapView arg0, MapPoint arg1) {}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {}
}