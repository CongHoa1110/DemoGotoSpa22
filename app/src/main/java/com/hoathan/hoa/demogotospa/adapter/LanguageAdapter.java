package com.hoathan.hoa.demogotospa.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.model.Language;
import com.hoathan.hoa.demogotospa.databinding.ItemLanguageBinding;
import com.hoathan.hoa.demogotospa.listener.ItemClickListener;
import com.hoathan.hoa.demogotospa.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageHolder> {

    private List<Language> mLanguageList = new ArrayList<>();
    private ItemClickListener<Language> mListener;
    private Language mCurrentLanguage = LanguageUtils.getCurrentLanguage();

    public LanguageAdapter(List<Language> languageList) {
        mLanguageList = languageList;
    }

    public void setListener(ItemClickListener<Language> listener) {
        mListener = listener;
    }

    public void setCurrentLanguage(Language language) {
        mCurrentLanguage = language;
        notifyDataSetChanged();
    }

    @Override
    public LanguageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLanguageBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_language, parent, false);
        return new LanguageHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(LanguageHolder holder, int position) {
        holder.mBinding.radioItemLanguage.setChecked(mCurrentLanguage.getId() == position);
        holder.bindLanguage(mLanguageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mLanguageList.size();
    }

    public class LanguageHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> name = new ObservableField<>();
        private ItemLanguageBinding mBinding;
        private Language mLanguage;

        LanguageHolder(ItemLanguageBinding binding, final ItemClickListener<Language> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setHolder(this);
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClickItem(getAdapterPosition(), mLanguage);
                    }
                }
            });
        }

        void bindLanguage(Language language) {
            mLanguage = language;
            name.set(language.getName());
        }
    }
}
