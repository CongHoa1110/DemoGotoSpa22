package com.hoathan.hoa.demogotospa.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hoathan.hoa.demogotospa.R;

/**
 * Created by Tungnguyenbk54 on 10/12/2017.
 */

public class ImageViewpageAdapter extends PagerAdapter {
    private int[] imge;
    private LayoutInflater inflater;

    public ImageViewpageAdapter(Context context, int[] imge) {
        this.imge = imge;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return imge.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_image_viewpager, container, false);
        ImageView imgFace = (ImageView) view.findViewById(R.id.img_viewpager_image);

        imgFace.setImageResource(imge[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
