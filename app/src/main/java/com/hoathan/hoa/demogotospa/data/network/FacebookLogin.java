package com.hoathan.hoa.demogotospa.data.network;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.hoathan.hoa.demogotospa.listener.LoginFacebookListener;

import java.util.Arrays;

/**
 * Created by Tungnguyenbk54 on 10/10/2017.
 */

public class FacebookLogin {
    private Activity activity;
    public FacebookLogin(Activity activity) {
        this.activity = activity;
    }
    public void loginFacebook(CallbackManager callbackManager, final FirebaseAuth mAuth, final Activity activity, final LoginFacebookListener listener){
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(mAuth, loginResult.getAccessToken(), activity);
                listener.loginFacebookSuccess();
            }

            @Override

            public void onCancel() {
                Toast.makeText(activity, "Login Cancel", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                listener.loginFacebookFailure(error.toString());
            }
        });
    }
    private void handleFacebookAccessToken(final FirebaseAuth mAuth, AccessToken token, final Activity activity) {
        Log.d("tag", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity, "loi facebook filebase ", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }

}
