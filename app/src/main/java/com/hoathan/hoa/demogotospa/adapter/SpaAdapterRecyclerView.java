package com.hoathan.hoa.demogotospa.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.OnLoadMoreListener;
import com.hoathan.hoa.demogotospa.listener.SpaItemListerner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Tungnguyenbk54 on 10/26/2017.
 */

public class SpaAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Spa> listSpa;
    private Context context;
    private ArrayList<Spa> arrayListSpaSearch;
    private OnLoadMoreListener mOnLoadMoreListener;
    private SpaItemListerner spaItemListerner;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean isLoading;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public SpaAdapterRecyclerView(RecyclerView mRecyclerView, Context context,
                                  ArrayList<Spa> listSpa, SpaItemListerner spaItemListerner) {

        this.listSpa = listSpa;
        this.context = context;
        this.arrayListSpaSearch = new ArrayList<>();
        this.arrayListSpaSearch.addAll(listSpa);
        this.spaItemListerner = spaItemListerner;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return listSpa.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_spa, parent, false);
            return new SpaViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_load, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    static class SpaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSpa, imgSpaIconPoint,imgSpaBinhLuan;
        ImageButton imgSpaIconPhone;
        LinearLayout lnSpaPhone;
        TextView txvSpaName;
        TextView txvSpaAddress;
        TextView txvSpaPhone;
        TextView txvSpaPoint;
        TextView txvSpaChiTiet;
        TextView txvSpaTime;
        TextView txvSpaEmail;

        public SpaViewHolder(View itemView) {
            super(itemView);
            imgSpaIconPhone = (ImageButton) itemView.findViewById(R.id.img_spa_icon_phone);
            imgSpa = (ImageView) itemView.findViewById(R.id.img_spa_custom);
            lnSpaPhone = (LinearLayout) itemView.findViewById(R.id.ln_spa_dienthoai);
            imgSpaBinhLuan = (ImageView) itemView.findViewById(R.id.img_spa_binhluan);
            txvSpaName = (TextView) itemView.findViewById(R.id.txv_spa_name);
            txvSpaEmail = (TextView) itemView.findViewById(R.id.txv_Spa_Email);
            txvSpaTime = (TextView) itemView.findViewById(R.id.txv_spa_time);
            txvSpaAddress = (TextView) itemView.findViewById(R.id.txv_spa_address);
            txvSpaChiTiet = (TextView) itemView.findViewById(R.id.txv_chitietSpa);
            txvSpaPoint = (TextView) itemView.findViewById(R.id.txv_spa_point);
            txvSpaPhone = (TextView) itemView.findViewById(R.id.txv_spa_phone);
            imgSpaIconPoint = (ImageView) itemView.findViewById(R.id.img_spa_icon_point);


        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SpaViewHolder) {
            Spa spa = listSpa.get(position);
            SpaViewHolder spaViewHolder = (SpaViewHolder) holder;
            spaViewHolder.txvSpaName.setText(spa.getSpaName());
            spaViewHolder.txvSpaTime.setText(spa.getSpaTime());
            spaViewHolder.txvSpaAddress.setText("Address: " + spa.getSpaAddress());
            spaViewHolder.txvSpaEmail.setText("Email: " + spa.getSpaEmail());
            spaViewHolder.txvSpaPoint.setText(spa.getSpaViews() + "");
            spaViewHolder.txvSpaPhone.setText(spa.getSpaPhone() + "");
          if (spa.getspaLike()){
              spaViewHolder.imgSpaIconPoint.setImageResource(R.drawable.icon_like);
          }else {
              spaViewHolder.imgSpaIconPoint.setImageResource(R.drawable.icon_nolike);

          }
            if (spa.getSpaImage() != null) {

                int vitri = spa.getSpaImage().size() - 1;
                Random random = new Random();
                int vt = random.nextInt(vitri);
                Picasso.with(context).load(spa.getSpaImage().get(vt)).resize(100, 100)
                        .centerCrop().into(spaViewHolder.imgSpa);
            }
          /*  holder.itemView.setOnClickListener(onClick_item_chitiet);
            holder.itemView.setTag(position);*/
            spaViewHolder.txvSpaChiTiet.setOnClickListener(onClick_item_chitiet);
            spaViewHolder.txvSpaChiTiet.setTag(position);
            spaViewHolder.imgSpaIconPoint.setOnClickListener(onClick_item_icon_point);
            spaViewHolder.imgSpaIconPoint.setTag(position);
            spaViewHolder.imgSpaBinhLuan.setOnClickListener(onClick_item_binhLuan);
            spaViewHolder.imgSpaBinhLuan.setTag(position);
            spaViewHolder.lnSpaPhone.setOnClickListener(onClick_item_phone);
            spaViewHolder.lnSpaPhone.setTag(position);


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    private View.OnClickListener onClick_item_chitiet = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            spaItemListerner.onClickItemChitiet(position);

        }
    };
    private View.OnClickListener onClick_item_icon_point = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            spaItemListerner.onClickItemlike(position);
        }
    };
    private View.OnClickListener onClick_item_binhLuan = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            spaItemListerner.onClickItemVBinhLuan(position);
        }
    };
    private View.OnClickListener onClick_item_phone = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            spaItemListerner.onClickItemPhone(position);
        }
    };
    @Override
    public int getItemCount() {
        return listSpa == null ? 0 : listSpa.size();
    }

    public void setLoaded(boolean load) {
        isLoading = load;
    }


    public void search(String textSearch) {
        textSearch = textSearch.toLowerCase(Locale.getDefault()).trim();
        listSpa.clear();
        if (textSearch.length() == 0) {
            listSpa.addAll(arrayListSpaSearch);
        } else {
            for (Spa name : arrayListSpaSearch)
                if (name.getSpaName().toLowerCase(Locale.getDefault()).contains(textSearch)) {
                    listSpa.add(name);
                }
        }
        notifyDataSetChanged();
    }
}
