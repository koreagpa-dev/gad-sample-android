package com.gpakorea.gad.sample;

import android.os.Bundle;

import com.gad.cashtalktalk.CashTalkTalk;
import com.gpakorea.gad.sample.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.buttonLaunch.setOnClickListener(v -> {
            // 캐시톡톡 실행 (메인 UI 팝업)
            CashTalkTalk.launch(this);
        });

        mBinding.buttonOn.setOnClickListener(v -> {
            // 톡톡버튼 보이기
            CashTalkTalk.showFloatingView(this);
        });

        mBinding.buttonOff.setOnClickListener(v -> {
            // 톡톡버튼 감추기
            CashTalkTalk.hideFloatingView(this);
        });
    }
}
