package com.tho.mapprippree;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Main extends ActionBarActivity {


    private int uid,age,career,language,interest,group, typeRun = 0,searchInt = 1;
    private String name,email,gender,password,nameplace,typeplace,iconplace;
    private FunctionLib link;

    private String[] typeName = {"MAP_TYPE_NORMAL",
            "MAP_TYPE_SATELLITE", "MAP_TYPE_TERRAIN", "MAP_TYPE_HYBRID"};
    private int[] typeId = {1,2,3,4};
    private int[] typeIcon = {R.drawable.ic_globalx4,R.drawable.ic_globalx3,R.drawable.ic_globalx2,R.drawable.ic_global3};

    private boolean status = false,status2 = false;

    private Fragment fragment;
    private FragmentManager frgManager;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;

    private LatLng select_place;
    private List markers = new ArrayList<Marker>();



    private LatLng LL;
    private GoogleMap mMap;
    ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
    private int intPage = 3; // Menu myPosition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle intentex = getIntent().getExtras();
        uid = intentex.getInt("uid");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");
        age = intentex.getInt("age");
        career = intentex.getInt("career");
        language = intentex.getInt("language");
        interest = intentex.getInt("interest");
        group = intentex.getInt("group");

       // Log.e("check name main ",""+name);
        link = new FunctionLib();
        welcome();


        itemTitle = activityTitle = getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
//        if (getSupportActionBar() != null)
//        {
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setIcon(R.drawable.icon);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1fb08d")));
//        }
            drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        items.add(new DrawerItem(" Profile Menu ( "+name+" )")); // add a header
        items.add(new DrawerItem("Edit Profile", R.drawable.edit_user,false));
        items.add(new DrawerItem(" Map Menu")); // add a header
        items.add(new DrawerItem("My Position  ", R.drawable.my_posiotion,false));
        items.add(new DrawerItem("List Places", R.drawable.list_place,false));
        items.add(new DrawerItem("Search Places", R.drawable.search, false));
        items.add(new DrawerItem("Map Style", R.drawable.mapstyle, false));
        items.add(new DrawerItem(" Other Menu")); // add a header
        items.add(new DrawerItem("About", R.drawable.about, false));
        items.add(new DrawerItem("Exit", R.drawable.exit, false));
        drawerList.setAdapter(new DrawerListAdapter(this, R.layout.drawer_list_item, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                // getSupportActionBar().setTitle(itemTitle);
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //  getSupportActionBar().setTitle(activityTitle);
                //  invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        if (savedInstanceState == null) {
            if (items.get(0).getTitle() != null) {

                SelectItem(intPage);

            } else {
                SelectItem(0);
            }
        }
        //run F1



    } // End oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Do onOptionItem Main"," True");
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("Do onItemClickAd Main", " True");
            int[] pos_check = {1,3,4,5,6,8,9};
            try {
                Log.e(" check if Arrays ","  "+checkArrays(pos_check,position));
                 if(checkArrays(pos_check,position)){
                    Log.e("position  Main", " 3 5 6 9 ");

                    for(int j = 0; j < items.size();j++){
                        Log.e("Do onItemClick Main3.0"," "+items.get(j).getItemName()+"  "+items.get(position).isChecked);
                        items.get(j).setChecked(false);
                    }
                    if (items.get(position).getTitle() == null) {//Check HERE !!!!!!!!!!

                        Log.e("Do onItemClickAd Main", " items getTitle() == null 3569");
//                        Anti Bug load time
//                        if(position == 2)
//                        { items.get(position).setChecked(true); }

                        SelectItem(position);
                    }
                }

                else if (items.get(position).isChecked == false){
                    Log.e("Do onItemClickAd Main"," items.isChecked == false");
                    for(int j = 0; j < items.size();j++){
                        Log.e("Do onItemClickAd Main"," "+items.get(j));
                        items.get(j).setChecked(false);
                    }
                    if (items.get(position).getTitle() == null) {//Check HERE !!!!!!!!!!

                        Log.e("Do onItemClickAd Main", " items.get(position).getTitle() == null");

                        //Anti Bug load time
                        items.get(position).setChecked(true);

                        SelectItem(position);

                    }

                }
//              else if(position == 3 || position == 5 ||position == 6 || position == 9){
//                  Log.e("position  Main", " 3 5 6 9 ");
//
//                  ////////////
//                  for(int j = 0; j < items.size();j++){
//                      Log.e("Do onItemClickAd Main"," "+items.get(j).getItemName()+"  "+items.get(position).isChecked);
//                      items.get(j).setChecked(false);
//                  }
//
//                  items.get(position).setChecked(true);
//                  Log.e("Do onItemClickAd Main"," "+items.get(position).isChecked+"  "+items.get(position).getItemName());
//                  ////////////
//                  drawerLayout.closeDrawer(drawerList);
//                  SelectItem(position);
//              }
              else{
                    drawerLayout.closeDrawer(drawerList);
                    Log.e("Do onItemClickAd Main", " closeDrawer");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SelectItem(int possition) {
        Log.e("Main Debug","Case "+possition);
        fragment = null;

        Bundle args = new Bundle();
        switch (possition) {
            case 1:
                fragment = new Editprofile();
                args.putString(Editprofile.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(Editprofile.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());

                args.putInt(Editprofile.UID, uid);
                args.putString(Editprofile.NAME_USER, name);
                args.putString(Editprofile.PASSWORD_USER, password);
                args.putString(Editprofile.EMAIL_USER, email);
                args.putInt(Editprofile.AGE_USER, age);
                args.putString(Editprofile.GENDER_USER, gender);
                args.putInt(Editprofile.ID_CAREER, career);
                args.putInt(Editprofile.ID_LANGUAGE, language);
                args.putInt(Editprofile.ID_INTEREST, interest);
                args.putInt(Editprofile.ID_GROUP, group);
                Log.e("case ", " 1 ");
                break;
            case 3:
                if(!status) {
                    fragment = new FragmentOne();
                    args.putInt(FragmentOne.UID, uid);
                    args.putString(FragmentOne.NAME_USER, name);
                    args.putString(FragmentOne.EMAIL_USER, email);
                    args.putInt(FragmentOne.AGE_USER, age);
                    args.putString(FragmentOne.GENDER_USER, gender);
                    args.putInt(FragmentOne.ID_CAREER, career);
                    args.putInt(FragmentOne.ID_LANGUAGE, language);
                    args.putInt(FragmentOne.ID_INTEREST, interest);
                    args.putInt(FragmentOne.ID_GROUP, group);
                    status = true;
                    }
                else {
                    fragment = null;
                    if (mMap == null) {
                        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                    }
                    if (mMap != null) {
                        LL = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                        Log.e("LL "," "+LL);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(LL).zoom(14).tilt(10).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
                Log.e("case ", " 3 ");
                break;
            case 4:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, items.get(possition).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, items.get(possition).getImgResID());

                Log.e("case ", " 4 ");
                break;
            case 5:
                  //Search Place
                    search_place();
                Log.e("case ", " 5 ");
                break;
            case 6:
                if (mMap == null) {
                    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                }
                if (mMap != null) {
                    Log.e("Type Run num 1 ", " " + typeRun);
                    ImageButton btnStyle = (ImageButton) findViewById(R.id.btnMapStyle);
                    int next = (typeRun+1)%typeId.length;
                    mMap.setMapType((typeId[next]));
                    Toast.makeText(getApplicationContext(), typeName[next], Toast.LENGTH_LONG).show();
                    //+" ID =  "+mMap.getMapType()+"TypeRun = "+ typeRun
                    btnStyle.setBackgroundResource(typeIcon[next]);
                    typeRun = next;
                    Log.e("Type Run num 2 ", " " + typeRun);
                }
                Log.e("case ", " 6 ");
                break;
            case 8:
                fragment = new FragmentAbout();
                Log.e("case ", " 8 ");
                break;
            case 9:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Back");
                dialog.setIcon(R.drawable.ic_app);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to back?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                Log.e("case ", " 9 ");
                break;

            default:
                Log.e("case ", " d ");
                break;
        }

        Log.e("Do code set", "itemselect" + possition);
        drawerList.setItemChecked(possition,true);
        Log.e("Do draList", "true" + possition);
        items.get(possition).setChecked(true);
        Log.e("Do item.get", "true" + possition);
        setTitle(items.get(possition).getItemName());
        Log.e("Do settitle", "true" + items.get(possition).getItemName());
        drawerLayout.closeDrawer(drawerList);
        Log.e("Do drawerLayout", "closeDrawer(drawerList);");


        fragment.setArguments(args);
        frgManager = getFragmentManager();

        if(fragment != null&& status) {
            if(possition != 3)
            {
                frgManager.beginTransaction().replace(R.id.content_frames, fragment).commit();

                //status = false;
            }
            else {
                Log.e("Do create F", " case" + possition);

                frgManager.beginTransaction().add(R.id.content_frame, fragment).commit();
                Log.e("Do create F", " Num of F " + frgManager.getBackStackEntryCount());
            }

        }



    }
    @Override
    public void setTitle(CharSequence title) {
        Log.e("Do setTitle Main"," True");
        itemTitle = title;
//        if (getSupportActionBar() != null)
//        {
//            getSupportActionBar().setTitle(itemTitle);
//        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.e("Do onPost Main", " True");
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("Do onConfig Main"," True");
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    public void onBackPressed() {
        Log.e("OnBackPressed", " True ");
    }

    public void hideKeyBoard(View v){
        Log.e("OverLap hideKeyBoard", " true view = "+v.toString());
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }


    private void search_place(){

        Log.e("Search ", " run ");

        //AlertDialog
        final Dialog dialog = new Dialog(Main.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog);

        final EditText key_place = (EditText) dialog.findViewById(R.id.keyplace);
        Button btnClose = (Button) dialog.findViewById(R.id.btnCloseS);
        Button btnSearch = (Button) dialog.findViewById(R.id.btnSearch);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_list(key_place.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.show();
        //End Dialog
    }//end search

    private void search_list(String key) {
        final String[] name_plce;
        final String[] type_place;
        final String[] icon_place;
        final int[] id_place;
        final LatLng[] Latlong;

        try {
            String paramiter = "index.php?tag=search_place&my_keyplace=" + key;
            Log.e("search Places ", ""+ paramiter);
            String resultServerg = link.getJSONUrl(paramiter);
            try {
                JSONObject c = new JSONObject(resultServerg);
                boolean error = c.getBoolean("error");
                if (!error) {
                    JSONArray place_data = c.getJSONArray("data");
                    int max_index = place_data.length();
                    name_plce = new String[max_index];
                    type_place = new String[max_index];
                    icon_place = new String[max_index];
                    id_place = new int[max_index];
                    Latlong = new LatLng[max_index];

                    for (int i = 0; i < max_index; i++) {
                        JSONObject jsonObj = place_data.getJSONObject(i);
                        id_place[i] = jsonObj.getInt("id_place");
                        Log.e("MySearch id_place = ", " " + id_place[i]);
                        name_plce[i] = jsonObj.getString("name_place");
                        Latlong[i] = new LatLng(jsonObj.getDouble("lat_place"),jsonObj.getDouble("long_place"));
                        type_place[i] = jsonObj.getString("name_type");
                        icon_place[i] = jsonObj.getString("icon_type");
                    }


                    //AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                    builder.setTitle(R.string.search_settings);
                    searchInt = id_place[0];
                    select_place = Latlong[0];
                    nameplace = name_plce[0];
                    typeplace = type_place[0];
                    iconplace = icon_place[0];
                    builder.setSingleChoiceItems(name_plce, 0, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("MySearch0 which = ", " " + which);
                            searchInt = id_place[which];
                            select_place = Latlong[which];
                            nameplace = name_plce[which];
                            typeplace = type_place[which];
                            iconplace = icon_place[which];

                            Log.e("MySearch1 = ", " " + select_place.toString());
                            //Toast.makeText(myContext.getApplicationContext(), "select " +
                            //		subtype[which] + " id = " + id_subtype[which] + " select = " + select_subtype, Toast.LENGTH_LONG).show();
                        }
                    });

                    //when yes
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Log.e("MySearch2 = ", " " + searchInt);
                            if (searchInt > 0) {
                                if (mMap == null) {
                                    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
                                }
                                if (mMap != null) {
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(select_place).zoom(20).tilt(10).build();
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    final Marker marker = mMap.addMarker(new MarkerOptions()
                                            .title(nameplace)
                                            .position(select_place)
                                            .snippet(typeplace)
                                            .icon(BitmapDescriptorFactory.fromBitmap(link.getBitmapFromURL(iconplace))));
                                    markers.add(marker);
                                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                        @Override
                                        public void onMapLongClick(LatLng latLng) {
                                            removeMarkerArray(markers);

                                        }
                                    });


                                }
                            }// searchInt > 0
                            }
                    });

                    //when no
                        builder.setNegativeButton("No",new DialogInterface.OnClickListener()

                        {
                            @Override
                            public void onClick (DialogInterface dialogInterface,int i){

                        }
                    });
                    //builder.create();
                    builder.show();
                    //End Dialog





                } else {
                    Toast.makeText(getApplicationContext(), "Server not responding",Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception er){
                Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                Log.e("DeBug ", "er = " + er.toString());
            }

        } catch (Exception edb) {
            Toast.makeText(getApplicationContext(), edb.toString(),Toast.LENGTH_LONG).show();
            Log.e("DeBug ", "edb = " + edb.toString());

        }
    }//end search list

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

    private void welcome(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(Main.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_welcome, null);
        builder.setView(view);
        builder.setCancelable(false);

        final TextView NameW = (TextView) view.findViewById(R.id.tvNameWel);
        NameW.setText(name);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();


    }


    private boolean checkArrays(int[] array,int value){

        for(int i = 0;i<=array.length;i++)
        {
            Log.e("check A ","i = "+array[i]+"  value = "+value);
            if(array[i] == value)
                return true;
        }
        return false;
    }

}// End class