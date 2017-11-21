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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.ui.base.BaseFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private EditText edtName;
    private ProgressBar prbLoad;

    private StorageReference mStorageReference;
    private Uri firePage;

    private CircleIndicator circleIndicator;
    private ArrayList<String> imgViewPager;
    private HomeFragment homeFragment;

    private GoogleMap mMap;

    private EditText edtRegisterName, edtRegiterPhone, edtRegisterEmail, edtRegisterdesciption;
    private TextView edtRegisterClose, edtRegisteropen, edtRegisterYeae, edtRegisterAddress;

    private DatabaseReference spaReference;
    private LatLng lng;
    private String spaTime = "";

    private Button btnDemo;


    public RegisterSpaFragment newInstance() {
        RegisterSpaFragment registerSpaFragment = new RegisterSpaFragment();
        return registerSpaFragment;
    }

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
    }

    @Override
    protected void initialVariables() {

    }


    private void addImage() {
        imgViewPager = new ArrayList<>();

    }

    private void iUnit(View view) {
        btnDemo = (Button) view.findViewById(R.id.btn_register_demo);
        btnDemo.setOnClickListener(this);

        imgBack = (ImageView) view.findViewById(R.id.img_toobal_back);
        imgBack.setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prbLoad = (ProgressBar) view.findViewById(R.id.prb_load);
        btnRegister = (Button) view.findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(this);

        edtRegisterdesciption = (EditText) view.findViewById(R.id.edt_redister_desciption);
        edtRegisterName = (EditText) view.findViewById(R.id.edt_register_Name);
        edtRegisterAddress = (TextView) view.findViewById(R.id.edt_register_address);
        edtRegisterAddress.setOnClickListener(this);
        edtRegiterPhone = (EditText) view.findViewById(R.id.edt_register_phone);
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
            case R.id.btn_register_demo:
                baseActivity.setCurrentTab(BaseActivity.TITLE_HOST);
                baseActivity.pushFragment(new DemoFragment(), true);
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
                        askForPermission(android.Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
                        //ActivityCompat.requestPermissions(baseActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                        break;
                    case R.id.menu_chonanhcosan:
                        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE_FODER);
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

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            // imgShwo.setImageBitmap(bitmap)
            firePage = data.getData();
            imgViewPager.add(String.valueOf(firePage));
            circleIndicator.setViewPager(viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(imgViewPager.size() - 1);

        }
        if (requestCode == REQUEST_CODE_FODER && resultCode == RESULT_OK && data != null) {
            firePage = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(firePage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // imgShwo.setImageBitmap(bitmap);
                imgViewPager.add(String.valueOf(firePage));
                circleIndicator.setViewPager(viewPager);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(imgViewPager.size() - 1);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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
                // The user canceled the operation.
            }
        }
        //upLoadFileImage();
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {

            Toast.makeText(getActivity(), "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    private void upLoadFileImage() {
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
                    Toast.makeText(baseActivity, "upload anh", Toast.LENGTH_SHORT).show();
                    Uri dowloadUri = taskSnapshot.getDownloadUrl();
                    imgViewPager.add(String.valueOf(dowloadUri));
                    adapter.notifyDataSetChanged();
                    Log.d("AAA", "onSuccess: " + dowloadUri);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progret = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Toast.makeText(baseActivity, progret + "", Toast.LENGTH_SHORT).show();

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
        String phoneSpa = edtRegiterPhone.getText().toString();
        if (TextUtils.isEmpty(phoneSpa)) {
            edtRegiterPhone.setError("Required.");
            valid = false;
        } else {
            edtRegiterPhone.setError(null);
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
        String phoneSpa = edtRegiterPhone.getText().toString().trim();
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
}