package com.hoathan.hoa.demogotospa.ui.fragment.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    public static NewsFragment newInstance() {
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {

    }

    @Override
    protected void initialVariables() {

    }

}
