package com.example.russianroulette;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter2 extends BaseAdapter {
    Context context;
    int gun_images2[];
    String[] skins;
    LayoutInflater inflter;

    public CustomAdapter2(Context applicationContext, int[] gun_images2, String[] skins) {
        this.context = applicationContext;
        this.gun_images2 = gun_images2;
        this.skins = skins;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return gun_images2.length;
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
        icon.setImageResource(gun_images2[i]);
        names.setText(skins[i]);
        return view;
    }
}