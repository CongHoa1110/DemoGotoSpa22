package com.hoathan.hoa.demogotospa.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.interfaces.ToobarShwo;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.fragment.RegisterSpaFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.home.HomeFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.news.NewsFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.setting.SettingFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ToobarShwo{
    private FragmentTabHost tabHost;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FloatingActionButton flbtnAdd;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        setToobal();
        setTabHost();
        flbtnAdd = (FloatingActionButton) findViewById(R.id.fl_btn_add);
        flbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm Spa Mới", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                switchTab(1);
                setCurrentTab(TITLE_NEWS);
                pushFragment(RegisterSpaFragment.newInstance(),true );
                showToolbar(false);
                flbtnAdd.setVisibility(View.INVISIBLE);
               /* toolbar.setNavigationIcon(null);
                toolbar.setTitle(" Register ");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
*/

            }
        });


    }

    @Override
    protected void initialVariables() {

    }

    private void setToobal() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Host");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setTabHost() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec(TITLE_HOME).setIndicator(getTabIndicator(this, TITLE_HOME,
                R.drawable.ic_widget_host)), HomeFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec(TITLE_NEWS).setIndicator(getTabIndicator(this, TITLE_NEWS,
                R.drawable.ic_widget_new)), NewsFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec(TITLE_SETTINGS).setIndicator(getTabIndicator(this, TITLE_SETTINGS,
                R.drawable.ic_widget_settings)), SettingFragment.class, null);

        setCurrentTab(TITLE_HOME);
        pushFragment(HomeFragment.newInstance(), true);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {//tab id la tag va giong ten vs ten cua stack trong lop base

                setCurrentTab(tabId);

                // neu stack cua tab chuyen den chua  co phan tu nao thi push fragment cua tab day,
                // con ko thi se lay fragment cuoi cung cua stack do de hien thi
                if (typeStackMap.get(tabId).size() == 0) {
                    if (tabId.equals(TITLE_HOME)) {

                        pushFragment(HomeFragment.newInstance(), true);


                    } else if (tabId.equals(TITLE_NEWS)) {
                        pushFragment(NewsFragment.newInstance(), true);

                    } else if (tabId.equals(TITLE_SETTINGS)) {
                        pushFragment(SettingFragment.newInstance(), true);
                    }
                } else {
                    pushFragment(typeStackMap.get(tabId).lastElement(), false);
                }
                switch (tabId){
                    case TITLE_HOME:
                        showToolbar(true);
                        //toolbar.getNavigationIcon();
                        pushFragment(HomeFragment.newInstance(), false);
                        flbtnAdd.setVisibility(View.VISIBLE);
                        toolbar.setTitle("Host");

                        break;
                    case TITLE_NEWS:
                        showToolbar(true);
                       // toolbar.getNavigationIcon();
                        pushFragment(NewsFragment.newInstance(), false);
                        flbtnAdd.setVisibility(View.VISIBLE);
                        toolbar.setTitle("news");
                        break;
                    case TITLE_SETTINGS:
                        showToolbar(true);
                        //toolbar.getNavigationIcon();
                        pushFragment(SettingFragment.newInstance(), false);
                        flbtnAdd.setVisibility(View.INVISIBLE);
                        toolbar.setTitle("Setting");
                        break;


                }
            }
        });


    }

    private View getTabIndicator(Context context, String title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_widget, null);

        ImageView iconTab = (ImageView) view.findViewById(R.id.img_icon_tab);

        iconTab.setImageResource(icon);

        return view;
    }

    @Override
    protected int getFragmentContainerResID() {
        return android.R.id.tabcontent;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showToolbar(boolean show) {
        if (show) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }
    public void switchTab(int position){
        tabHost.setCurrentTab(position);
    }


    @Override
    public void toobarshwo() {
        showToolbar(true);
        Log.d("toobar", "toobarshwo: ");
    }
}
