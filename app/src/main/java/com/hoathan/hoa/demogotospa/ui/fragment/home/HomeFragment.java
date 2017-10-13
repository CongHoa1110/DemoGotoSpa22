package com.hoathan.hoa.demogotospa.ui.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {

    }

    @Override
    protected void initialVariables() {

    }

}
