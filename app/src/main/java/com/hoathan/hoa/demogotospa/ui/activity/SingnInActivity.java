package com.hoathan.hoa.demogotospa.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private Snackbar snackbar;
    private RelativeLayout relativeLayout;

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
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
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
                        signIn();
                    }else {
                        progressDialog.dismiss();
                    }
                    break;
            }
    }
    private void signIn(){
        String email = metEmail.getText().toString();
        String password = metPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SingnInActivity.this, HomeActivity.class));
                                                overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                                                finish();
                                            }else {
                                                snackbar = Snackbar.make(relativeLayout,"error :" + task.getException(),Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    });
                        }else {
                            snackbar = Snackbar.make(relativeLayout,"error :" + task.getException(),Snackbar.LENGTH_LONG);
                            snackbar.show();                        }
                        progressDialog.dismiss();
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
    private void sendEmailVerification() {
        // Disable button

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
                            Toast.makeText(SingnInActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            signIn();
                        } else {
                            Log.e("tag", "sendEmailVerification", task.getException());
                            Toast.makeText(SingnInActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_LONG).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

}
