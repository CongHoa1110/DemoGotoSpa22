package com.hoathan.hoa.demogotospa.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.ImageViewpageAdapter;
import com.hoathan.hoa.demogotospa.interfaces.ToobarShwo;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_OK;
import static com.hoathan.hoa.demogotospa.util.Comon.REQUEST_CODE_CAMERA;
import static com.hoathan.hoa.demogotospa.util.Comon.REQUEST_CODE_FODER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSpaFragment extends BaseFragment implements View.OnClickListener {
    private ViewPager viewPager;
    private ImageViewpageAdapter adapter;
    private FloatingActionButton flbtnAddImage;
    private ImageView imgShwo;
    private ImageButton imgBack;
    private Button btnRegister;



    private CircleIndicator circleIndicator;
    private int[] img;
    private static int curentpage = 0;
    private static int numpage = 0;
    private ToobarShwo toobarShow;



    public static RegisterSpaFragment newInstance() {
        RegisterSpaFragment registerSpaFragment = new RegisterSpaFragment();
        return registerSpaFragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_register_spa;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        addImage();
        imgBack = (ImageButton) view.findViewById(R.id.img_toobal_back);
        imgBack.setOnClickListener(this);
        btnRegister = (Button) view.findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(this);
        imgShwo = (ImageView) view.findViewById(R.id.img_shwo_image);
        flbtnAddImage = (FloatingActionButton) view.findViewById(R.id.fl_btn_add_image);
        flbtnAddImage.setOnClickListener(this);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new ImageViewpageAdapter(getActivity(), img);
        viewPager.setAdapter(adapter);

        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindicator);
        circleIndicator.setViewPager(viewPager);

        setViewPager();


    }

    @Override
    protected void initialVariables() {
        toobarShow = new ToobarShwo() {
            @Override
            public void toobarshwo() {

            }
        };
    }

    private void addImage() {
        img = new int[]{R.drawable.spa_one, R.drawable.spa_three, R.drawable.spa_two};

    }

    private void setViewPager() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curentpage = 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    int pagecount = img.length;
                    if (curentpage == 0){
                        viewPager.setCurrentItem(pagecount - 1,false);
                    } else {
                        if (curentpage == pagecount - 1){
                            viewPager.setCurrentItem(0,false);
                        }
                    }

                }
            }
        });
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
          if (curentpage == numpage){
              curentpage =0;

          }
          viewPager.setCurrentItem(curentpage ++,true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },3000,3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_btn_add_image:
                showPopumenu();
                break;
            case R.id.img_toobal_back:

                baseActivity.popFragment();
                toobarShow.toobarshwo();
                break;
            case R.id.btn_register_register:
                Toast.makeText(baseActivity, "aa", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPopumenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), flbtnAddImage);
        popupMenu.getMenuInflater().inflate(R.menu.menu_hinhanh, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_chupanh:
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                        break;
                    case R.id.menu_chonanhcosan:
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FODER);

                        break;
                }

                return false;
            }

        });
        popupMenu.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(getActivity(), "ban khong cho phep quyen camera", Toast.LENGTH_SHORT).show();
                }

                break;
            case REQUEST_CODE_FODER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1, REQUEST_CODE_FODER);
                } else {
                    Toast.makeText(getActivity(), "khong cho phep truy cap thu muc ", Toast.LENGTH_SHORT).show();
                }

                break;

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgShwo.setImageBitmap(bitmap);

        }
        if (requestCode == REQUEST_CODE_FODER && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgShwo.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
