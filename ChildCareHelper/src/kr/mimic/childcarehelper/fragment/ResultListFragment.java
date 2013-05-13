package kr.mimic.childcarehelper.fragment;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import kr.mimic.childcarehelper.MainActivity;
import kr.mimic.childcarehelper.R;
import kr.mimic.childcarehelper.common.Constants;
import kr.mimic.childcarehelper.common.LogUtil;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ResultListFragment extends SherlockListFragment {
	Context context;
	Activity activity;

	LayoutInflater inflater;
	ListAdapter adapter;
	public TextView tvResult;
	public Button btnMore;
	public ListView listView;

	ContentValues headerData;
	ContentValues commonBodydata;
	ArrayList<ContentValues> listAcualBodyData = new ArrayList<ContentValues>();

	int totalCnt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = getActivity();
		context  = activity.getApplicationContext();

		init();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		setHasOptionsMenu(false);
	}

	public void init(){
		ArrayList<ContentValues> initData = getArguments().getParcelableArrayList("RESULT");

		for(int i=0; i<initData.size(); i++){
			switch(i){
			case 0:
				headerData = initData.get(i);
				break;
			case 1:
				commonBodydata = initData.get(i);
				break;
			default:
				listAcualBodyData.add(initData.get(i));
				break;
			}
		}

		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter  = new ListAdapter(context, R.layout.facility_item, listAcualBodyData);

		initLayout();
	}

	public void initLayout(){
		tvResult = (TextView)activity.findViewById(R.id.tv_result);
		btnMore  = (Button)inflater.inflate(R.layout.listview_more_button, null);
		listView = getListView();
		totalCnt = ("Y".equals(headerData.get("SuccessYN").toString()))?Integer.parseInt(commonBodydata.get("TotalFacilifyCount").toString()):0;

		tvResult.setText(
			activity.getApplicationContext().getString(
				R.string.tv_search_count, 
				totalCnt
			)
		);

//		btnMore.setVisibility((listAcualBodyData.size()<totalCnt)?View.VISIBLE:View.GONE);
		btnMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.addItem(listAcualBodyData);
				adapter.notifyDataSetChanged();
				listView.removeFooterView(btnMore);
				if(listAcualBodyData.size()<totalCnt) listView.addFooterView(btnMore);
				
				LogUtil.log(Log.ERROR, this, "setOnClickListener()", "list Size : " + listView.getCount());
			}
		});

		if(listAcualBodyData.size()<totalCnt) listView.addFooterView(btnMore);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long itemId) {
				((MainActivity)activity).showFragment(
					Constants.RESLUT_FRAGMENT,
					(ContentValues)view.getTag(R.layout.facility_item)
				);
			}
		});

		if("N".equals(headerData.get("SuccessYN").toString())) Toast.makeText(context, headerData.get("ErrMsg").toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_main, menu);
		menu.findItem(R.id.menu_map).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch(item.getItemId()){
		case R.id.menu_settings:
			((MainActivity)activity).showFragment(
				Constants.RESLUT_FRAGMENT,
				new ContentValues()
			);
			break;
		}

		return false;
	}

	class ListAdapter extends BaseAdapter{
		ArrayList<ContentValues> list;

		Context context;

		TextView tvType;
		TextView tvFacilityName;
		TextView tvNumber;
		TextView tvTelephone;

		int resource;

		public ListAdapter(Context context, int resource, ArrayList<ContentValues> list){
			this.context  = context;
			this.resource = resource;
			this.list     = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public ContentValues getItem(int index) {
			return list.get(index);
		}

		@Override
		public long getItemId(int index) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContentValues content = list.get(position);
			ContentViewHolder contentViewHolder;
			
			if(convertView == null){
				convertView = inflater.inflate(resource, null);

				contentViewHolder                = new ContentViewHolder();
				contentViewHolder.tvType         = (TextView)convertView.findViewById(R.id.tv_type);
				contentViewHolder.tvFacilityName = (TextView)convertView.findViewById(R.id.tv_facility_name);
				contentViewHolder.tvNumber       = (TextView)convertView.findViewById(R.id.tv_number);
				contentViewHolder.tvTelephone    = (TextView)convertView.findViewById(R.id.tv_telephone);

				convertView.setTag(contentViewHolder);
			}else{
				contentViewHolder = (ContentViewHolder)convertView.getTag();
			}

			contentViewHolder.tvType.setText(content.getAsString("Type"));
			contentViewHolder.tvFacilityName.setText(content.getAsString("FacilityName"));
			contentViewHolder.tvNumber.setText("(" + content.getAsString("PresentNumber") + "/" + content.getAsString("FixedNumber") + ")");
			contentViewHolder.tvTelephone.setText("Tel : " + content.getAsString("Telephone"));

			if(position%2 != 0) convertView.setBackgroundResource(R.color.list_item_bg_color);
			convertView.setTag(R.layout.facility_item, content);

			return convertView;
		}

		public void addItem(ArrayList<ContentValues> collection){
			this.list.addAll(collection);
		}

		public class ContentViewHolder{
			public LinearLayout linearLayout;
			public TextView tvType;
			public TextView tvFacilityName;
			public TextView tvNumber;
			public TextView tvTelephone;
		}
	}
}