package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hoathan.hoa.demogotospa.util.Comon.SPA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationMainFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rltProfile;
    private Toolbar toolbar;
    private CircleImageView imgAvata;
    private TextView txvName;

    private DatabaseReference spaReference;
    private FirebaseAuth mAuth;
    @Override
    protected int setLayout() {
        return R.layout.fragment_information_main;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        setHasOptionsMenu(true);
        initUI(view);
        getName();
    }

    @Override
    protected void initialVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_profile:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new ProfileFragment(), true);
                break;
        }

    }

    private void initUI(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        spaReference = firebaseDatabase.getReference(SPA_KEY);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_infomation);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        rltProfile = (RelativeLayout) view.findViewById(R.id.rlt_profile);
        rltProfile.setOnClickListener(this);
        imgAvata = (CircleImageView) view.findViewById(R.id.img_infomation_spa_Avatar);
        txvName = (TextView) view.findViewById(R.id.txv_infomation_spa_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.information, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_information_setting:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new SettingFragment(), true);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            if (user.getDisplayName() != null) {
                txvName.setText(user.getDisplayName().toString());
            }
            if (user.getPhotoUrl() != null) {
                Picasso.with(getActivity())
                        .load(user.getPhotoUrl())
                        .resize(50, 50)
                        .centerCrop()
                        .into(imgAvata);
            }
        }
    }
}
