package com.hoathan.hoa.demogotospa.data.network;

import android.app.Activity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hoathan.hoa.demogotospa.interfaces.LoginFacebookListener;

import java.util.Arrays;

/**
 * Created by Tungnguyenbk54 on 10/10/2017.
 */

public class FacebookLogin {
    private Activity activity;
    public FacebookLogin(Activity activity) {
        this.activity = activity;
    }
    public void loginFacebook(CallbackManager callbackManager, final Activity activity, final LoginFacebookListener listener){
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                listener.loginFacebookSuccess();
                activity.finish();
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

}
