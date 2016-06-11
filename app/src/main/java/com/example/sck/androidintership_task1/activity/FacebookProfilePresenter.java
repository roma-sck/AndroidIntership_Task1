package com.example.sck.androidintership_task1.activity;

import android.content.Context;
import android.content.Intent;

import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.models.FacebookUserModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Collection;

import io.realm.Realm;

public class FacebookProfilePresenter implements FacebookProfileContract.Presenter {
    private FacebookProfileContract.View mView;
    private Context mContext;
    private static final String ID_FIELD = "mFacebookID";
    private CallbackManager mCallbackManager;
    private String mToken;
    private String mUserId;
    private LoginManager mLoginManager;
    private ProfileTracker mProfileTracker;

    public FacebookProfilePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(FacebookProfileContract.View view) {
        mView = view;
    }

    @Override
    public void logInToProfile() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();

        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mUserId = loginResult.getAccessToken().getUserId();
                mToken = loginResult.getAccessToken().getToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            stopTracking();
                            saveProfileToDb(currentProfile);
                            mView.updateViews(currentProfile);
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    saveProfileToDb(profile);
                    mView.updateViews(profile);
                }
            }
            @Override
            public void onCancel() {
                logOutProfile();
            }
            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });

        Collection<String> permissions = Arrays.asList(
                mContext.getString(R.string.facebook_read_permission_public_profile),
                mContext.getString(R.string.facebook_read_permission_email),
                mContext.getString(R.string.facebook_read_permission_user_friends));
        mLoginManager.logInWithReadPermissions(mView.getActivity(), permissions);
    }

    public void saveProfileToDb(Profile profile) {
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

    @Override
    public void logOutProfile() {
        mLoginManager.logOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStopTracking() {
        if (mProfileTracker != null && mProfileTracker.isTracking()){
            mProfileTracker.stopTracking();
        }
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
