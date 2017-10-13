package com.hoathan.hoa.demogotospa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;

public class SplashScreenActivity extends BaseActivity {
    private ImageView ivSplash;
    private TextView tvSplash;

    @Override
    protected int setLayout() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {

        initUI();
        showAnim();
        nextAction();
    }

    @Override
    protected void initialVariables() {

    }

    @Override
    protected int getFragmentContainerResID() {
        return 0;
    }
    private void initUI() {
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        tvSplash = (TextView) findViewById(R.id.tv_splash);
    }

    private void showAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        tvSplash.startAnimation(animation);
        ivSplash.startAnimation(animation);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_intent_entervao,R.anim.anim_intent_editra);
                finish();
            }
        }, 4000);
    }
}
