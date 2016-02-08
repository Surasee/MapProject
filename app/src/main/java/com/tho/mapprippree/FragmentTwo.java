package com.tho.mapprippree;

import android.app.Activity;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentTwo   extends Fragment {

	ImageView ivIcon;
	TextView tvItemName;
	private int count = 0;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	private FragmentActivity myContext;
	private FunctionLib link;
	private Bitmap[] intIcon;
	private String newline = System.getProperty("line.separator");
	private String[] strTitle,strType,strDetail,strPicture;
	private LatLng[] latLngs;
	private Fragment fragment;
	private ListView myListView;

		private boolean status = true;

	public FragmentTwo()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("DEBUG", "onCreateView of FragmentTwo "+status);
		View view=inflater.inflate(R.layout.fragment_layout_two,container, false);

		ivIcon=(ImageView)view.findViewById(R.id.frag2_icon);
		tvItemName=(TextView)view.findViewById(R.id.frag2_text);

		tvItemName.setText(getArguments().getString(ITEM_NAME));
		ivIcon.setImageDrawable(view.getResources().getDrawable(
				getArguments().getInt(IMAGE_RESOURCE_ID)));


		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		Log.e("DEBUG", "onAttach of FragmentTwo "+status);
		myContext=(FragmentActivity) activity;
		link = new FunctionLib();
		super.onAttach(activity);
	}//end onAttach

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e("DEBUG", "onCreate of FragmentTwo " + status);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		Log.e("DEBUG", "onResume of FragmentTwo " + status);
		if(status){
			myContext.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setUp();
				}
			});
		}


		super.onResume();
	}

	@Override
	public void onPause() {
		Log.e("DEBUG", "OnPause of FragmentTwo "+status);
		status = false;
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.e("DEBUG", "OnDes of FragmentTwo "+status);
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		link.btnBackClick(FragmentTwo.this);
		super.onDetach();
	}

	private void setUp() {

		try {
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
					strPicture = new String[max_data];
					strTitle = new String[max_data];
					strType = new String[max_data];
					strDetail = new String[max_data];
					latLngs = new LatLng[max_data];

					for (int i = 0; i < max_data; i++) {
						JSONObject jsonObj = place_data.getJSONObject(i);
						intIcon[i] = link.getBitmapFromURL(jsonObj.getString("url_picture"));
						strPicture[i] = jsonObj.getString("url_picture");
						strTitle[i] = jsonObj.getString("name_place");
						strType[i] = jsonObj.getString("name_type");
						strDetail[i] = jsonObj.getString("detail_place");
						latLngs[i] = new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place"));
						Log.e("link data ", "" + strTitle[i]);
					}

					MyAdapter objMyAdapter = new MyAdapter(myContext, intIcon, strTitle, strType);
					Log.e("MyAdapter Two"," cre MyAdapter ");
					myListView = (ListView) myContext.findViewById(R.id.listView);
					myListView.setAdapter(objMyAdapter);

					myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//							if(count == 0)
//							{
								Log.e("if count", " " + count);
								Log.e("link data Pic 0", "" + strPicture[i]);
								//Toast.makeText(myContext.getApplicationContext(), "long = " + l + "  i = " + i + "name_place = " + strTitle[i] + "latLong = " + latLngs[i].latitude + "," + latLngs[i].longitude, Toast.LENGTH_LONG).show();
								SelectItem(strPicture[i], strTitle[i], strDetail[i], strType[i], latLngs[i].latitude, latLngs[i].longitude);

//							}
//							else {
//								Log.e("else count", " " + count);
//								frgManager.beginTransaction().remove(fragment);
//								Log.e("link data Pic 1", "" + strPicture[i]);
//								Toast.makeText(myContext.getApplicationContext(), "long = " + l + "  i = " + i + "name_place = " + strTitle[i] + "latLong = " + latLngs[i].latitude + "," + latLngs[i].longitude, Toast.LENGTH_LONG).show();
//								SelectItem(strPicture[i], strTitle[i], strDetail[i], strType[i], latLngs[i].latitude, latLngs[i].longitude);
//								count--;
//							}
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

		Button btnBack = (Button) myContext.findViewById(R.id.btnBackTwo);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e("onClickPL", "back click");

				//FragmentManager frgManager = getFragmentManager();
				//frgManager.beginTransaction().remove(FragmentTwo.this).commit();
				link.btnBackClick(FragmentTwo.this);

			}
		});

	}// end setup
	//	BitMAP


	public void SelectItem(String picture,String Title,String Detail,String Type,Double lat,Double lng) {

		fragment = null;
		Bundle args = new Bundle();

		fragment = new FragmentListPlace();
		args.putString(FragmentListPlace.TITLE, Title.replace("-", " ").replace("(", newline + "("));
		args.putString(FragmentListPlace.TYPE, Type);
		args.putString(FragmentListPlace.DETAIL, Detail);
		args.putDouble(FragmentListPlace.LAT, lat);
		args.putDouble(FragmentListPlace.LNG, lng);
		args.putString(FragmentListPlace.PICTURE, picture);


		fragment.setArguments(args);

		FragmentManager frgManager = getFragmentManager();
		//frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		frgManager.beginTransaction().add(R.id.content_frames, fragment).commit();

		count++;
		//this.onPause();
		//frgManager.beginTransaction().show(fragment).commit();

	}



}
