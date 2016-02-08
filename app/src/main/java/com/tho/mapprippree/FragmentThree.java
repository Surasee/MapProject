package com.tho.mapprippree;

import android.app.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentThree extends Fragment {

	ImageView ivIcon;
	TextView tvItemName;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";


	private FragmentActivity myContext;
	private boolean status = true;
	private FunctionLib link;
	private Bitmap[] intIcon;
	private String[] strTitle;
	private String[] strDetail;
	private LatLng[] latLngs;

	public FragmentThree() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_three, container,false);

		ivIcon = (ImageView) view.findViewById(R.id.frag3_icon);
		tvItemName = (TextView) view.findViewById(R.id.frag3_text);

		tvItemName.setText(getArguments().getString(ITEM_NAME));
		ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		myContext=(FragmentActivity) activity;
		super.onAttach(activity);
	}//end onAttach



	@Override
	public void onResume() {

		Log.e("DEBUG", "onResume of FragmentThree" + status);
		if(status)
			//setUp();

		super.onResume();

	}

	@Override
	public void onPause() {
		Log.e("DEBUG", "OnPause of FragmentThree"+status);
		status = false;



		super.onPause();

	}

	private void setUp() {

		link = new FunctionLib();
		try {
			//String url = "http://www.travelapp.pe.hu/";
			String url = "http://tho.ddns.net:2407/prippree/";
			String paramiter = "index.php?tag=show_place_main";
			Log.e("link host main ", "" + url + paramiter);
			String resultServerg = link.getJSONUrl(paramiter);
//			Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			Log.d("GET_PLACE_WEB_HOST", resultServerg);

			try{
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");

				if(!error)
				{
					JSONArray place_data = c.getJSONArray("data");
					int max_data = place_data.length();
					intIcon = new Bitmap[max_data];
					strTitle = new String[max_data];
					strDetail = new String[max_data];
					latLngs = new LatLng[max_data];

					for (int i = 0; i < max_data; i++) {
						JSONObject jsonObj = place_data.getJSONObject(i);
						intIcon[i] = link.getBitmapFromURL(jsonObj.getString("url_picture"));
						strTitle[i] = jsonObj.getString("name_place");
						strDetail[i] = jsonObj.getString("name_type");
						latLngs[i] = new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place"));
					}
					MyAdapter objMyAdapter = new MyAdapter(myContext, intIcon, strTitle, strDetail);
					ListView myListView = (ListView) myContext.findViewById(R.id.listView);
					myListView.setAdapter(objMyAdapter);

					myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

							Toast.makeText(myContext.getApplicationContext(), "long = " + l + "  i = " + i + "name_place = " + strTitle[i] + "latLong = " + latLngs[i].latitude + "," + latLngs[i].longitude, Toast.LENGTH_LONG).show();




						}
					});




				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Loading.....",
							Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),
					Toast.LENGTH_LONG).show();
		}

	}// end setup



}
