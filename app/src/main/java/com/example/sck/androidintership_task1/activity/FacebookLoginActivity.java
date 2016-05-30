package com.example.sck.androidintership_task1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sck.androidintership_task1.models.FacebookUserModel;
import com.example.sck.androidintership_task1.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class FacebookLoginActivity extends Activity {

    @BindView (R.id.login_btn) TextView mCustomLoginBtn;
    @BindView (R.id.facebook_login_button) LoginButton mLoginButton;
    private static final String ID_FIELD = "mFacebookID";
    private CallbackManager mCallbackManager;
    private String mToken;
    private String mUserId;
    private String mPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallbackManager = CallbackManager.Factory.create();
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
            mToken = loginResult.getAccessToken().getToken();
            mUserId = loginResult.getAccessToken().getUserId();
            mPermissions = loginResult.getRecentlyGrantedPermissions().toString();
            Profile profile = Profile.getCurrentProfile();
            if (profile == null) {
                ProfileTracker tracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        stopTracking();
                        saveProfileToDb(currentProfile);
                    }
                };
                tracker.startTracking();
            } else {
                saveProfileToDb(profile);
            }
            startActivity(new Intent(FacebookLoginActivity.this, FacebookProfileActivity.class));
            finish();
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

    private void saveProfileToDb(Profile profile) {
        Realm realm = Realm.getDefaultInstance();
        if (profile != null) {
            realm.beginTransaction();
            FacebookUserModel user = realm.where(FacebookUserModel.class).equalTo(ID_FIELD, mUserId).findFirst();
            if (user == null) {
                user = new FacebookUserModel();
                user.setFacebookID(mUserId);
                user.setAccessToken(mToken);
                user.setName(profile.getName());
                user.setPermissions(mPermissions);
            }
            realm.commitTransaction();
            realm.close();
        }
    }
}
