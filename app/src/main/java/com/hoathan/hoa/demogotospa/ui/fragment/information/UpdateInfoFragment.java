package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvToobarTitle;
    private Toolbar toolbar;
    private ImageView imgToobarBack;
    @Override
    protected int setLayout() {
        return R.layout.fragment_update_info;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
    }

    @Override
    protected void initialVariables() {

    }
    private void initUI(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        txvToobarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        txvToobarTitle.setText("Edit Profile");
        imgToobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        imgToobarBack.setOnClickListener(this);
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
