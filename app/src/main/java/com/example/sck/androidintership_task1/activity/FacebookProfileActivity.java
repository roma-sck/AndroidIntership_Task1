package com.example.sck.androidintership_task1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class FacebookProfileActivity extends AppCompatActivity {
    @BindView (R.id.profile_image) ImageView mProfileImage;
    @BindView (R.id.profile_name) TextView mProfileName;
    @BindView (R.id.logout_btn) TextView mBtnLogout;
    private static final String ID_FIELD = "mFacebookID";
    private CallbackManager mCallbackManager;
    private String mToken;
    private String mUserId;
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_profile);
        ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();

        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mUserId = loginResult.getAccessToken().getUserId();
                mToken = loginResult.getAccessToken().getToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    ProfileTracker tracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            stopTracking();
                            saveProfileToDb(currentProfile);
                            updateViews(currentProfile);
                        }
                    };
                    tracker.startTracking();
                } else {
                    saveProfileToDb(profile);
                    updateViews(profile);
                }
            }
            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
            }
            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });

        Collection<String> permissions = Arrays.asList(
                getString(R.string.facebook_read_permission_public_profile),
                getString(R.string.facebook_read_permission_email),
                getString(R.string.facebook_read_permission_user_friends));
        mLoginManager.logInWithReadPermissions(this, permissions);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout from facebook by calling following method
                mLoginManager.logOut();
                startActivity(new Intent(FacebookProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void saveProfileToDb(Profile profile) {
        Realm realm = Realm.getDefaultInstance();
        if (profile != null) {
            realm.beginTransaction();
            FacebookUserModel user = realm.where(FacebookUserModel.class).equalTo(ID_FIELD, mUserId).findFirst();
            if (user == null) {
                user = new FacebookUserModel();
                user.setFacebookID(mUserId);
            }
            user.setAccessToken(mToken);
            user.setName(profile.getName());
            realm.commitTransaction();
        }
        realm.close();
    }

    private void updateViews(Profile profile) {
        mProfileName.setText(profile.getName());
        // fetching facebook's profile picture
        Picasso.with(this)
                .load(getString(R.string.facebook_profile_pic_url) + profile.getId() +
                        getString(R.string.facebook_profile_pic_type))
                .into(mProfileImage);
    }
}
