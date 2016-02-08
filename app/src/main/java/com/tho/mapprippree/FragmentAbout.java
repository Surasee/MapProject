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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbout extends Fragment {
    private FunctionLib link;
    private Activity myContext;
    private boolean status = true;

    public FragmentAbout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        Log.e("DEBUG", "onAttach of FragmentAbout ");
        myContext=(FragmentActivity) activity;
        link = new FunctionLib();
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of FragmentAbout " + status);
//        if(status){
//            myContext.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    setUp();
//                }
//            });
//        }
        setUp();

        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.e("DEBUG", "OnDestroy of FragmentAbout "+status);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("DEBUG", "OnDet of FragmentAbout "+status);
        link.btnBackClick(FragmentAbout.this);
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of FragmentAbout "+status);
        status = false;
       // link.btnBackClick(FragmentAbout.this);
        super.onPause();
    }


    private void setUp(){
        Button btnBack = (Button) myContext.findViewById(R.id.btnBackAbout);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClickBack", "back About click");
                //FragmentManager frgManager = getFragmentManager();
                //frgManager.beginTransaction().remove(FragmentTwo.this).commit();
                link.btnBackClick(FragmentAbout.this);

            }
        });
    }
}
