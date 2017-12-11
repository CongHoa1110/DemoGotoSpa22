package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvToobarTitle;
    private Toolbar toolbar;
    private ImageView imgToobarBack;
    private FloatingActionButton flUpdateImage;
    private CircleImageView crAvata;
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
        crAvata = (CircleImageView) view.findViewById(R.id.img_update_spa_Avatar);
        flUpdateImage = (FloatingActionButton) view.findViewById(R.id.fl_update_image);
        flUpdateImage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_back:
                baseActivity.popFragment();
                break;
            case R.id.fl_update_image:
                CropImage.activity()
                        .start(getContext(), this);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                crAvata.setImageURI(resultUri);
                Log.d("Crop", "onActivityResult: " + resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("Crop", "onActivityResult: " + error);
            }
        }
    }
}
