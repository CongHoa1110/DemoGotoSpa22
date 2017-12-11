package com.hoathan.hoa.demogotospa.ui.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.adapter.ImageViewpageAdapter;
import com.hoathan.hoa.demogotospa.data.model.Spa;
import com.hoathan.hoa.demogotospa.listener.ImageViewpagerListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.hoathan.hoa.demogotospa.util.Comon.PLACE_AUTOCOMPLETE_REQUEST_CODE;
import static com.hoathan.hoa.demogotospa.util.Comon.REQUEST_CODE_CAMERA;
import static com.hoathan.hoa.demogotospa.util.Comon.REQUEST_CODE_FODER;
import static com.hoathan.hoa.demogotospa.util.Comon.REQUEST_PERMISSION_CAMERA;
import static com.hoathan.hoa.demogotospa.util.Comon.SPA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSpaFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {
    private ViewPager viewPager;
    private ImageViewpageAdapter adapter;
    private FloatingActionButton flbtnAddImage;
    private ImageView imgBack;
    private Button btnRegister;
    private ProgressBar prbLoad;

    private StorageReference mStorageReference;
    private Uri firePage;

    private CircleIndicator circleIndicator;
    private ArrayList<String> imgViewPager;

    private GoogleMap mMap;

    private EditText edtRegisterName, edtRegisterPhone, edtRegisterEmail, edtRegisterdesciption;
    private TextView edtRegisterClose, edtRegisteropen, edtRegisterYeae, edtRegisterAddress;

    private DatabaseReference spaReference;
    private LatLng lng;
    private String spaTime = "";

    private ArrayList<Bitmap> bitmap = null;

    @Override
    protected int setLayout() {
        return R.layout.fragment_register_spa;
    }

    @Override
    protected void initialViews(View view, Bundle bundle) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        spaReference = firebaseDatabase.getReference(SPA_KEY);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        addImage();
        iUnit(view);
        addTime();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    @Override
    protected void initialVariables() {

    }


    private void addImage() {
        imgViewPager = new ArrayList<>();

    }

    private void iUnit(View view) {

        imgBack = (ImageView) view.findViewById(R.id.img_toobal_back);
        imgBack.setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_register);
        mapFragment.getMapAsync(this);
        prbLoad = (ProgressBar) view.findViewById(R.id.prb_load);
        btnRegister = (Button) view.findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(this);

        edtRegisterdesciption = (EditText) view.findViewById(R.id.edt_redister_desciption);
        edtRegisterName = (EditText) view.findViewById(R.id.edt_register_Name);
        edtRegisterAddress = (TextView) view.findViewById(R.id.edt_register_address);
        edtRegisterAddress.setOnClickListener(this);
        edtRegisterPhone = (EditText) view.findViewById(R.id.edt_register_phone);
        edtRegisterEmail = (EditText) view.findViewById(R.id.edt_register_Email);
        edtRegisterYeae = (TextView) view.findViewById(R.id.sp_register_Founded_year);

        edtRegisterClose = (TextView) view.findViewById(R.id.sp_register_close);
        edtRegisteropen = (TextView) view.findViewById(R.id.sp_register_opent);
        // imgShwo = (ImageView) view.findViewById(R.id.img_shwo_image);
        flbtnAddImage = (FloatingActionButton) view.findViewById(R.id.fl_btn_add_image);
        flbtnAddImage.setOnClickListener(this);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindicator);
        adapter = new ImageViewpageAdapter(getActivity(), imgViewPager, new ImageViewpagerListener() {
            @Override
            public void onClickImage(final int position) {
                if (imgViewPager.size() > 1) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Delete");
                    dialog.setMessage("ban co that su muon xoa anh nay ?");
                    dialog.setPositiveButton("co", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imgViewPager.remove(position);
                            circleIndicator.setViewPager(viewPager);
                            adapter.notifyDataSetChanged();
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(0);

                        }
                    });
                    dialog.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Delete");
                    dialog.setMessage("ban co that su muon xoa anh nay ?");
                    dialog.setPositiveButton("co", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(baseActivity, "ban khong the xoa anh nay", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialog.show();

                }


            }
        });
        circleIndicator.setViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_btn_add_image:
                showPopumenu();
                break;
            case R.id.img_toobal_back:
                baseActivity.popFragment();
                break;
            case R.id.btn_register_register:
                if (validateForm()) {
                    prbLoad.setVisibility(View.VISIBLE);
                    putDataSpa();
                }
                break;
            case R.id.edt_register_address:
                placeAutoComplete();
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
                        askForPermission(android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_CAMERA);
                        //ActivityCompat.requestPermissions(baseActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                        break;
                    case R.id.menu_chonanhcosan:
                        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, REQUEST_CODE_FODER);
                        break;
                }

                return false;
            }

        });
        popupMenu.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    } else {
                        Toast.makeText(getActivity(), "ban khong cho phep quyen camera", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REQUEST_CODE_FODER:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        getActivity().startActivityForResult(intent1, REQUEST_CODE_FODER);
                    } else {
                        Toast.makeText(getActivity(), "khong cho phep truy cap thu muc ", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }

        } else {
            Toast.makeText(getActivity(), "Can't get media!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        firePage = data.getData();
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {

            Bitmap bitmap = getBitmapFromUri(getContext(), firePage);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            String imageEncoded = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
            imgViewPager.add(imageEncoded);
            circleIndicator.setViewPager(viewPager);
            adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(imgViewPager.size() - 1);
            //upLoadFileImage();
        }
        if (requestCode == REQUEST_CODE_FODER && resultCode == RESULT_OK && data != null) {
            //upLoadFileImage();

            Bitmap bitmap = getBitmapFromUri(getContext(), firePage);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            String imageEncoded = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
            imgViewPager.add(imageEncoded);
            circleIndicator.setViewPager(viewPager);
            adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(imgViewPager.size() - 1);

        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                edtRegisterAddress.setText(place.getAddress());
                lng = place.getLatLng();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 16));
                mMap.addMarker(new MarkerOptions().position(lng)
                        .title(place.getName() + "")
                        .snippet(place.getAddress() + "" +
                                ""))
                ;
                Log.i("success", "Place: " + place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i("fail", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

            }
            if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
                mMap.setMyLocationEnabled(true);
            }
        }



    }

    private void askForPermission(String permission1, String permission2, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission1) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), permission2) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(permission1) || shouldShowRequestPermissionRationale(permission1)) {

                //This is called if user has denied the permission1 before
                //In this case I am just asking the permission1 again
                requestPermissions(new String[]{permission1, permission2}, requestCode);

            } else {
                requestPermissions(new String[]{permission1, permission2}, requestCode);
            }
        } else {

            Toast.makeText(getActivity(), "" + permission1 + " is already granted.", Toast.LENGTH_SHORT).show();
            //TODO

            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    break;
                case REQUEST_CODE_FODER:
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1, REQUEST_CODE_FODER);
                    break;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //  TODO: Prompt with explanation!

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void upLoadFileImage() {
        prbLoad.setVisibility(View.VISIBLE);
        if (firePage != null) {
            StorageReference riversRef = mStorageReference.child("images/" + firePage.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(firePage);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(baseActivity, exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    imgViewPager.add(String.valueOf(dowloadUri));
                    circleIndicator.setViewPager(viewPager);
                    adapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(imgViewPager.size() - 1);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            prbLoad.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    Log.d("AAA", "onSuccess: " + dowloadUri);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    /*double progret = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Toast.makeText(baseActivity, progret + "", Toast.LENGTH_SHORT).show();*/

                }
            })
            ;
        } else {
            Toast.makeText(baseActivity, "khong luu dduoc anh", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean validateForm() {
        boolean valid = true;

        String name = edtRegisterName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            edtRegisterName.setError("Required.");
            // edtRegisterName.setHighlightColor(Color.RED);
            valid = false;
        } else {
            edtRegisterName.setError(null);
        }

        String openSpa = edtRegisteropen.getText().toString();
        if (TextUtils.isEmpty(openSpa)) {
            edtRegisteropen.setError("Required.");
            valid = false;
        } else {
            edtRegisteropen.setError(null);
        }
        String closeSpa = edtRegisterClose.getText().toString();
        if (TextUtils.isEmpty(closeSpa)) {
            edtRegisterClose.setError("Required.");
            valid = false;
        } else {
            edtRegisterClose.setError(null);
        }
        String addressSpa = edtRegisterAddress.getText().toString();
        if (TextUtils.isEmpty(addressSpa)) {
            edtRegisterAddress.setError("Required.");
            valid = false;
        } else {
            edtRegisterAddress.setError(null);
        }
        String desciptionSpa = edtRegisterdesciption.getText().toString();
        if (TextUtils.isEmpty(desciptionSpa)) {
            edtRegisterdesciption.setError("Required.");
            valid = false;
        } else {
            edtRegisterdesciption.setError(null);
        }
        String phoneSpa = edtRegisterPhone.getText().toString();
        if (TextUtils.isEmpty(phoneSpa)) {
            edtRegisterPhone.setError("Required.");
            valid = false;
        } else {
            edtRegisterPhone.setError(null);
        }
        String emailSpa = edtRegisterEmail.getText().toString();
        if (TextUtils.isEmpty(emailSpa)) {
            edtRegisterEmail.setError("Required.");
            valid = false;
        } else {
            edtRegisterEmail.setError(null);
        }


        return valid;
    }

    private void time(final TextView edt) {
        final Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(calendar.HOUR_OF_DAY);
        int phut = calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                edt.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, gio, phut, false);
        timePickerDialog.show();
    }

    private void addTime() {
        edtRegisteropen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time(edtRegisteropen);
            }
        });
        edtRegisterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time(edtRegisterClose);
            }
        });
        edtRegisterYeae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year();
            }
        });
    }

    private void year() {
        final Calendar calendar = Calendar.getInstance();
        int nam = calendar.get(calendar.YEAR);
        final int thang = calendar.get(calendar.MONTH);
        int ngay = calendar.get(calendar.DATE);
        int thu = calendar.get(calendar.SHORT);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                edtRegisterYeae.setText(dinhDangNgay.format(calendar.getTime()));

            }
        }, nam, thang, ngay);
        datePickerDialog.show();

    }

    private void placeAutoComplete() {
        boolean networkOK = this.checkInternetConnection(viewPager);
        if (!networkOK) {
            prbLoad.setVisibility(View.INVISIBLE);
            return;
        }
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void putDataSpa() {
        date();
        String nameSpa = edtRegisterName.getText().toString().trim();
        String yearSpa = edtRegisterYeae.getText().toString().trim();
        String openSpa = edtRegisteropen.getText().toString().trim();
        String closeSpa = edtRegisterClose.getText().toString().trim();
        String addressSpa = edtRegisterAddress.getText().toString().trim();
        String phoneSpa = edtRegisterPhone.getText().toString().trim();
        String emailSpa = edtRegisterEmail.getText().toString().trim();
        String desciptionSpa = edtRegisterdesciption.getText().toString().trim();
        String key = spaReference.push().getKey();
        String spaLocation = lng.latitude + "," + lng.longitude;
        Random random = new Random();
        Spa spa = new Spa(key, random.nextInt(100), imgViewPager, nameSpa, addressSpa,
                spaLocation, desciptionSpa, yearSpa, openSpa, closeSpa, phoneSpa, emailSpa, spaTime, false);
        spaReference.child(key).setValue(spa, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    prbLoad.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "luu loi :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    baseActivity.popFragment();
                    prbLoad.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    private boolean checkInternetConnection(View view) {
        // Lấy ra bộ quản lý kết nối.
        ConnectivityManager connManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Thông tin mạng đang kích hoạt.
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Snackbar.make(view, "Khong co ket noi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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

        return true;
    }

    private void date() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat destFormat = new SimpleDateFormat(" HH:mm_dd/MM/yyyy.");
        Date date = null;
        try {
            date = destFormat.parse(destFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(date);
        spaTime = destFormat.format(calendar.getTime()) + " " + goal;
        Toast.makeText(baseActivity, destFormat.format(calendar.getTime()) + " " + goal, Toast.LENGTH_SHORT).show();
    }

    public Bitmap getBitmapFromUri(Context contextAdapter, Uri uriFile) {
        Bitmap bitmap = null;
        try {
            bitmap = decodeUri(contextAdapter, uriFile);

        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(contextAdapter.getResources(),
                    R.mipmap.ic_launcher);
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap decodeUri(Context context, Uri selectedImage)
            throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o2);

    }
}