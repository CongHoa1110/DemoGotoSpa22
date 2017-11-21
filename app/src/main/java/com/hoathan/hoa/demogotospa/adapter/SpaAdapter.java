package com.hoathan.hoa.demogotospa.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.SpaLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Tungnguyenbk54 on 8/22/2017.
 */

public class SpaAdapter extends ArrayAdapter<Spa> {

    private Context context;
    private int resource;
    private  ArrayList<Spa> arrayListSpa;
    private  ArrayList<Spa> arrayListSpaSearch;
    private SpaLikeListener onClickLike;

    public SpaAdapter(@NonNull Context context, @LayoutRes int resource,
                      @NonNull ArrayList<Spa> objects,SpaLikeListener onClickLike) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListSpa = objects;
        this.arrayListSpaSearch = new ArrayList<>();
        this.arrayListSpaSearch.addAll(arrayListSpa);
        this.onClickLike = onClickLike;
    }

    private class ViewHolde {
        ImageView imgSpa,imgSpaIconPoint;
        TextView txvSpaName;
        TextView txvSpaAddress;
        TextView txvSpaPhone;
        TextView txvSpaPoint;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolde holde;
        if (convertView == null) {
            holde = new ViewHolde();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_spa, parent, false);
            holde.imgSpa = (ImageView) convertView.findViewById(R.id.img_spa_custom);
            holde.txvSpaName = (TextView) convertView.findViewById(R.id.txv_spa_name);
            //holde.txvSpaPhone = (TextView) convertView.findViewById(R.id.txv_Spa_phone);
            holde.txvSpaAddress = (TextView) convertView.findViewById(R.id.txv_spa_address);
            holde.txvSpaPoint = (TextView) convertView.findViewById(R.id.txv_spa_point);
            holde.imgSpaIconPoint = (ImageView) convertView.findViewById(R.id.img_spa_icon_point);
            convertView.setTag(holde);
        } else {
            holde = (ViewHolde) convertView.getTag();

        }
        Spa spa = arrayListSpa.get(position);
        holde.txvSpaName.setText(spa.getSpaName());
        holde.txvSpaPhone.setText("phone : " + spa.getSpaPhone());
        holde.txvSpaAddress.setText("Address : " + spa.getSpaAddress());
        holde.txvSpaPoint.setText(spa.getSpaViews() + "");
        Random random = new Random();
        int vitri = random.nextInt(arrayListSpa.size());
        if (spa.getSpaImage() != null){
            Picasso.with(context).load(spa.getSpaImage().get(0)).resize(100, 100)
                    .centerCrop().into(holde.imgSpa);
        }
        holde.imgSpaIconPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLike.onClikLike(position);
            }
        });
        return convertView;
    }

    public void search(String textSearch) {
        textSearch = textSearch.toLowerCase(Locale.getDefault()).trim();
        arrayListSpa.clear();
        if (textSearch.length() == 0) {
            arrayListSpa.addAll(arrayListSpaSearch);
        } else {
            for (Spa name : arrayListSpaSearch)
                if (name.getSpaName().toLowerCase(Locale.getDefault()).contains(textSearch)) {
                    arrayListSpa.add(name);
                }
        }
       notifyDataSetChanged();
    }
}
