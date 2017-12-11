package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends BaseFragment implements View.OnClickListener {
    private Button btnUpdateInfo;
    private ImageView imgInfoBack;


    @Override
    protected int setLayout() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        initUI(view);
    }

    private void initUI(View view) {

        btnUpdateInfo = (Button) view.findViewById(R.id.btn_update_info);
        btnUpdateInfo.setOnClickListener(this);
        imgInfoBack = (ImageView) view.findViewById(R.id.img_info_back);
        imgInfoBack.setOnClickListener(this);

    }

    @Override
    protected void initialVariables() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_info:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new UpdateInfoFragment(), true);
                break;
            case R.id.img_info_back:
                baseActivity.popFragment();
                break;

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Window window = getActivity().getWindow();
        /*window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //window.setStatusBarColor(getActivity().getResources().getColor(android.R.color.transparent));

       /* window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        window.setStatusBarColor(android.R.color.transparent);*/


    }

    @Override
    public void onPause() {
        super.onPause();
        Window window = getActivity().getWindow();

        /*window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);*/
       // window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
}
