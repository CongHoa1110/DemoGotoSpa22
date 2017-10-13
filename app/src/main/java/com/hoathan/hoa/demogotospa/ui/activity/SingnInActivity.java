package com.hoathan.hoa.demogotospa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.util.Util;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SingnInActivity extends BaseActivity implements View.OnClickListener {

    private MaterialEditText metEmail;
    private MaterialEditText metPassword;
    private Button btnSignIn;

    private FirebaseAuth mAuth;

    private String email,password;

    private ProgressDialog progressDialog;


    @Override
    protected int setLayout() {
        return R.layout.activity_singn_in;
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        initUI();

    }

    @Override
    protected void initialVariables() {
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign In ");
        progressDialog.setMessage("load....");
    }

    @Override
    protected int getFragmentContainerResID() {
        return 0;
    }
    private void initUI(){
        metEmail = (MaterialEditText) findViewById(R.id.met_sign_in_email);
        metPassword = (MaterialEditText) findViewById(R.id.met_sign_in_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_sign_in:
                    progressDialog.show();
                    if (checkInputData()){
                        singIn();
                    }else {
                        progressDialog.dismiss();
                    }

                    break;
            }
    }
    private void singIn(){
        String email = metEmail.getText().toString();
        String password = metPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SingnInActivity.this, "dang ki thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SingnInActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.anim_intent_entervao,R.anim.anim_intent_editra);
                            progressDialog.dismiss();
                            finish();
                        }else {
                            Toast.makeText(SingnInActivity.this, "khong thanh cong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean checkInputData() {
        if (Util.isEmpty(metEmail) && Util.isEmpty(metPassword)) {
            email = metEmail.getText().toString().trim();
            password = metPassword.getText().toString().trim();
            if (!Util.isEmailValid(email)) {
                metEmail.requestFocus();
                metEmail.setError(getResources().getString(R.string.email_error));

                return false;
            } else {
                if (password.length() < 6) {
                    metPassword.requestFocus();
                    metPassword.setError(getResources().getString(R.string.pass_error));

                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
