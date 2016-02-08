package com.tho.mapprippree;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentListPlace extends Fragment {

    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DETAIL = "detail";
    public static final String LAT = "lat";
    public static final String LNG = "long";
    public static final String PICTURE = "picture";


    private FragmentActivity myContext;
    private FunctionLib link;
    private boolean status = true;

    public FragmentListPlace() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_place, container, false);
        return view;

    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of FragmentPL " + status);
        if(status)
            myContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setUp();
                }
            });
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of FragmentPL "+status);
        status = false;
        super.onPause();
    }



    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        link = new FunctionLib();
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        Log.e("FragmentPL Destory"," TRUE ");
        //link.btnBackClick(FragmentListPlace.this);
        super.onDetach();

    }
    private void setUp() {
        //Show Picture
        ImageView imgPicture = (ImageView) myContext.findViewById(R.id.imvDetail);
        imgPicture.setImageBitmap(link.getBitmapFromURL(getArguments().getString(PICTURE)));

        //Show Title
        TextView tvTitle = (TextView) myContext.findViewById(R.id.tvTitleDetail);
        tvTitle.setText(getArguments().getString(TITLE));

        //Show Type
        TextView tvType = (TextView) myContext.findViewById(R.id.tvTypeDetail);
        tvType.setText(getArguments().getString(TYPE));

        //Show Detail
        TextView tvDetail = (TextView) myContext.findViewById(R.id.tvDetailf);
        tvDetail.setText(getArguments().getString(DETAIL));

        Button btnBack = (Button) myContext.findViewById(R.id.btnListBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("onClickPL", "back click");

//                FragmentManager frgManager = getFragmentManager();
//                frgManager.beginTransaction().remove(FragmentListPlace.this).commit();
                link.btnBackClick(FragmentListPlace.this);


            }
        });


    }// end setup






}
