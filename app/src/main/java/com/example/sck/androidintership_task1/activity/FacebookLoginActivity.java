package com.example.sck.androidintership_task1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sck.androidintership_task1.models.FacebookUserModel;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.utils.SharedPrefUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookLoginActivity extends Activity {

    @BindView (R.id.login_btn) TextView mCustomLoginBtn;
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private FacebookUserModel mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);
        if(SharedPrefUtils.getCurrentUser(FacebookLoginActivity.this) != null){
            startActivity(new Intent(FacebookLoginActivity.this, FacebookProfileActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton)findViewById(R.id.facebook_login_button);
        mLoginButton.setReadPermissions(getString(R.string.facebook_read_permission_public_profile), getString(R.string.facebook_read_permission_email), getString(R.string.facebook_read_permission_user_friends));
        mCustomLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.performClick();
                mLoginButton.setPressed(true);
                mLoginButton.invalidate();
                mLoginButton.registerCallback(mCallbackManager, mCallBack);
                mLoginButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject obj, GraphResponse response) {
                            try {
                                mUser = new FacebookUserModel(
                                        obj.getString(getString(R.string.fb_user_field_name)),
                                        obj.getString(getString(R.string.fb_user_field_email)),
                                        obj.getString(getString(R.string.fb_user_field_id)) );
                                SharedPrefUtils.setCurrentUser(mUser, FacebookLoginActivity.this);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            startActivity(new Intent(FacebookLoginActivity.this, FacebookProfileActivity.class));
                            finish();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString(getString(R.string.facebook_request_parameters_name), getString(R.string.facebook_request_parameters));
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            LoginManager.getInstance().logOut();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(FacebookLoginActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}
