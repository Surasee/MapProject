package com.tho.mapprippree;


import android.app.Activity;
import android.app.AlertDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class FragmentOne extends Fragment {

	public static final String UID = "uid";
	public static final String NAME_USER = "username";
	public static final String EMAIL_USER = "email";
	public static final String AGE_USER = "age";
	public static final String GENDER_USER = "gender";
	public static final String ID_CAREER = "career";
	public static final String ID_LANGUAGE = "language";
	public static final String ID_INTEREST = "interest";
	public static final String ID_GROUP = "group";

	private int percent = 100, download = 0, dataload = 0,infocount = 0;
	private boolean status = true,statusTrack = true,statusSub = true,statusAll = true;
	private String[] typeName = {"MAP_TYPE_NORMAL",
			"MAP_TYPE_SATELLITE", "MAP_TYPE_TERRAIN", "MAP_TYPE_HYBRID"};
	private int[] typeId = {1,2,3,4};
	private int[] typeIcon = {R.drawable.ic_globalx4,R.drawable.ic_globalx3,R.drawable.ic_globalx2,R.drawable.ic_global3};

	private String detailInfo,pictureInfo,idPlace,newline = System.getProperty("line.separator"),rank_place;
	private DecimalFormat df = new DecimalFormat("#.##");
	private float rating_cur = 0.0f;
	private GoogleMap mMap;
	private LatLng[] latLng_track;
	private double myLat=13.112398, myLong=99.940237;
	private LocationManager lm;
	private FragmentActivity myContext;
	private View view;
	private FunctionLib link;
	private String[] subtype, name_place, main_name_place;
	private int[] id_subtype, id_type;
	private int select_subtype = 1, select_name = 0,typeRun = 0,DialogSelect = 0;
	//private static final String url = "http://tho.ddns.net:2407/prippree/";
	private static final String url = "http://travelapp.pe.hu/prippree/";
	private boolean statusDialog = true;
	private ProgressDialog progress;
	Handler handler;
	Runnable runnable;
	long delay_time;
	long time = 4000L;


	private ImageButton btnPlaceAll, btnClear, btnStyle, btnSubType, btnTrack;
	private List<Marker> markers = new ArrayList<Marker>(), markersAll = new ArrayList<Marker>(), markers_subtype = new ArrayList<Marker>(),markersMain = new ArrayList<Marker>();

	public FragmentOne() {
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		int uid = getArguments().getInt(UID);
//		String name = getArguments().getString(NAME_USER);
//		String email = getArguments().getString(EMAIL_USER);
//		String gender = getArguments().getString(GENDER_USER);
//		int career = getArguments().getInt(ID_CAREER);
//		int group = getArguments().getInt(ID_GROUP);
//		int language = getArguments().getInt(ID_LANGUAGE);
//		int interest = getArguments().getInt(ID_INTEREST);
//		int age = getArguments().getInt(AGE_USER);
//		Log.e("uid",""+uid);
//		Log.e("name",""+name);
//		Log.e("email",""+email);
//		Log.e("age",""+age);
//		Log.e("gender",""+gender);
//		Log.e("language",""+language);
//		Log.e("career",""+career);
//		Log.e("interest",""+interest);
//		Log.e("group",""+group);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		view = inflater.inflate(R.layout.fragment_layout_one, container, false);


		return view;
	}

	@Override
	public void onResume() {
		Log.e("DEBUG", "OnResume of FragmentOne "+status);
		if(status)
			setUpMapIfNeeded();
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.e("DEBUG", "OnPause of FragmentOne "+status);
		status = false;
		super.onPause();
	}

	@Override
	public void onDetach() {
//		Intent i = new Intent(myContext, LoginActivity.class);
//		startActivity(i);
//		myContext.finish();
		super.onDetach();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) myContext.getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		}
		if (mMap != null) {
			setUpMap();
			//
		}
	}// end setUpMapIfNeeded




	private void setUpMap() {
		Log.e("Number of Core ", "" + Runtime.getRuntime().availableProcessors());
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
//		mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
//		mMap.getUiSettings().setMapToolbarEnabled(true);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
//		mMap.getUiSettings().setRotateGesturesEnabled(true);
//		mMap.getUiSettings().setScrollGesturesEnabled(true);
//		mMap.getUiSettings().setTiltGesturesEnabled(true);
//		mMap.getUiSettings().setZoomGesturesEnabled(true);
//		mMap.getUiSettings().setAllGesturesEnabled(true);

		myLocation();
		myContext.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				insertMakerMain();
				Log.e("insertMakerMain", " true");

				insertMaker(getArguments().getInt(ID_GROUP));
				Log.e("insertMaker", " true");

				insertMakerSubtype(getArguments().getInt(ID_INTEREST));
				Log.e("insertMakerSubType", " true");

				setInfoWindow();
				Log.e("setInfoWindow", " true");
				//progress.dismiss();

			}
		});

