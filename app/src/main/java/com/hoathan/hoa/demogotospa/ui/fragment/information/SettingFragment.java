package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.activity.LogInActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import static com.hoathan.hoa.demogotospa.util.Comon.SPA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgToobarBack;
    private TextView txvToobarTitle;
    private LinearLayout lnlanguae;
    private LinearLayout lnLogout;
    private LinearLayout lnAbout;

    private DatabaseReference spaReference;
    private FirebaseAuth mAuth;

    @Override
    protected int setLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUi(view);
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
            case R.id.ln_language:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new LanguageFragment(),true);
                break;
            case R.id.ln_log_out:
                showDialogLogout();
                break;
            case R.id.ln_about:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new AboutFragment(),true);
                break;
        }
    }

    private void initUi(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        spaReference = firebaseDatabase.getReference(SPA_KEY);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        imgToobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        imgToobarBack.setOnClickListener(this);
        txvToobarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        txvToobarTitle.setText("Setting");
        lnlanguae = (LinearLayout) view.findViewById(R.id.ln_language);
        lnlanguae.setOnClickListener(this);
        lnLogout = (LinearLayout) view.findViewById(R.id.ln_log_out);
        lnLogout.setOnClickListener(this);
        lnAbout = (LinearLayout) view.findViewById(R.id.ln_about);
        lnAbout.setOnClickListener(this);
    }
    private void showDialogLogout(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Notification");
        dialog.setMessage("Log out this account ? ");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(getActivity(), LogInActivity.class));
                    getActivity().finish();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
