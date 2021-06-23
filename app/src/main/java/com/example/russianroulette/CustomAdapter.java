package com.example.russianroulette;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int gun_images[];
    String[] guns;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] gun_images, String[] guns) {
        this.context = applicationContext;
        this.gun_images = gun_images;
        this.guns = guns;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return gun_images.length;
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
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(gun_images[i]);
        names.setText(guns[i]);
        return view;
    }
}