//		handler = new Handler();
//		runnable = new Runnable() {
//			public void run() {
//				// insert code here for delay
//			}
//		};
//		//handler.post(runnable);



//		progress = ProgressDialog.show(myContext, "dialog title",
//				"dialog message", true);
//
//		new Thread(new Runnable() {
//			@Override
//			public void run()
//			{
//				// do the thing that takes a long time

//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//
//						progress.dismiss();
//					}
//				});
//			}
//		}).start();


		//SET Button Style
		btnStyle = (ImageButton) myContext.findViewById(R.id.btnMapStyle);
		btnStyle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e("Type Run num 1 "," "+typeRun);
				int next = (typeRun+1)%typeId.length;
				mMap.setMapType((typeId[next]));
				Toast.makeText(myContext, typeName[next], Toast.LENGTH_LONG).show();
				btnStyle.setBackgroundResource(typeIcon[next]);
				typeRun = next;

			}
		});
		//END SET Button Style

		//SET Button Place All
		btnPlaceAll = (ImageButton) myContext.findViewById(R.id.btnplaceall);
		btnPlaceAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(myContext.getApplicationContext(),"Show All Place",Toast.LENGTH_LONG).show();
				if (statusAll) {
					statusAll = false;
					place_all();
				}
				else{
					Toast.makeText(myContext.getApplicationContext(),"Have shown",Toast.LENGTH_LONG).show();
				}

			}
		});
		//END SET Button Place All


		//SET Button Clear
		btnClear = (ImageButton) myContext.findViewById(R.id.btnclear);
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				statusAll = true;
				removeMarkerArray(markersAll);
				removeMarkerArray(markers_subtype);
				Toast.makeText(myContext.getApplicationContext(), "clear",Toast.LENGTH_LONG).show();
			}
		});
		//END SET Button Clear

		//SET Button Dialog
		btnSubType = (ImageButton) myContext.findViewById(R.id.btnSubtype);
		btnSubType.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (statusSub) {
					Toast.makeText(myContext.getApplicationContext(),"SubType Place",Toast.LENGTH_LONG).show();
					statusSub = false;
					try {
						String paramiter = "index.php?tag=subtype_list";
						Log.e("link subtype ", "" + url + paramiter);
						String resultServerg = link.getJSONUrl(paramiter);
						try {
							JSONObject c = new JSONObject(resultServerg);
							boolean error = c.getBoolean("error");
							if (!error) {
								JSONArray subtype_data = c.getJSONArray("data");
								int max_index = subtype_data.length();
								subtype = new String[max_index];
								id_subtype = new int[max_index];
								for (int i = 0; i < max_index; i++) {
									JSONObject jsonObj = subtype_data.getJSONObject(i);
									subtype[i] = jsonObj.getString("name_subtype");
									id_subtype[i] = jsonObj.getInt("id_subtype");
								}
								//AlertDialog
								AlertDialog.Builder builder = new AlertDialog.Builder(myContext);

								builder.setTitle(R.string.title_dialog_type);
								builder.setSingleChoiceItems(subtype, 0, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										select_subtype = id_subtype[which];
										//Toast.makeText(myContext.getApplicationContext(), "select " +
										//		subtype[which] + " id = " + id_subtype[which] + " select = " + select_subtype, Toast.LENGTH_LONG).show();
									}
								});
								//when yes
								builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										if (select_subtype > 0) {
											removeMarkerArray(markers_subtype);

											for (int j = 0; j < markers_subtype.size(); j++) {
												removeMarker(markers_subtype.get(j));
											}

											insertMakerSubtype(select_subtype);
											statusSub = true;
										}
									}
								});
								//when no
								builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialogInterface, int i) {
										statusSub = true;
									}
								});
								builder.create();
								builder.show();
								//End Dialog


							} else {
								Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
							}
						}
						catch(Exception er){
							Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
							Log.e("DeBug ", "er = " + er.toString());
						}

					} catch (Exception edb) {
						Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
						Log.e("DeBug ", "edb = " + edb.toString());
					}


					//Toast.makeText(myContext.getApplicationContext(), "Subtype Dialog", Toast.LENGTH_LONG).show();
				}
			}//if status


		});
		//END SET Button Dialog

		//SET Button Track place
		btnTrack = (ImageButton) myContext.findViewById(R.id.btnTrackPlace);
		btnTrack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (statusTrack) {
					Toast.makeText(myContext.getApplicationContext(),"Track Place",Toast.LENGTH_LONG).show();
					statusTrack = false;

					//AlertDialog
					AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
					builder.setTitle("Track Place");
					select_name = 0;
					DialogSelect = 0;
					builder.setSingleChoiceItems(main_name_place, 0, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							select_name = which;
							DialogSelect = which;
						}
					});
					//when yes
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {

							CameraPosition cameraPosition = new CameraPosition.Builder()
									.target(markersMain.get(DialogSelect).getPosition()).zoom(18).tilt(5).build();
							mMap.animateCamera(CameraUpdateFactory
									.newCameraPosition(cameraPosition));
							markersMain.get(DialogSelect).showInfoWindow();
							statusTrack = true;
							Log.e("MakerMain ", " Number = " + markersMain.size() + " i = " + i);
						}
					});
					//when no
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							statusTrack = true;
						}
					});
					builder.create();
					builder.show();
					//End Dialog
					//Toast.makeText(myContext.getApplicationContext(), "Track Dialog", Toast.LENGTH_LONG).show();


				}// end if status
			}
		});

		//END SET Track place




	}//end setUpMap

	//Location
	LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
