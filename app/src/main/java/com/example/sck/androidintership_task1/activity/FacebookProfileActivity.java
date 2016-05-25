package com.example.sck.androidintership_task1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sck.androidintership_task1.model.FacebookUserModel;
import com.example.sck.androidintership_task1.R;
import com.example.sck.androidintership_task1.utils.SharedPrefUtils;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookProfileActivity extends Activity {

    @BindView (R.id.profile_image) ImageView mProfileImage;
    @BindView (R.id.profile_name) TextView mProfileName;
    @BindView (R.id.profile_email) TextView mProfileEmail;
    @BindView (R.id.logout_btn) TextView mBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile);
        ButterKnife.bind(this);

        FacebookUserModel user = SharedPrefUtils.getCurrentUser(FacebookProfileActivity.this);
        mProfileName.setText(user.getName());
        mProfileEmail.setText(user.getEmail());
        // fetching facebook's profile picture
        Picasso.with(this)
                .load(getString(R.string.facebook_profile_pic_url) + user.getFacebookID() + getString(R.string.facebook_profile_pic_type))
                .into(mProfileImage);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefUtils.clearCurrentUser(FacebookProfileActivity.this);
                // logout from facebook by calling following method
                LoginManager.getInstance().logOut();
                startActivity(new Intent(FacebookProfileActivity.this, FacebookLoginActivity.class));
                finish();
            }
        });
    }
}
