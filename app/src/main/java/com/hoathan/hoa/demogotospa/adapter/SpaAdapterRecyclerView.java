package com.hoathan.hoa.demogotospa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;
import com.hoathan.hoa.demogotospa.listener.LoginGoogleListener;
import com.hoathan.hoa.demogotospa.listener.OnLoadMoreListener;
import com.hoathan.hoa.demogotospa.listener.SpaItemListerner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

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
    private LoginGoogleListener loginGoogleListener;

    public void setLoginGoogleListener(LoginGoogleListener loginGoogleListener){
        this.spaItemListerner = spaItemListerner;
    }

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
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview_spa, parent, false);
            return new SpaViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_load, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    static class SpaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSpaAvata, imgSpaIconPoint, imgSpaBinhLuan;
        ImageButton imgSpaIconPhone;
        LinearLayout lnSpaPhone;
        TextView txvSpaName;
        TextView txvSpaAddress;
        TextView txvSpaPhone;
        TextView txvSpaPoint;
        TextView txvSpaChiTiet;
        TextView txvSpaTime;
        TextView txvSpaEmail;
        TextView txvSpadescription;
        private ViewPager viewPager;
        private CircleIndicator indicator;
        ImageView imgMenuItem;

        public SpaViewHolder(View itemView) {
            super(itemView);
            imgSpaIconPhone = (ImageButton) itemView.findViewById(R.id.img_spa_icon_phone);
            imgSpaAvata = (ImageView) itemView.findViewById(R.id.img_spa_custom);
            lnSpaPhone = (LinearLayout) itemView.findViewById(R.id.ln_spa_dienthoai);
            imgSpaBinhLuan = (ImageView) itemView.findViewById(R.id.img_spa_binhluan);
            imgMenuItem = (ImageView) itemView.findViewById(R.id.img_item_menu);
            txvSpaName = (TextView) itemView.findViewById(R.id.txv_spa_name);
            txvSpadescription = (TextView) itemView.findViewById(R.id.txv_item_spa_description);
            txvSpaEmail = (TextView) itemView.findViewById(R.id.txv_Spa_Email);
            txvSpaTime = (TextView) itemView.findViewById(R.id.txv_spa_time);
            txvSpaAddress = (TextView) itemView.findViewById(R.id.txv_spa_address);
            txvSpaChiTiet = (TextView) itemView.findViewById(R.id.txv_chitietSpa);
            txvSpaPoint = (TextView) itemView.findViewById(R.id.txv_spa_point);
            txvSpaPhone = (TextView) itemView.findViewById(R.id.txv_spa_phone);
            imgSpaIconPoint = (ImageView) itemView.findViewById(R.id.img_spa_icon_point);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewpager_item);
            indicator = (CircleIndicator) itemView.findViewById(R.id.circleindicator_item);


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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SpaViewHolder) {
            Spa spa = listSpa.get(position);
            final SpaViewHolder spaViewHolder = (SpaViewHolder) holder;
            spaViewHolder.txvSpaName.setText(spa.getSpaName());
            spaViewHolder.txvSpaTime.setText("Register Date: "+spa.getSpaTime());
            spaViewHolder.txvSpaAddress.setText("Address: " + spa.getSpaAddress());
            spaViewHolder.txvSpadescription.setText(spa.getSpaDescription());
            spaViewHolder.txvSpaPoint.setText(spa.getSpaViews() + "");
            //spaViewHolder.txvSpaPhone.setText(spa.getSpaPhone() + "");
            if (spa.getspaLike()) {
                spaViewHolder.imgSpaIconPoint.setImageResource(R.drawable.icons_like);
            } else {
                spaViewHolder.imgSpaIconPoint.setImageResource(R.drawable.icon_nolike);

            }
            if (spa.getSpaImage() != null) {

                /*int vitri = spa.getSpaImage().size() - 1;
                Random random = new Random();
                int vt = random.nextInt(vitri);*/

                try {
                    Bitmap imageBitmap = decodeFromFireBaseBase64(spa.getSpaImage().get(0));
                    spaViewHolder.imgSpaAvata.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*Picasso.with(context).load(Uri.parse(spa.getSpaImage().get(0))).resize(100, 100)
                        .centerCrop().into(spaViewHolder.imgSpaAvata);*/
            }
          /*  holder.itemView.setOnClickListener(onClick_item_chitiet);
            holder.itemView.setTag(position);*/
            spaViewHolder.imgMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenuItem(spaViewHolder.imgMenuItem,position);
                }
            });
            spaViewHolder.imgMenuItem.setTag(position);
            spaViewHolder.imgSpaIconPoint.setOnClickListener(onClick_item_icon_point);
            spaViewHolder.imgSpaIconPoint.setTag(position);
            spaViewHolder.imgSpaBinhLuan.setOnClickListener(onClick_item_binhLuan);
            spaViewHolder.imgSpaBinhLuan.setTag(position);
      /*      spaViewHolder.lnSpaPhone.setOnClickListener(onClick_item_phone);
            spaViewHolder.lnSpaPhone.setTag(position);*/
            spaViewHolder.imgSpaAvata.setOnClickListener(onClick_item_imgAvata);
            spaViewHolder.imgSpaAvata.setTag(position);
            ArrayList<String> imgViewPager = new ArrayList<>();
            imgViewPager.addAll(spa.getSpaImage());
            ImageViewpageAdapter adapter = new ImageViewpageAdapter(context, imgViewPager, new ImageViewpagerListener() {
                @Override
                public void onClickImage(int position) {

                }
            });
            spaViewHolder.viewPager.setAdapter(adapter);
            spaViewHolder.indicator.setViewPager(spaViewHolder.viewPager);


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
    private View.OnClickListener onClick_item_imgAvata = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            spaItemListerner.onClickItemAvata(position);
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

    public static Bitmap decodeFromFireBaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    private  void showMenuItem(View view, final int position){
        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_spa_nhantin:
                        Toast.makeText(context, "nhan tin", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_spa_goi_dien:
                        Toast.makeText(context, "goi dien", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_spa_xem_chitiet:
                        spaItemListerner.onClickItemChitiet(position);

                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
