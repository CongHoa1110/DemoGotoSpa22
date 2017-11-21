package com.hoathan.hoa.demogotospa.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.util.Util;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ChangerPassword extends AppCompatActivity implements View.OnClickListener {
    private MaterialEditText metForgrotEmail;
    private Button btnResetPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_password);
        initUI();
    }

    private void initUI() {
        metForgrotEmail = (MaterialEditText) findViewById(R.id.met_forgot_Email);
        btnResetPassword = (Button) findViewById(R.id.btn_resetPassword);
        btnResetPassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (checkInputData()){
            changerPassword(metForgrotEmail.getText().toString().trim());
        }

    }

    private void changerPassword(final String email) {
    /* mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 Toast.makeText(ChangerPassword.this, "password sen Email" + " : " + email , Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(ChangerPassword.this, "fail", Toast.LENGTH_SHORT).show();
             }
         }
     });*/
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChangerPassword.this, "password sen Email" + " : " + email , Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ChangerPassword.this, "fail" + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private boolean checkInputData() {

        if (Util.isEmpty(metForgrotEmail)) {
           String email = metForgrotEmail.getText().toString().trim();

            if (!Util.isEmailValid(email)) {
                metForgrotEmail.requestFocus();
                metForgrotEmail.setError(getResources().getString(R.string.email_error));
                return false;
            } else {

                }

            return true;
        } else {
            return false;
        }
    }
}
