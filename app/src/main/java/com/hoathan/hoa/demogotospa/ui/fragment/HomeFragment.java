package com.hoathan.hoa.demogotospa.ui.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.host.HostFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.news.NewsFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.information.InformationMainFragment;

import static com.hoathan.hoa.demogotospa.ui.base.BaseActivity.TITLE_HOST;
import static com.hoathan.hoa.demogotospa.ui.base.BaseActivity.TITLE_NEWS;
import static com.hoathan.hoa.demogotospa.ui.base.BaseActivity.TITLE_SETTINGS;

public class HomeFragment extends BaseFragment {
    private FragmentTabHost tabHost;
    //private BackBroadCast backBroadCast;
    IntentFilter filter;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        setTabHost(view);
    }


    @Override
    protected void initialVariables() {
    }

    /*
        @Override
        protected void onResume() {
            super.onResume();
            registerReceiver(backBroadCast, filter);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            unregisterReceiver(backBroadCast);
        }
    */
    private void setTabHost(View view) {

        tabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec(TITLE_HOST).setIndicator(getTabIndicator(getActivity(),
                R.drawable.ic_widget_host)), HostFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TITLE_NEWS).setIndicator(getTabIndicator(getActivity(),
                R.drawable.ic_widget_new)), NewsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TITLE_SETTINGS).setIndicator(getTabIndicator(getActivity(),
                R.drawable.ic_widget_menu)), InformationMainFragment.class, null);


       /* baseActivity.typeStackMap.get(TITLE_NEWS).set(1,new NewsFragment());
        baseActivity.typeStackMap.get(TITLE_SETTINGS).setSize(1);*/
       /* tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {//tab id la tag va giong ten vs ten cua stack trong lop base
                baseActivity.setCurrentTab(tabId);
                // neu stack cua tab chuyen den chua  co phan tu nao thi push fragment cua tab day,
                // con ko thi se lay fragment cuoi cung cua stack do de hien thi
                if (baseActivity.typeStackMap.get(tabId).size() == 0) {
                    if (tabId.equals(TITLE_HOST)) {

                    } else if (tabId.equals(TITLE_NEWS)) {
                        baseActivity.pushFragmentFull(new NewsFragment(), true);

                    } else if (tabId.equals(TITLE_SETTINGS)) {
                        baseActivity.pushFragmentFull(new InformationMainFragment(), true
                        );

                    }
                } else {
                    baseActivity.pushFragment(baseActivity.typeStackMap.get(tabId).lastElement(), false);
                }

            }
        });*/
    }

    private View getTabIndicator(Context context, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_widget, null);

        ImageView iconTab = (ImageView) view.findViewById(R.id.img_icon_tab);

        iconTab.setImageResource(icon);

        return view;
    }


    /* private void changerPassword(String password) {
         FirebaseUser user = mAuth.getCurrentUser();
         user.updatePassword(password).addOnCompleteListener(this, new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()) {
                     Toast.makeText(HomeFragment.this, "thay mat khau thanh cong", Toast.LENGTH_SHORT).show();
                 }
             }
         });

     }*/
    public void switchTab(int position) {
        tabHost.setCurrentTab(position);
    }

}
/*
    public class BackBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("send", "receive");
           showToolbar(true);
        }*/



