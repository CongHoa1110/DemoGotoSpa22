package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    private TextView txvinformation;
    private TextView txvGeneralSetting;
    private ImageView imgProfileBack;

    @Override
    protected int setLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
    }

    @Override
    protected void initialVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txv_profile_information:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new InformationFragment(),true);
                break;
            case R.id.img_profile_back:
                baseActivity.popFragment();
                break;
            case R.id.txv_profile_setting:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new SettingFragment(),true);
        }

    }

    private void initUI(View view) {
        txvinformation = (TextView) view.findViewById(R.id.txv_profile_information);
        txvinformation.setOnClickListener(this);
        imgProfileBack = (ImageView) view.findViewById(R.id.img_profile_back);
        imgProfileBack.setOnClickListener(this);
        txvGeneralSetting = (TextView) view.findViewById(R.id.txv_profile_setting);
        txvGeneralSetting.setOnClickListener(this);
    }
}
