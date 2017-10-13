package com.hoathan.hoa.demogotospa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.network.FacebookLogin;
import com.hoathan.hoa.demogotospa.interfaces.LoginFacebookListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.util.Util;
import com.rengwuxian.materialedittext.MaterialEditText;



public class LogInActivity extends BaseActivity implements View.OnClickListener {
    private MaterialEditText metEmail;
    private MaterialEditText metPassword;
    private Button btnLogin;
    private CheckBox cbRemember;
    private ImageButton imgLoginFacebook,imgLoginGoogle;
    private TextView txvForgotPassword,txvSignIn;

    private CallbackManager callbackManager;
    private FacebookLogin facebookLogin;

    private String email;
    private String password;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private static final String NAME_SP = "nho dang nhap";

    @Override
    protected int setLayout() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
       initUI();
    }

    @Override
    protected void initialVariables() {
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("login");
        progressDialog.setMessage("load....");
        setRemember();
    }

    @Override
    protected int getFragmentContainerResID() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                progressDialog.show();
                if (checkInputData()){
                    login();
                }else {
                    progressDialog.dismiss();
                }

                break;
            case R.id.img_login_facebook:

                facebookLogin.loginFacebook(callbackManager, this, new LoginFacebookListener() {
                    @Override
                    public void loginFacebookSuccess() {

                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.anim_intent_entervao,R.anim.anim_intent_editra);
                        finish();
                    }

                    @Override
                    public void loginFacebookFailure(String message) {
                        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.img_login_google:
                break;
            case R.id.txv_forgot_password:
                break;
            case R.id.txv_sign_in:

                startActivity(new Intent(LogInActivity.this, SingnInActivity.class));
                overridePendingTransition(R.anim.anim_intent_entervao,R.anim.anim_intent_editra);
                finish();
                break;
        }
    }



    private void initUI(){
        callbackManager = CallbackManager.Factory.create();
        facebookLogin = new FacebookLogin(this);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        metEmail = (MaterialEditText) findViewById(R.id.met_log_in_email);
        metPassword = (MaterialEditText) findViewById(R.id.met_log_in_password);
        cbRemember = (CheckBox) findViewById(R.id.cb_remember);
        imgLoginFacebook = (ImageButton) findViewById(R.id.img_login_facebook);
        imgLoginFacebook.setOnClickListener(this);
        imgLoginGoogle = (ImageButton) findViewById(R.id.img_login_google);
        imgLoginGoogle.setOnClickListener(this);
        txvForgotPassword = (TextView) findViewById(R.id.txv_forgot_password);
        txvForgotPassword.setOnClickListener(this);
        txvSignIn = (TextView) findViewById(R.id.txv_sign_in);
        txvSignIn.setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void login(){
        email = metEmail.getText().toString();
      password = metPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            remember();
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.anim_intent_entervao,R.anim.anim_intent_editra);
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                           checkInputData();
                            Toast.makeText(LogInActivity.this, "loi khi dang nhap", Toast.LENGTH_SHORT).show();

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
    private void remember(){
        email = metEmail.getText().toString().trim();
        password = metPassword.getText().toString().trim();
            if (cbRemember.isChecked()){

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", email);
                editor.putString("password", password);
                editor.putBoolean("check", true);
                editor.commit();
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("name");
                editor.remove("password");
                editor.remove("check");
                editor.commit();
            }
        }
        private void setRemember(){
            sharedPreferences = getSharedPreferences(NAME_SP,MODE_APPEND);
            metEmail.setText(sharedPreferences.getString("name", ""));
            metPassword.setText(sharedPreferences.getString("password", ""));
            cbRemember.setChecked(sharedPreferences.getBoolean("check", false));
        }

}
