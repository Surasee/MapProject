package com.tho.mapprippree;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Tho on 28/10/2558.
 */
public class MySpinAdapter extends BaseAdapter{

    private Context objContext; //คอนเทน
    private String[] dataTitle; //รหัสไอดีของภาพ
    private int[] dataId; //ข้อความ
    private int spinID;

    public MySpinAdapter(Context objContext,String[] DataTitle,int[] DataId,int SpinID)
    {
        this.objContext = objContext;
        this.dataId = DataId;
        this.dataTitle = DataTitle;
        this.spinID = SpinID;

    }


    @Override
    public int getCount() {
        return dataId.length;
    }

    @Override
    public Object getItem(int i) {
        return dataTitle[i];
    }

    @Override
    public long getItemId(int i) {
        return dataId[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewSpin = objLayoutInflater.inflate(R.layout.listspinview,viewGroup,false);



        TextView tvTitle = (TextView) viewSpin.findViewById(R.id.tvTitleData);
        tvTitle.setText(dataTitle[i]);



        return viewSpin;
    }
}
