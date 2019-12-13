package com.gpakorea.gad.sample;

import android.os.Bundle;
import android.text.TextUtils;

import com.gad.sdk.Gad;
import com.gpakorea.gad.sample.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    
    private static final String MEDIA_KEY = "{INSERT_MEDIA_KEY}";
    private static final String USER_ID = "{INSERT_USER_ID}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.mediaKey.setText(MEDIA_KEY);
        mBinding.userId.setText(USER_ID);

        mBinding.buttonActivity.setOnClickListener(v -> {
            if (checkField()) {
                initializeGad();
                Gad.showAdList(this);
            }
        });

        mBinding.buttonFragment.setOnClickListener(v -> {
            if (checkField()) {
                initializeGad();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, Gad.getAdListFragment(this))
                        .commitAllowingStateLoss();
            }
        });
    }

    private boolean checkField() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.mediaKey.getText())) {
            isValid = false;
            mBinding.mediaKeyLayout.setError("{MEDIA KEY}를 입력해 주세요.");
        } else {
            mBinding.mediaKeyLayout.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(mBinding.userId.getText())) {
            isValid = false;
            mBinding.userIdLayout.setError("{USER ID}를 입력해 주세요.");
        } else {
            mBinding.userIdLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    private void initializeGad() {
        Gad.init(this, mBinding.mediaKey.getText().toString(), mBinding.userId.getText().toString());
//        Gad.setUserInfo("M", 22);
//        Gad.setProgressAnimation(true);
    }
}
