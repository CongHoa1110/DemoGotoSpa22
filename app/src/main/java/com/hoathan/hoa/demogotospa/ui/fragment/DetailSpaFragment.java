package com.hoathan.hoa.demogotospa.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.ImageViewpageAdapter;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailSpaFragment extends BaseFragment implements OnMapReadyCallback,View.OnClickListener {
    private ImageView imgToobraBack;
    private TextView txvTitle;
    private TextView txvYearSpaDetail, txvOpentDetail, txvCloseDetail, txvAddressSpaDetail,
            txvDescriptionSpaDetail, txvPhoneSpaDetail, txvEmailSpaDetail;
    private ViewPager viewPager;
    private ImageViewpageAdapter adapter;

    private CircleIndicator circleIndicator;
    private ArrayList<String> imgViewPager;
    private static int curentpage = 0;
    private static int numpage = 0;

    private GoogleMap mMap;

    private HomeFragment homeFragment;


    @Override
    protected int setLayout() {
        return R.layout.fragment_detail_spa;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
        spaDetail();
        setHasOptionsMenu(true);
    }

    @Override
    protected void initialVariables() {

    }


    private void initUI(View view) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_detail);
        mapFragment.getMapAsync(this);
        imgToobraBack = (ImageView) view.findViewById(R.id.img_toobal_back_detail);
        imgToobraBack.setOnClickListener(this);
        txvTitle = (TextView) view.findViewById(R.id.txv_toobal_title_detail);
        txvOpentDetail = (TextView) view.findViewById(R.id.sp_detail_opent);
        txvCloseDetail = (TextView) view.findViewById(R.id.sp_detail_close);
        txvYearSpaDetail = (TextView) view.findViewById(R.id.sp_detail_Founded_year);
        txvAddressSpaDetail = (TextView) view.findViewById(R.id.txv_detail_address);
        txvDescriptionSpaDetail = (TextView) view.findViewById(R.id.txv_detail_desciption);
        txvPhoneSpaDetail = (TextView) view.findViewById(R.id.txv_detail_phone);
        txvEmailSpaDetail = (TextView) view.findViewById(R.id.txv_detail_Email);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_detail);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindicator_detail);

    }

    private void spaDetail() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Spa spa = (Spa) bundle.getSerializable("spa");
            txvTitle.setText(spa.getSpaName());
            txvYearSpaDetail.setText(spa.getSpaYear());
            txvOpentDetail.setText(spa.getSpaOpen());
            txvCloseDetail.setText(spa.getSpaClose());
            txvAddressSpaDetail.setText(spa.getSpaAddress());
            txvDescriptionSpaDetail.setText(spa.getSpaDescription());
            txvPhoneSpaDetail.setText(spa.getSpaPhone());
            txvEmailSpaDetail.setText(spa.getSpaEmail());
            imgViewPager = new ArrayList<>();
            if (spa.getSpaImage() != null){
                imgViewPager.addAll(spa.getSpaImage());

                adapter = new ImageViewpageAdapter(getActivity(), imgViewPager, new ImageViewpagerListener() {
                    @Override
                    public void onClickImage(int position) {

                    }
                });

                viewPager.setAdapter(adapter);
                circleIndicator.setViewPager(viewPager);
                setViewPager();
            }


        }


    }

    private void setViewPager() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curentpage = 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               /* if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pagecount = imgViewPager.size();
                    if (curentpage == 0) {
                        viewPager.setCurrentItem(pagecount - 1, false);
                    } else {
                        if (curentpage == pagecount - 1) {
                            viewPager.setCurrentItem(0, false);
                        }
                    }

                }*/
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (curentpage == numpage) {
                    curentpage = 0;

                }
                viewPager.setCurrentItem(curentpage++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Bundle bundle1 = getArguments();
        if (bundle1 != null){
            Spa spa = (Spa) bundle1.getSerializable("spa");
            mMap = googleMap;

            String spaLocation = spa.getSpaLocation();
            String[] latlong = spaLocation.split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            LatLng lng = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 16));
            mMap.addMarker(new MarkerOptions().position(lng)
                    .title(spa.getSpaName())
                    .snippet(spa.getSpaAddress()));

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toobal_back_detail:
                baseActivity.popFragment();
                break;
        }
    }


}