//			LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());
//			Log.e("MyLocation = "," "+coordinate.toString());
//
//			myLat = location.getLatitude();
//			myLong = location.getLongitude();
//			//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15));
//			CameraPosition cameraPosition = new CameraPosition.Builder()
//					.target(coordinate).zoom(14).tilt(10).build();
//
//			mMap.animateCamera(CameraUpdateFactory
//					.newCameraPosition(cameraPosition));
		}
		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {}
		@Override
		public void onProviderEnabled(String s) {}
		@Override
		public void onProviderDisabled(String s) {}
	};

	private void myLocation()
	{
		lm = (LocationManager)myContext.getSystemService(Context.LOCATION_SERVICE);
		boolean isNetwork =
				lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPS =
				lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if(isNetwork) {
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
					, 60000, 10, listener);
			Location loc = lm.getLastKnownLocation(
					LocationManager.NETWORK_PROVIDER);
			if(loc != null) {
				myLat= loc.getLatitude();
				myLong = loc.getLongitude();
			}
		}
		if(isGPS) {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER
					, 60000, 10, listener);
			Location loc = lm.getLastKnownLocation(
					LocationManager.GPS_PROVIDER);
			if(loc != null) {
				myLat = loc.getLatitude();
				myLong = loc.getLongitude();
			}
		}

		LatLng coordinate = new LatLng(myLat, myLong);
		Log.e("MyLocation = "," "+coordinate.toString());
		//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15));
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(coordinate).zoom(14).tilt(10).build();

		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));


	}
	//End Location

	@Override
	public void onAttach(Activity activity) {
		myContext=(FragmentActivity) activity;
		link = new FunctionLib();

		super.onAttach(activity);
	}//end onAttach



	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mMap != null) {
			//myContext.getSupportFragmentManager().beginTransaction().remove(myContext.getSupportFragmentManager().findFragmentById(R.id.map)).commit();
			mMap = null;
		}

	}// end onDestroyView

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (mMap != null) {
			//setUpMap();
		}
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) myContext.getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// getMap is deprecated
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				//setUpMap();
			}
		}

	}//end onViewCreated

	// MakerMain
	private void insertMakerMain() {
		try {
			//String url = "http://www.travelapp.pe.hu/";
			//String url = "http://tho.ddns.net:2407/prippree/";
			String paramiter = "index.php?tag=show_place_main";
			//Log.e("link host main ", "" + url + paramiter);
			String resultServerg = link.getJSONUrl(paramiter);

			//Log.e("link host main ", "" + resultServerg);

			//Log.e("testWEBBBB", "Start = " + resultServerg+"End = "+ resultServerg.charAt(0));

			//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();

			//Log.d("GET_PLACE_WEB_HOST", resultServerg);

			try{
				download = 0;

				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				//Log.e("GET Boolen", ""+error);

				if(!error)
				{
					JSONArray place_data = c.getJSONArray("data");
					//Log.e("test main place ","num array = "+place_data.length());
					int max_data = place_data.length();
					dataload += max_data;
					main_name_place = new String[max_data];
					//Log.e("test main place ","true");
					//List<Marker> markersMain = new ArrayList<Marker>();
					for (int i = 0; i < max_data; i++) {
						download = ((i+1)*percent)/place_data.length();
						Log.e("Load DATA","place_main "+download+" %");
						//Log.e("test main inside ",""+place_data.getJSONObject(i).getString("name_place"));
						JSONObject jsonObj = place_data.getJSONObject(i);
						//Log.e("DeBug ", "Round = " + i);
						Marker marker = mMap.addMarker(new MarkerOptions()
								.title(jsonObj.getString("name_place"))
								.position(new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place")))
								.snippet(jsonObj.getString("name_type"))
								.icon(BitmapDescriptorFactory.fromBitmap(link.getBitmapFromURL(jsonObj.getString("icon_type")))));
						markersMain.add(marker);
						//Log.e("Marker main place ", "true - " + marker.getTitle());
						main_name_place[i] = jsonObj.getString("name_place");
					}
				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), "Error",Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}

	}// end MakerMain

	// Maker
	private void insertMaker(int group) {
		//Toast.makeText(myContext.getApplicationContext(), "test data from server",Toast.LENGTH_LONG).show();
		try {
			//String url = "http://www.travelapp.pe.hu/";
			//String url = "http://tho.ddns.net:2407/prippree/";
			String paramiter = "index.php?tag=show_place_group&id_group="+group+"&my_lat="+myLat+"&my_long"+myLong;
			Log.e("link host ",""+url+paramiter);
			String resultServerg = link.getJSONUrl(paramiter);
//			Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();

			//Log.d("GET_PLACE_WEB_HOST", resultServerg);
//			for (int i = 0; i <resultServerg.length(); i++) {
//				//Log.d("testJS3 & testJS2","JS3 = "+testJS3.charAt(i)+"  JS2 = "+testJS2.charAt(i));
//				Log.d("testWEBBBB", "JSWEB = " + resultServerg.charAt(i));
//			}

			try{
				download = 0;
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");

				if(!error)
				{
					//Toast.makeText(myContext.getApplicationContext(), "Data OK... ",Toast.LENGTH_LONG).show();
					JSONArray place_data = c.getJSONArray("data");
					int max_data = place_data.length();
					dataload += max_data;

					//Log.e("test json array 2D","" + place_data.getJSONObject(0));
					//Log.e("test json inside ", "" + place_data.getJSONObject(0).getString("name_place"));

					//List<Marker> markers = new ArrayList<Marker>();
					for (int i = 0; i < max_data; i++) {
						download = ((i+1)*percent)/max_data;
						Log.e("Load DATA", "place_group " + download + " %");
						JSONObject jsonObj = place_data.getJSONObject(i);
						String Title = jsonObj.getString("name_place");
						Marker marker = mMap.addMarker(new MarkerOptions()
								.title(jsonObj.getString("name_place"))
								.position(new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place")))
								.snippet(jsonObj.getString("name_type"))
								.icon(BitmapDescriptorFactory.fromBitmap(link.getBitmapFromURL(jsonObj.getString("icon_type")))));


						markers.add(marker);
						//Log.e("icon ", " " + link.getBitmapFromURL(jsonObj.getString("icon_type")));




					}//for


				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}

	}// end Maker

	// Makerall
	public void place_all() {

		removeMarkerArray(markers_subtype);
		try {
			//String url = "http://www.travelapp.pe.hu/";
			//String url = "http://tho.ddns.net:2407/prippree/";
			String paramiter = "index.php?tag=show_place_all&my_lat="+myLat+"&my_long="+myLong;
			//Log.e("link host allplace ",""+url+paramiter);
			String resultServerg = link.getJSONUrl(paramiter);
//			Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			//Log.d("GET_PLACE_WEB_HOST", resultServerg);
			try{
				//progress.show(myContext, "Wait!...", "Loading!...", true);
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				if(!error)
				{
					JSONArray place_data = c.getJSONArray("data");
					int max_data = place_data.length();

					//Log.e("test main",""+place_data.getJSONObject(0));
					//Log.e("test main inside ",""+place_data.getJSONObject(0).getString("name_place"));
					for (int i = 0; i < max_data; i++) {
						download = ((i+1)*percent)/max_data;
						//progress.setMessage("Loading..."+download+"%");
						Log.e("Load DATA", "place_all " + download + " %");
						//Toast.makeText(myContext.getApplicationContext(),"Downloading... "+download+"%" ,Toast.LENGTH_SHORT).show();
						JSONObject jsonObj = place_data.getJSONObject(i);
						Marker marker = mMap.addMarker(new MarkerOptions()
								.title(jsonObj.getString("name_place"))
								.position(new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place")))
								.snippet(jsonObj.getString("name_type"))
								.icon(BitmapDescriptorFactory.fromBitmap(link.getBitmapFromURL(jsonObj.getString("icon_type")))));
						markersAll.add(marker);
						//Log.e("download", " out if = " + download);

						if(download == percent)
						{
							//Log.e("download"," in if = "+download);
							//Log.e("download"," Finish "+download+"%");
						}

					}

				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}
		//progress.dismiss();




	}// end place All

	// MakerSubtype
	private void insertMakerSubtype(int id_subtype) {
		try {
			String paramiter = "index.php?tag=showplacesub&id_subtype="+id_subtype;
			//Log.e("link host main ", "" + url + paramiter);
			String resultServerg = link.getJSONUrl(paramiter);
			//Log.d("GET_PLACE_WEB_HOST", resultServerg);
			try{
				download = 0;
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				if(!error)
				{
					JSONArray place_data = c.getJSONArray("data");
					int max_data = place_data.length();
					//Log.e("test subtype",""+place_data.getJSONObject(0));
					for (int i = 0; i < max_data; i++) {
						download = ((i+1)*percent)/max_data;
						Log.e("Load DATA","place_sub "+download+" %");

						//Log.e("subtype inside name", "" + place_data.getJSONObject(i).getString("name_place"));
						JSONObject jsonObj = place_data.getJSONObject(i);
						Marker marker = mMap.addMarker(new MarkerOptions()
								.title(jsonObj.getString("name_place"))
								.position(new LatLng(jsonObj.getDouble("lat_place"), jsonObj.getDouble("long_place")))
								.snippet(jsonObj.getString("name_type"))
								.icon(BitmapDescriptorFactory.fromBitmap(link.getBitmapFromURL(jsonObj.getString("icon_type")))));
						markers_subtype.add(marker);
					}
				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}

	}// end Subtype

	// SetInfo
	private void setInfoWindow()	{
	//CUSTOM INFOWINDOW

		Log.e("SET INFO", " setInfoWindow()");
		mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				infocount++;
				download = ((infocount + 1) * percent) / dataload;
				Log.e("Load DATA", "getInfoWindow " + download + " %");
				Log.e("Load DATA", "name place " + marker.getTitle());
				//Log.e("getInfoContents Work", " TRUE ");
				float rating = 4.0f;
				int review = 0;
				View v = myContext.getLayoutInflater().inflate(R.layout.infowindow, null);
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				TextView tvType = (TextView) v.findViewById(R.id.tvType);
				TextView tvDetail = (TextView) v.findViewById(R.id.tvDetail);
				TextView tvSpin = (TextView) v.findViewById(R.id.tvSpin); // Check counter!!!
				TextView tvRating = (TextView) v.findViewById(R.id.tvRating);
				ImageView imgView = (ImageView) v.findViewById(R.id.imageView1);
				RatingBar ratingBar = (RatingBar) v.findViewById(R.id.RatBarInfo);


				try {
					String paramiter = "index.php?tag=search_place&my_keyplace=" + marker.getTitle().trim();
					Log.e("link marker ", "" + url + paramiter);
					String resultServerg = link.getJSONUrl(paramiter);
					try {
						JSONObject c = new JSONObject(resultServerg);
						boolean error = c.getBoolean("error");
						if (!error) {
							JSONArray place_data = c.getJSONArray("data");
							JSONObject jsonObj = place_data.getJSONObject(0);
							String Title = jsonObj.getString("name_place").replace("-", " ").replace("(", newline + "(");
							String detail = " ";


							detailInfo = jsonObj.getString("detail_place");
							pictureInfo = jsonObj.getString("url_picture");
							idPlace = jsonObj.getString("id_place");
							Log.e("rating id_place info ", "" + idPlace);

							rating = RatingValue(Integer.parseInt(idPlace));

							//Toast.makeText(myContext.getApplicationContext(), "pid=" + idPlace, Toast.LENGTH_LONG).show();
							//Log.e("marker Rating ", "pid=" + idPlace);


							if (jsonObj.getString("detail_place").length() > 100) {
								detail = jsonObj.getString("detail_place").substring(0, 80) + "...";
							}

							tvTitle.setText(Title);
							tvType.setText(marker.getSnippet());
							imgView.setImageBitmap(link.getBitmapFromURL(jsonObj.getString("url_picture")));
							tvDetail.setText(detail);

							ratingBar.setRating(rating);
							tvRating.setText(df.format(rating) + " Star "+ratingReview(Integer.parseInt(idPlace))+" Vote ");

							setOnInfoWindowClick();

						} else {
							Toast.makeText(myContext.getApplicationContext(), "Server not responding", Toast.LENGTH_LONG).show();
						}
					} catch (Exception er) {
						Toast.makeText(myContext.getApplicationContext(), er.toString(), Toast.LENGTH_LONG).show();
						Log.e("DeBug ", "er = " + er.toString());
					}

				} catch (Exception edb) {
					Toast.makeText(myContext.getApplicationContext(), edb.toString(), Toast.LENGTH_LONG).show();
					Log.e("DeBug ", "edb = " + edb.toString());
				}


				return v;
			}
		});


	}//CUSTOM INFOWINDOW

	//Set onMapClick
	private void setOnclickMap(){
		mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				mMap.addMarker(new MarkerOptions().position(latLng).title(
						String.valueOf(latLng.latitude) + " , "
								+ String.valueOf(latLng.longitude)));
			}
		});
	}

	//Set onInfowindowClick
	private void setOnInfoWindowClick(){

		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				final Dialog dialog = new Dialog(myContext);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setTitle(R.string.title_dialog_detail);
				dialog.setContentView(R.layout.detail_place);


				TextView tvTitleDetail = (TextView) dialog.findViewById(R.id.tvTitleDetail);
				TextView tvPlaceType = (TextView) dialog.findViewById(R.id.tvPlaceType);
				TextView tvPlaceDetail = (TextView) dialog.findViewById(R.id.tvPlaceDetail);

				TextView tvGiveRating = (TextView) dialog.findViewById(R.id.tvGiveR);

				ImageView imvDetail = (ImageView) dialog.findViewById(R.id.imvDetail);
				Button buttonOk = (Button) dialog.findViewById(R.id.btnDetailOk);
				String Title = "";
				Log.e("detail&picture Info", " " + detailInfo + " & " + pictureInfo);

				Title = (marker.getTitle().length() > 23) ? (marker.getTitle().substring(0, 24) + newline + marker.getTitle().substring(24)) : (marker.getTitle().replace("-", " ").replace("(", newline + "("));
				//Title = marker.getTitle().replace("-", " ").replace("(",newline+"(");
				Log.e("Title Info", " " + Title);
				String Type = marker.getSnippet();


				tvTitleDetail.setText(Title);
				tvPlaceType.setText(Type);
				tvPlaceDetail.setText(detailInfo);
				imvDetail.setImageBitmap(link.getBitmapFromURL(pictureInfo));


				//Insert Log code here
				Log.e("Inset to DB", " id_place = " + idPlace + " id_user = " + getArguments().getInt(UID));
				insetLog(Integer.parseInt(idPlace), getArguments().getInt(UID));

				tvGiveRating.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//Toast.makeText(myContext.getApplicationContext(), "pid=" + idPlace, Toast.LENGTH_LONG).show();
						ratingPlace(Integer.parseInt(idPlace),getArguments().getInt(UID));

					}
				});


				buttonOk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						//Insert Log code here
						//Log.e("Inset to DB", " id_place = " + idPlace+" id_user = "+getArguments().getInt(UID));
						dialog.dismiss();
					}
				});
				dialog.show();


			}
		});



		}//End Seton info window


	// Remove Marker
	private void removeMarker(Marker marker)
	{
		marker.remove();
	}
	private void removeMarkerArray(List<Marker> ArMarker)
	{
		for (int j = 0;j < ArMarker.size();j++)
		{
			removeMarker(ArMarker.get(j));
		}
	}



	private void insetLog(final  int pid,final int uid){
		try {
			String paramiter = "index.php?"+"tag=log_place"+"&uid="+uid+"&pid="+pid;
			String resultServerg = link.getJSONUrl(paramiter);
			Log.e("ADD LOG"," "+paramiter);
			//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			try{
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				JSONObject jsonObject = c.getJSONObject("data");

				if(!error)
				{

					Log.e("ADD LOG"," OK uid= "+jsonObject.getInt("id_user")+" pid= "+jsonObject.getInt("id_place"));
				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}



	}// insert log

	private void ratingPlace(final int pid, final int uid){ //check BUG************************
		//check place rating
		AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
		LayoutInflater inflater = myContext.getLayoutInflater();

		View view = inflater.inflate(R.layout.dialog_rating, null);
		builder.setView(view);
		builder.setTitle("Rating Place");
		final TextView tvUserRating = (TextView) view.findViewById(R.id.tvUserRating);
		final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingPlace);
		final String status_r = (check_user_rating(uid,pid))?"update":"insert";
		ratingBar.setRating(rating_cur);
		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				tvUserRating.setText("Rating " + df.format(rating)+" Star");
			}
		});

		ratingBar.setStepSize(1.0f);
		builder.setPositiveButton("Rating", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Check Rating
				try {
					float getRating = ratingBar.getRating();
					if(getRating < 1)
					{
						Log.e("DeBug ", "Rating = 0");
					}
					else {
						//Log.e("status_r "," = "+status_r);
						String paramiter = "index.php?tag=set_rating_place" + "&uid=" + uid + "&pid=" + pid + "&status=" + status_r + "&rating=" + getRating;
						String resultServerg = link.getJSONUrl(paramiter);
						Log.e("Rating " + status_r, " " + paramiter);
						//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
						try {
							JSONObject c = new JSONObject(resultServerg);
							boolean error = c.getBoolean("error");
							JSONObject jsonObject = c.getJSONObject("data");
							if (!error) {
								ratingBar.setRating(jsonObject.getInt("avg_rank"));
								Log.e("Rating Place", " OK uid= " + uid + " pid= " + jsonObject.getInt("id_place") + " Rating_all=" + jsonObject.getInt("avg_rank"));
							} else {
								Toast.makeText(myContext.getApplicationContext(), "Server not responding", Toast.LENGTH_LONG).show();
							}
						} catch (Exception er) {
							Toast.makeText(myContext.getApplicationContext(), er.toString(), Toast.LENGTH_LONG).show();
							Log.e("DeBug ", "er = " + er.toString());
						}
					}//else rating < 1

				} catch (Exception edb) {
					Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
					Log.e("DeBug ", "edb = " + edb.toString());
				}


			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.show();



		//end check place rating



	}

	private boolean check_user_rating(int uid,int pid){
		boolean ur_check = false;

		try {
			String paramiter = "index.php?tag=check_user_rating"+"&uid="+uid+"&pid="+pid;
			String resultServerg = link.getJSONUrl(paramiter);
			Log.e("Rating "," "+paramiter);
			//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			try{
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				JSONObject jsonObject = c.getJSONObject("data");
				if(!error)
				{
					ur_check = jsonObject.getBoolean("check");
//					if(ur_check)
//					{rating_cur = jsonObject.getInt("id_rank");}
//					else
//					{rating_cur = 0.0f;}
					rating_cur = (ur_check)?(jsonObject.getInt("id_rank")):(0.0f);
					Log.e("user Rating"," OK uid= "+uid+"rating= "+rating_cur+" check= "+ur_check);
				}
				else
				{
					Toast.makeText(myContext.getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();

				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}
		return ur_check;
	}

	private float RatingValue(int pid){
		float rating = 0.0f;
		try {
			String paramiter = "index.php?tag=get_rating_place"+"&pid="+pid;
			String resultServerg = link.getJSONUrl(paramiter);
			Log.e("Rating get"," "+paramiter);
			//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			try{
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				JSONObject jsonObject = c.getJSONObject("data");
				if(!error)
				{
					Log.e("Rating Place get","pid= "+jsonObject.getInt("id_place")+" Rating=" + jsonObject.getInt("avg_rank"));
					rating = Float.parseFloat(jsonObject.getString("avg_rank"));
				}
				else
				{
					rating = 0.0f;
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}

		return rating;

	}

	private int ratingReview(int pid){
		int countRating = 0;
		try {
			String paramiter = "index.php?tag=count_rating_place"+"&pid="+pid;
			String resultServerg = link.getJSONUrl(paramiter);
			Log.e("Count Rating get"," "+paramiter);
			//Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
			try{
				JSONObject c = new JSONObject(resultServerg);
				boolean error = c.getBoolean("error");
				JSONObject jsonObject = c.getJSONObject("data");
				if(!error)
				{
					Log.e("Count Rating Place get","pid= "+jsonObject.getInt("id_place")+" Count Rating=" + jsonObject.getInt("count_rank"));
					countRating = jsonObject.getInt("count_rank");
				}
				else
				{
					countRating = 0;
				}
			}
			catch(Exception er){
				Toast.makeText(myContext.getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
				Log.e("DeBug ", "er = " + er.toString());
			}

		} catch (Exception edb) {
			Toast.makeText(myContext.getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
			Log.e("DeBug ", "edb = " + edb.toString());
		}
		return countRating;
	}




}
