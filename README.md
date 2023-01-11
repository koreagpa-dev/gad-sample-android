# 캐시톡톡 연동하기

- 이 프로젝트는 캐시톡톡을 적용한 샘플앱으로 아래 가이드 한 내용을 포함하고 있습니다.
- GAD 오퍼월 사이트에 앱 등록은 [GAD 미디어 설정하기](https://github.com/koreagpa-dev/gad-sample-android/blob/cashtalktalk/guide_media.md#gad-%EB%AF%B8%EB%94%94%EC%96%B4-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0) 페이지를 참고 바랍니다.
- 캐시톡톡은 별도 서버가 필요치 않으며 관리자가 별도로 세팅해드립니다.
- [캐시톡톡 세팅요청하기](https://open.kakao.com/o/slURBiHc)

## Gradle 설정
- 모듈 수준의 build.gradle 파일에서 아래 코드를 추가합니다.

```groovy
android {
    defaultConfig {
        multiDexEnabled true
    }
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

## TargetSdkVersion 33 (Android 13) 이상인 경우
- 매니페스트 파일에서 Google Play 서비스 일반 권한을 다음과 같이 선언해야 합니다.
```xml
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

## 참고 사이트
- MultiDex : https://developer.android.com/studio/build/multidex.html?hl=ko
- 데이터바인딩 : https://developer.android.com/topic/libraries/data-binding/start.html?hl=ko
- Java 8 : https://developer.android.com/studio/write/java8-support?hl=ko
- AD_ID : https://support.google.com/googleplay/android-developer/answer/6048248?hl=ko

## 캐시톡톡 SDK 사용
#### 프로젝트 설정
- 캐시톡톡은 [JitPack](https://jitpack.io/) 원격 저장소를 가지고 있습니다.
- 프로젝트 수준의 build.gradle 파일에 저장소르 추가합니다.
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- 모듈 수준의 build.gradle 파일에 캐시톡톡 종속성을 추가합니다.
    - {version}란에 아래 버전명(녹색 배경의 흰색 글자)을 기입한다.

[![](https://jitpack.io/v/koreagpa-dev/cashtalktalk.svg)](https://jitpack.io/#koreagpa-dev/cashtalktalk)
```groovy
dependencies {
    implementation 'com.github.koreagpa-dev:cashtalktalk:{version}'
}
```
#### 캐시톡톡 초기화 및 실행
- 캐시톡톡을 사용하기 위해서는 어플리케이션 클래스에서 초기화 함수를 호출해야 합니다.
```java
public class MyApplication extends Application {
    ...
    @Override
    public void onCreate() {
        super.onCreate();
        ...
        CashTalkTalk.initialize(this, "{미디어키}");
    }
    ...
}
```
- 캐시톡톡을 실행하거나 톡톡버튼만 노출시킬 수 있습니다.
```java
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ...
        // 앱 실행시 톡톡버튼 보여주기
        CashTalkTalk.showFloatingView(this);
        ...
        
        /**
         * 테스트 코드
         */
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
```


#### SDK API 21 미만 버전 및 에러 대응
```java
public class MyApplication extends Application {
    
    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
```
```groovy
dependencies {
    implementation 'androidx.multidex:multidex:2.0.1'
    /* okhttp 3.13부터 API 21+ 만 지원 */
    implementation('com.squareup.okhttp3:logging-interceptor:3.12.8') {
        force = true
    }
    implementation("com.squareup.okhttp3:okhttp:3.12.8") {
        force = true
    }
}
```