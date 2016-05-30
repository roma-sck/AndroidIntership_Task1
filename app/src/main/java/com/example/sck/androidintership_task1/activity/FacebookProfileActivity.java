package com.example.sck.androidintership_task1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.androidintership_task1.R;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookProfileActivity extends AppCompatActivity implements FacebookProfileContract.View {
    @BindView (R.id.profile_image) ImageView mProfileImage;
    @BindView (R.id.profile_name) TextView mProfileName;
    @BindView (R.id.logout_btn) TextView mBtnLogout;

    private FacebookProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_profile);
        ButterKnife.bind(this);

        mPresenter = new FacebookProfilePresenter(this, this);
        mPresenter.logInToProfile();
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout from facebook by calling following method
                mPresenter.logOutProfile();
                startActivity(new Intent(FacebookProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    public void updateViews(Profile profile) {
        mProfileName.setText(profile.getName());
        // fetching facebook's profile picture
        Picasso.with(this)
                .load(getString(R.string.facebook_profile_pic_url) + profile.getId() +
                        getString(R.string.facebook_profile_pic_type))
                .into(mProfileImage);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onStopTracking();
    }
}
