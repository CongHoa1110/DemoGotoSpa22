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
public class AboutFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView toobarBack;
    private TextView toobarTitle;

    @Override
    protected int setLayout() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
    }

    private void initUI(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        toobarBack.setOnClickListener(this);
        toobarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        toobarTitle.setText("About");
    }

    @Override
    protected void initialVariables() {

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
