package com.hoathan.hoa.demogotospa.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hoathan.hoa.demogotospa.R;
import com.hoathan.hoa.demogotospa.data.network.FacebookLogin;
import com.hoathan.hoa.demogotospa.listener.LoginFacebookListener;
import com.hoathan.hoa.demogotospa.ui.base.BaseActivity;
import com.hoathan.hoa.demogotospa.util.Util;
import com.rengwuxian.materialedittext.MaterialEditText;


public class LogInActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private MaterialEditText metEmail;
    private MaterialEditText metPassword;
    private Button btnLogin;
    private CheckBox cbRemember;
    private ImageView imgLoginFacebook, imgLoginGoogle;
    private TextView txvForgotPassword, txvSignIn;

    private CallbackManager callbackManager;
    private FacebookLogin facebookLogin;
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 1;

    private String email;
    private String password;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private static final String
            NAME_SP = "nho dang nhap";


    @Override
    protected int setLayout() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        initUI();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void initialVariables() {
        mAuth = FirebaseAuth.getInstance();
        facebookLogin = new FacebookLogin(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("login");
        progressDialog.setMessage("load....");
        setRemember();
      /*  if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
            finish();
        }
*/
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
                if (checkInputData()) {
                    login();
                } else {
                    progressDialog.dismiss();
                }

                break;
            case R.id.img_login_facebook:

                facebookLogin.loginFacebook(callbackManager, mAuth, this, new LoginFacebookListener() {
                    @Override
                    public void loginFacebookSuccess() {

                        startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                        overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                        finish();
                    }

                    @Override
                    public void loginFacebookFailure(String message) {
                        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.img_login_google:
                signIn();
                break;
            case R.id.txv_forgot_password:
                startActivity(new Intent(LogInActivity.this, ChangerPassword.class));
                overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                finish();
                break;
            case R.id.txv_sign_in:
                startActivity(new Intent(LogInActivity.this, SingnInActivity.class));
                overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                finish();
                break;
        }
    }

    private void initUI() {
        callbackManager = CallbackManager.Factory.create();
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        metEmail = (MaterialEditText) findViewById(R.id.met_log_in_email);
        metPassword = (MaterialEditText) findViewById(R.id.met_log_in_password);
        cbRemember = (CheckBox) findViewById(R.id.cb_remember);
        imgLoginFacebook = (ImageView) findViewById(R.id.img_login_facebook);
        imgLoginFacebook.setOnClickListener(this);
        imgLoginGoogle = (ImageView) findViewById(R.id.img_login_google);
        imgLoginGoogle.setOnClickListener(this);
        txvForgotPassword = (TextView) findViewById(R.id.txv_forgot_password);
        SpannableString content = new SpannableString("Forgot password ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txvForgotPassword.setText(content);
        txvForgotPassword.setOnClickListener(this);
        txvSignIn = (TextView) findViewById(R.id.txv_sign_in);
        SpannableString contentt = new SpannableString("sign in ?");
        content.setSpan(new UnderlineSpan(), 0, contentt.length(), 0);
        txvSignIn.setText(contentt);
        txvSignIn.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(this, "Google Sign In failed, update UI appropriately", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

    private void login() {
        email = metEmail.getText().toString();
        password = metPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            remember();
                            startActivity(new Intent(LogInActivity.this, HomeActivity
                                    .class));
                            overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                            finish();
                        } else {
                            checkInputData();
                            Toast.makeText(LogInActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            Log.d("dang ", "onComplete: " + task.getException().toString());

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

    private void remember() {
        email = metEmail.getText().toString().trim();
        password = metPassword.getText().toString().trim();
        if (cbRemember.isChecked()) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", email);
            editor.putString("password", password);
            editor.putBoolean("check", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("name");
            editor.remove("password");
            editor.remove("check");
            editor.commit();
        }
    }

    @SuppressLint("WrongConstant")
    private void setRemember() {
        sharedPreferences = getSharedPreferences(NAME_SP, MODE_APPEND);
        metEmail.setText(sharedPreferences.getString("name", ""));
        metPassword.setText(sharedPreferences.getString("password", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("check", true));
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressDialog.show();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "google Failed", Toast.LENGTH_SHORT).show();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("tag", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                            overridePendingTransition(R.anim.anim_intent_entervao, R.anim.anim_intent_editra);
                            finish();
                            progressDialog.dismiss();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed." + task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }
}
