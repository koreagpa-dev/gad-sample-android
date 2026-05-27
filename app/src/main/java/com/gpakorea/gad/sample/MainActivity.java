package com.gpakorea.gad.sample;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;

import com.gad.sdk.Gad;
import com.gpakorea.gad.sample.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class MainActivity extends AppCompatActivity {
    private static final String DEFAULT_MEDIA_KEY = "{INSERT_MEDIA_KEY}";
    private static final String DEFAULT_USER_ID = "{INSERT_USER_ID}";

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.mediaKey.setText(DEFAULT_MEDIA_KEY);
        mBinding.userId.setText(DEFAULT_USER_ID);
        initGad();

        mBinding.buttonActivity.setOnClickListener(v -> {
            if (validateInputs()) {
                initGad();
                Gad.showAdList(this);
            }
        });

        mBinding.buttonFragment.setOnClickListener(v -> {
            if (validateInputs()) {
                initGad();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, Gad.getAdListFragment(this))
                        .commitAllowingStateLoss();
            }
        });
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(getMediaKey())) {
            mBinding.mediaKeyLayout.setError("{MEDIA KEY}를 입력해 주세요.");
            return false;
        }
        mBinding.mediaKeyLayout.setErrorEnabled(false);

        if (TextUtils.isEmpty(getUserId())) {
            mBinding.userIdLayout.setError("{USER ID}를 입력해 주세요.");
            return false;
        }
        mBinding.userIdLayout.setErrorEnabled(false);

        return true;
    }

    private void initGad() {
        Gad.init(this, getMediaKey(), getUserId());
    }

    private String getMediaKey() {
        Editable text = mBinding.mediaKey.getText();
        return text != null ? text.toString() : "";
    }

    private String getUserId() {
        Editable text = mBinding.userId.getText();
        return text != null ? text.toString() : "";
    }
}
