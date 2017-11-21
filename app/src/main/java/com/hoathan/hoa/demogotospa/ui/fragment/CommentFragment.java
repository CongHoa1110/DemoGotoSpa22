package com.hoathan.hoa.demogotospa.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.ImageViewpageAdapter;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgCommentBack;
    private CircleImageView imgCommentSpa;
    private TextView txvCommentSpaName;
    private TextView txvCommentSpaTime;
    private TextView txvCommentSpaDescription;
    private ViewPager viewPagerCommentSpa;
    private CircleIndicator circleIndicator;
    private ImageView imgCommentSpalike;
    private TextView txvCommentSpapoint;
    private TextView txvCommentSpaLike;
    private ArrayList<String> imgViewPager;
    private ImageViewpageAdapter adapter;

    private static int curentpage = 0;
    private static int numpage = 0;

    @Override
    protected int setLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        setToobal(view);
        setHasOptionsMenu(true);
        spaComment();
    }

    @Override
    protected void initialVariables() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.comment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.comment_baoxau:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setToobal(View view) {
        imgCommentSpa = (CircleImageView) view.findViewById(R.id.img_comment_spa_Avatar);
        txvCommentSpaName = (TextView) view.findViewById(R.id.txv_infomation_spa_name);
        txvCommentSpaTime = (TextView) view.findViewById(R.id.txv_comment_spa_time);
        txvCommentSpaDescription = (TextView) view.findViewById(R.id.txv_comment_spa_description);
        viewPagerCommentSpa = (ViewPager) view.findViewById(R.id.viewpager_comment);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindicator_comment);
        imgCommentSpalike = (ImageView) view.findViewById(R.id.img_comment_spa_icon_point);
        txvCommentSpaLike = (TextView) view.findViewById(R.id.txv_comment_spa_point);
        txvCommentSpaLike = (TextView) view.findViewById(R.id.txv_comment_xemluotthich);
        imgCommentBack = (ImageView) view.findViewById(R.id.img_comment_back);
        imgCommentBack.setOnClickListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_comment);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_comment_back:
                baseActivity.popFragment();
                break;
        }
    }

    private void spaComment() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Spa spa = (Spa) bundle.getSerializable("spa");

            txvCommentSpaName.setText(spa.getSpaName());
            txvCommentSpaTime.setText(spa.getSpaTime());
            txvCommentSpaDescription.setText(spa.getSpaDescription());
            int vitri = spa.getSpaImage().size() - 1;
            Random random = new Random();
            int vt = random.nextInt(vitri);
            Picasso.with(getActivity()).load(spa.getSpaImage().get(vt)).resize(50, 50)
                    .centerCrop().into(imgCommentSpa);

            imgViewPager = new ArrayList<>();
            if (spa.getSpaImage() != null) {
                imgViewPager.addAll(spa.getSpaImage());
                adapter = new ImageViewpageAdapter(getActivity(), imgViewPager, new ImageViewpagerListener() {
                    @Override
                    public void onClickImage(int position) {

                    }
                });

                viewPagerCommentSpa.setAdapter(adapter);
                circleIndicator.setViewPager(viewPagerCommentSpa);
                setViewPager();
            }


        }
    }

    private void setViewPager() {
        viewPagerCommentSpa.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curentpage = 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pagecount = imgViewPager.size();
                    if (curentpage == 0) {
                        viewPagerCommentSpa.setCurrentItem(pagecount - 1, false);
                    } else {
                        if (curentpage == pagecount - 1) {
                            viewPagerCommentSpa.setCurrentItem(0, false);
                        }
                    }
                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (curentpage == numpage) {
                    curentpage = 0;

                }
                viewPagerCommentSpa.setCurrentItem(curentpage++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000);
    }
}
