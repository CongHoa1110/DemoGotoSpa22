package com.hoathan.hoa.demogotospa.ui.fragment.information;



import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageFragment extends BaseFragment implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView imgToobarBack;
    private TextView txvToobarTitle;
    private TextView txvLangage;
    private TextView txvFonts;


    @Override
    protected int setLayout() {
        return R.layout.fragment_language;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
    initUI(view);
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
            case R.id.txv_language:
              baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new LanguageSelectedFragment(),true);
                break;
            case R.id.txv_fonts:
                showDialogFonrt();
                break;

        }

    }
    private void initUI(View view){
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        imgToobarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        imgToobarBack.setOnClickListener(this);
        txvToobarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        txvToobarTitle.setText("Language and Fornts");
        txvLangage = (TextView) view.findViewById(R.id.txv_language);
        txvLangage.setOnClickListener(this);
        txvFonts = (TextView) view.findViewById(R.id.txv_fonts);
        txvFonts.setOnClickListener(this);
    }

    private void showDialogLanguage(){
        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoglangaue);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private void showDialogFonrt(){
        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogfornt);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
