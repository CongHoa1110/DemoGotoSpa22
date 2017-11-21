package com.hoathan.hoa.demogotospa.ui.fragment.news;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.SpaAdapterRecyclerView;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.OnLoadMoreListener;
import com.hoathan.hoa.demogotospa.listener.SpaItemListerner;
import com.hoathan.hoa.demogotospa.ui.fragment.HomeFragment;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.CommentFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.DetailSpaFragment;
import com.hoathan.hoa.demogotospa.ui.fragment.RegisterSpaFragment;

import java.util.ArrayList;

import static com.hoathan.hoa.demogotospa.util.Comon.SPA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    private RecyclerView rcvSpa;
    private SpaAdapterRecyclerView adapter;
    private ArrayList<Spa> arrayListSpaNews;
    private DatabaseReference spaReference;
    private HomeFragment homeFragment;
    private int index = 10;
    private String spaId = "";

    private Toolbar toolbar;
    private TextView txvToobarTitle;
    private ProgressBar prbLoad;
    private SwipeRefreshLayout srlLayout;

    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        spaReference = firebaseDatabase.getReference(SPA_KEY);
        rcvSpa = (RecyclerView) view.findViewById(R.id.lv_news);
        arrayListSpaNews = new ArrayList<>();
        getListNewsSpa(getActivity(), index, "-KwnWCPcIsg8BLbczd66");
        rcvSpa.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SpaAdapterRecyclerView(rcvSpa, getActivity(), arrayListSpaNews, new SpaItemListerner() {
            @Override
            public void onClickItemChitiet(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("spa",arrayListSpaNews.get(position));
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new DetailSpaFragment(),true,bundle);
            }

            @Override
            public void onClickItemlike(int position) {
                Toast.makeText(baseActivity, "" + arrayListSpaNews.get(position).getSpaName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickItemVBinhLuan(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("spa",arrayListSpaNews.get(position));
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new CommentFragment()
                        ,true,bundle);
            }

            @Override
            public void onClickItemPhone(int position) {
            dialogPhone(position);
            }
        });
        rcvSpa.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add them item null vao de hien thi loading
                arrayListSpaNews.add(null);
                adapter.notifyItemInserted(arrayListSpaNews.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        //Remove loading item
                        int sizeOfArray = arrayListSpaNews.size();
                        if (sizeOfArray > 0) {
                            spaId = arrayListSpaNews.get(sizeOfArray - 2).getSpaID(); //vi phan tu cuoi cung la null
                        }
                        arrayListSpaNews.remove(arrayListSpaNews.size() - 1);
                        adapter.notifyItemRemoved(arrayListSpaNews.size());
                        getListNewsSpa(getActivity(), index, spaId);
                    }
                }, 3000);
            }
        });

        setHasOptionsMenu(true);
        setToobal(view);
        scrollToTop();
    }

    @Override
    protected void initialVariables() {
    }

    private int timeToLoad = 0;

    private void getListNewsSpa(final Activity activity, final int size, final String spaID) {
        timeToLoad++;
        spaReference.orderByChild("spaID").startAt(spaID).limitToFirst(size).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("tag", dataSnapshot.getChildrenCount() + "");
                if (dataSnapshot != null) {
                    Log.d("tag", dataSnapshot.getChildrenCount() + "");
                    ArrayList<Spa> newSpa = new ArrayList<Spa>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Spa spa = postSnapshot.getValue(Spa.class);
                        newSpa.add(spa);
                    }
                    if (timeToLoad > 1 && newSpa.size() > 0) {
                        newSpa.remove(0);
                    }
                    arrayListSpaNews.addAll(newSpa);
                    adapter.notifyDataSetChanged();
                    if (dataSnapshot.getChildrenCount() == 10) {
                        adapter.setLoaded(false);
                    } else {
                        adapter.setLoaded(true);
                    }

                } else {
                    View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                    Snackbar.make(rootView, "het du lieu ", Snackbar.LENGTH_LONG).show();
                }
                prbLoad.setVisibility(View.INVISIBLE);
                srlLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(baseActivity, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.news, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_news).getActionView();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new RegisterSpaFragment(), true);
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Thêm Spa Mới", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setToobal(View view) {
        srlLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_layout);
        prbLoad = (ProgressBar) view.findViewById(R.id.prb_load);
        prbLoad.setVisibility(View.VISIBLE);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }
    private void scrollToTop() {
        srlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListNewsSpa(getActivity(),10," ");
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
        intent.setData(Uri.parse("tel:" + arrayListSpaNews.get(position).getSpaPhone()));
        startActivity(intent);
    }
}
