package com.hoathan.hoa.demogotospa.ui.fragment.information;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.LanguageAdapter;
import com.hoathan.hoa.demogotospa.data.model.Language;
import com.hoathan.hoa.demogotospa.listener.ItemClickListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.hoathan.hoa.demogotospa.util.LanguageUtils;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageSelectedFragment extends BaseFragment {
    private LanguageAdapter mLanguageAdapter;
    private RecyclerView rclLanguage;

    @Override
    protected int setLayout() {
        return R.layout.fragment_language_selected;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        rclLanguage = (RecyclerView) view.findViewById(R.id.recycler_view_language);
        mLanguageAdapter = new LanguageAdapter(LanguageUtils.getLanguageData());
        mLanguageAdapter.setListener(new ItemClickListener<Language>() {
            @Override
            public void onClickItem(int position, Language language) {
                if (!language.getCode().equals(LanguageUtils.getCurrentLanguage().getCode())) {
                    onChangeLanguageSuccessfully(language);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rclLanguage.setLayoutManager(layoutManager);
        rclLanguage.setAdapter(mLanguageAdapter);
    }

    @Override
    protected void initialVariables() {

    }
    private void onChangeLanguageSuccessfully(final Language language) {
        mLanguageAdapter.setCurrentLanguage(language);
        LanguageUtils.changeLanguage(language);
        getActivity().setResult(RESULT_OK, new Intent());
       getActivity().finish();
    }
}
