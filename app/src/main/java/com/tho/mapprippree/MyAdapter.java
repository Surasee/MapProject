package com.tho.mapprippree;

/**
 * Created by Tho on 23/10/2558.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    //Explicit
    private Context objContext; //คอนเทน
    private Bitmap[] placeBitmap; //รหัสไอดีของภาพ
    private String[] placeDetail; //ข้อความ
    private String[] placeTitle;

    public MyAdapter(Context objContext, Bitmap[] placeBitmap, String[] placeTitle, String[] placeDetail) {
        this.objContext = objContext;
        this.placeBitmap = placeBitmap;
        this.placeDetail = placeDetail;
        this.placeTitle = placeTitle;
        Log.e("MyAdapter"," Create ");

    }

    @Override
    public int getCount() {
        return placeDetail.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("MyAdapter"," getView num "+i);
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = objLayoutInflater.inflate(R.layout.listplaceview, viewGroup, false);



        //Show Icon
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imvShowIcon);
        iconImageView.setImageBitmap(placeBitmap[i]);

        //Show Title
        TextView titleTextView = (TextView) view1.findViewById(R.id.txtShowTitle);
        titleTextView.setText(placeTitle[i]);

        //Show Detail
        TextView detailTextView = (TextView) view1.findViewById(R.id.txtShowDetail);
        detailTextView.setText(placeDetail[i]);

        //test
        return view1;
    }
}
