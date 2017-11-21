 package com.hoathan.hoa.demogotospa.ui.activity;

import android.os.Bundle;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.fragment.HomeFragment;

 public class HomeActivity extends BaseActivity {



     @Override
     protected int setLayout() {
         return R.layout.activity_home;
     }

     @Override
     protected void initialViews(Bundle savedInstanceState) {
         setCurrentTab(TITLE_HOST);
         pushFragment(new HomeFragment(),true);
     }

     @Override
     protected void initialVariables() {

     }
     @Override
     protected int getFragmentContainerResID() {
         return R.id.fram_home;
     }

     @Override
     protected int getFragmentContainerResIDFull() {
         return android.R.id.tabcontent;
     }
 }
