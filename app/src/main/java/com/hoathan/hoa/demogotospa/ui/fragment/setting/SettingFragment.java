package com.hoathan.hoa.demogotospa.ui.fragment.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {

    }

    @Override
    protected void initialVariables() {

    }
}
