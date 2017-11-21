package com.hoathan.hoa.demogotospa.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    public BaseActivity baseActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), container, false);

        baseActivity=(BaseActivity)getActivity();
        Bundle bundle = getArguments();
        initialViews(view, bundle);

        initialVariables();

        return view;
    }

    protected abstract int setLayout();

    protected abstract void initialViews(View view, Bundle bundle);

    protected abstract void initialVariables();

}
