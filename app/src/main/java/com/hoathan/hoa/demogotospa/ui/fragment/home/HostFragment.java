package com.hoathan.hoa.demogotospa.ui.fragment.home;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.SpaAdapterRecyclerView;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.SpaItemListerner;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.CommentFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.DetailSpaFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hoathan.hoa.demogotospa.ui.base.BaseActivity.TITLE_HOST;
import static com.hoathan.hoa.demogotospa.util.Comon.SPA_KEY;


public class HostFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rcvSpa;
    private SpaAdapterRecyclerView adapter;
    private ArrayList<Spa> arrayListSpaHots;
    private DatabaseReference spaReference;
    private FirebaseAuth mAuth;
    private int index = 5;
    private String lastEmail = "";
    private int timeToLoad = 0;
    private int spaView;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ProgressBar prbLoad;
    private CircleImageView imgImage;
    private TextView txvNameSpa;

    private SwipeRefreshLayout srlLayout;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {

        setHasOptionsMenu(true);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        spaReference = firebaseDatabase.getReference(SPA_KEY);
        rcvSpa = (RecyclerView) view.findViewById(R.id.lv_host_recy);
        arrayListSpaHots = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rcvSpa.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SpaAdapterRecyclerView(rcvSpa, getActivity(), arrayListSpaHots, new SpaItemListerner() {
            @Override
            public void onClickItemChitiet(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("spa", arrayListSpaHots.get(position));
                baseActivity.setCurrentTab(TITLE_HOST);
                baseActivity.pushFragment(new DetailSpaFragment()
                        , true,bundle);
                Log.d("sisemain ", "sise main:  " + " =" + baseActivity.typeStackMap.get(TITLE_HOST).size());

            }

            @Override
            public void onClickItemlike(final int position) {
                if (arrayListSpaHots.get(position).getspaLike() == false) {

                    Query pendingTasks = spaReference.orderByChild("spaLike").equalTo(arrayListSpaHots.get(position).getspaLike());
                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            DataSnapshot nodeDataSnapshot = tasksSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            Log.d("key", "key: " + key);
                            spaReference.child(key)
                                    .child("spaLike")
                                    .setValue(true);
                            arrayListSpaHots.get(position).setSpaLike(true);
                            spaReference.child(key)
                                    .child("spaViews")
                                    .setValue(arrayListSpaHots.get(position).getSpaViews() + 1);
                            arrayListSpaHots.get(position).setSpaViews(arrayListSpaHots.get(position).getSpaViews() + 1);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(baseActivity, "like", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Query pendingTasks = spaReference.orderByChild("spaLike").equalTo(arrayListSpaHots.get(position).getspaLike());
                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            DataSnapshot nodeDataSnapshot = tasksSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is
                            Log.d("key", "key: " + key);
                            spaReference.child(key)
                                    .child("spaLike")
                                    .setValue(false);
                            arrayListSpaHots.get(position).setSpaLike(false);
                            spaReference.child(key)
                                    .child("spaViews")
                                    .setValue(arrayListSpaHots.get(position).getSpaViews() - 1);
                            arrayListSpaHots.get(position).setSpaViews(arrayListSpaHots.get(position).getSpaViews() - 1);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(baseActivity, "Un like", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onClickItemVBinhLuan(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("spa", arrayListSpaHots.get(position));
                baseActivity.setCurrentTab(TITLE_HOST);
                baseActivity.pushFragment(new CommentFragment()
                        , true,bundle);

            }

            @Override
            public void onClickItemPhone(int position) {
                dialogPhone(position);
            }
        });
        rcvSpa.setAdapter(adapter);
        getHotSpas(15);
/*
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");
                arrayListSpaHots.add(null);
                adapter.notifyItemInserted(arrayListSpaHots.size() - 1);

                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        //Remove loading item
                        //Remove loading item
                        int sizeOfArray = arrayListSpaHots.size();
                        if (sizeOfArray > 0) {
                            spaView = arrayListSpaHots.get(sizeOfArray - 2).getSpaViews(); //vi phan tu cuoi cung la null
                        }
                        Log.d("spaViews", spaView + "");
                        arrayListSpaHots.remove(arrayListSpaHots.size() - 1);
                        adapter.notifyItemRemoved(arrayListSpaHots.size());

                        getHotSpas(spaView);
                     *//*   adapter.notifyDataSetChanged();
                        adapter.setLoaded(false);*//*
                    }
                }, 3000);
            }
        });*/

        setToobal(view);
        initUI(view);
        scrollToTop();
    }

    @Override
    protected void initialVariables() {

    }

    private void getHotSpas(int spaView) {

        boolean networkOK = this.checkInternetConnection(getActivity());
        if (!networkOK) {
            return;
        }
        timeToLoad++;
        spaReference.orderByChild("spaViews")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("loadmore", "onDataChange: " + timeToLoad);
                        ArrayList<Spa> spas = new ArrayList<Spa>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Spa spa = postSnapshot.getValue(Spa.class);
                            spas.add(spa);
                        }
                        //reorder spa list
                        for (int i = spas.size() - 1; i >= 0; i--) {
                            arrayListSpaHots.add(spas.get(i));
                        }
                        if (dataSnapshot.getChildrenCount() == 10) {
                            adapter.setLoaded(false);
                        } else {
                            adapter.setLoaded(true);
                        }
                        adapter.notifyDataSetChanged();
                        prbLoad.setVisibility(View.INVISIBLE);
                        srlLayout.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.host, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.search(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean checkInternetConnection(Activity activity) {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar.make(rootView, "khong co ket noi ", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(getActivity(), "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
      /*  View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, "ket noi", Snackbar.LENGTH_LONG).show();*/
        return true;
    }

    private void setToobal(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Host");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_changer_password) {
            // changerPassword();
            Toast.makeText(getActivity(), "nav_changer_password", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_gallery) {
            Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getActivity(), "222", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(getActivity(), "333", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(getActivity(), "444", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            // Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();

            if (user.getDisplayName() != null) {
                txvNameSpa.setText(user.getDisplayName().toString());
            }
            if (user.getPhotoUrl() != null) {
                Picasso.with(getActivity())
                        .load(user.getPhotoUrl())
                        .resize(50, 50)
                        .centerCrop()
                        .into(imgImage);
            }
        }
    }

    private void initUI(View view) {
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        txvNameSpa = (TextView) header.findViewById(R.id.txv_name_spa);
        imgImage = (CircleImageView) header.findViewById(R.id.img_image);
        srlLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_layout);
        prbLoad = (ProgressBar) view.findViewById(R.id.prb_load);
        prbLoad.setVisibility(View.VISIBLE);

        getName();
    }

    private void scrollToTop() {
        srlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getHotSpas(10);
            }
        });
    }

    private void dialogPhone(final int position) {

        AlertDialog.Builder alertDialaog = new AlertDialog.Builder(getActivity());
        alertDialaog.setCancelable(false);
        alertDialaog.setTitle("Thong Bao");
        alertDialaog.setIcon(R.mipmap.ic_launcher);
        alertDialaog.setMessage("Bạn có muốn gọi đến Spa này không ? ");
        alertDialaog.setPositiveButton("Gọi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                call(position);
            }
        });
        alertDialaog.setNegativeButton("khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialaog.show();
    }

    private void call(int position) {
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrayListSpaHots.get(position).getSpaPhone()));
        startActivity(intent);
    }
}
