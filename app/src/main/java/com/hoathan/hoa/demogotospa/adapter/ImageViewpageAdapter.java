package com.hoathan.hoa.demogotospa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tungnguyenbk54 on 10/12/2017.
 */

public class ImageViewpageAdapter extends PagerAdapter {
    /*private int[] imge;*/
    private Context context;
    private ArrayList<String> imge;
    private LayoutInflater inflater;
    private ImageViewpagerListener onClickImage;

    public ImageViewpageAdapter(Context context,  ArrayList<String> imge,ImageViewpagerListener onClickImage) {
        this.imge = imge;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onClickImage = onClickImage;

    }

    @Override
    public int getCount() {
        return imge.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_image_viewpager, container, false);
        ImageView imgFace = (ImageView) view.findViewById(R.id.img_viewpager_image);
       /* Picasso.with(context)
                .load(Uri.parse(imge.get(position)))
                .resize(50, 50)
                .centerCrop()
                .into(imgFace);*/

        try {
            Bitmap imageBitmap = decodeFromFireBaseBase64(imge.get(position));
            imgFace.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.addView(view);
        imgFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage.onClickImage(position);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public static Bitmap decodeFromFireBaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
