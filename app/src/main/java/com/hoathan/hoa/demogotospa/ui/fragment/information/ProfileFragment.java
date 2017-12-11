package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    private TextView txvinformation;
    private TextView txvGeneralSetting;
    private ImageView toobarBack;
    private TextView toobarTitile;
    private FirebaseAuth mAuth;

    @Override
    protected int setLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
        getName();
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
            case R.id.toolbar_back:
                baseActivity.popFragment();
                break;
            case R.id.txv_profile_setting:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new SettingFragment(),true);
        }

    }

    private void initUI(View view) {
        mAuth = FirebaseAuth.getInstance();
        txvinformation = (TextView) view.findViewById(R.id.txv_profile_information);
        txvinformation.setOnClickListener(this);
        toobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        toobarBack.setOnClickListener(this);
        txvGeneralSetting = (TextView) view.findViewById(R.id.txv_profile_setting);
        txvGeneralSetting.setOnClickListener(this);
        toobarTitile = (TextView) view.findViewById(R.id.toolbar_title);

    }
    private void getName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            if (user.getDisplayName() != null) {
                toobarTitile.setText(user.getDisplayName().toString());
            }

            }
        }

}
