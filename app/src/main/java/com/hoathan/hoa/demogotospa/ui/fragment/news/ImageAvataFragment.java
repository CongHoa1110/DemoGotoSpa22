package com.hoathan.hoa.demogotospa.ui.fragment.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.ImageViewpageAdapter;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageAvataFragment extends BaseFragment implements View.OnClickListener {
    private ArrayList<String> imgViewPager;
    private ImageViewpageAdapter adapter;
    private ViewPager viewPagerImageSpa;
    private CircleIndicator circleIndicator;
    private TextView toobarTitile;
    private ImageView toobarBack;


    @Override
    protected int setLayout() {
        return R.layout.fragment_image_avata;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {

        initUI(view);
        spaImage();
    }

    @Override
    protected void initialVariables() {

    }

    private void initUI(View view) {
        viewPagerImageSpa = (ViewPager) view.findViewById(R.id.viewpager_comment);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindicator_comment);
        toobarTitile = (TextView) view.findViewById(R.id.toolbar_title);
        toobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        toobarBack.setOnClickListener(this);
        imgViewPager = new ArrayList<>();
    }
    private void spaImage() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> imgSpa = bundle.getStringArrayList("SpaImage");

                imgViewPager.addAll(imgSpa);
                adapter = new ImageViewpageAdapter(getActivity(), imgViewPager, new ImageViewpagerListener() {
                    @Override
                    public void onClickImage(int position) {

                    }
                });

                viewPagerImageSpa.setAdapter(adapter);
                circleIndicator.setViewPager(viewPagerImageSpa);

            toobarTitile.setText(bundle.getString("nameSpa"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.color_black));
    }

    @Override
    public void onPause() {
        super.onPause();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back:
                baseActivity.popFragment();
                break;
        }
    }
}
