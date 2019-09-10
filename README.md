# GAD SDK 연동하기

- 이 프로젝트는 GAD SDK를 적용한 샘플앱으로 아래 가이드 한 내용을 포함하고 있습니다.
- GAD 오퍼월 사이트에 앱 등록은 [GAD 미디어 설정하기](https://github.com/koreagpa-dev/gad-sample-android/blob/master/guide_media.md#gad-%EB%AF%B8%EB%94%94%EC%96%B4-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0) 페이지를 참고 바랍니다.
- API 문서는 [GAD API DOCUMENT](https://github.com/koreagpa-dev/gad-sample-android/blob/master/api-doc.md#gad-api-document), [CPA 연동](https://github.com/koreagpa-dev/gad-sample-android/blob/master/guide_cpa.md#gad-cpa-%EC%97%B0%EB%8F%99-%EA%B0%80%EC%9D%B4%EB%93%9C) 페이지를 참고 바랍니다.

## AndroidX 마이그레이션
- 기존의 SupportLibrary는 더 이상 업데이트가 이루어지지 않으며 AndroidX 라는 이름으로 대체되었습니다.
- 주된 변경 사항중 하나는 기존에 v4, v7, v13 등으로 나눠져 있던 부분을 androidx라는 문자열로 시작하는 네임스페이스로 통일 됐습니다.
- Android 스튜디오 3.2 이상에서는 메뉴 바에서 리펙터링 > AndroidX로 이전을 선택하여 AndroidX를 사용하도록 기존 프로젝트를 빠르게 이전할 수 있습니다.
- 참고 사이트
    - AndroidX 개요 : https://developer.android.com/jetpack/androidx?hl=ko
    - AndroidX로 이전 : https://developer.android.com/jetpack/androidx/migrate#migrate

## Gradle 설정
- 모듈 수준의 build.gradle 파일에서 아래 코드를 추가합니다.

```groovy
android {
    defaultConfig {
        multiDexEnabled true
    }
    dataBinding {
        enabled = true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
- 참고 사이트
    - MultiDex : https://developer.android.com/studio/build/multidex.html?hl=ko
    - 데이터바인딩 : https://developer.android.com/topic/libraries/data-binding/start.html?hl=ko
    - Java 8 : https://developer.android.com/studio/write/java8-support?hl=ko

## GAD SDK 라이브러리 사용
#### 프로젝트 설정
- GAD SDK는 [JitPack](https://jitpack.io/) 원격 저장소를 가지고 있습니다.
- 프로젝트 수준의 build.gradle 파일에 저장소르 추가합니다.
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- 모듈 수준의 build.gradle 파일에 GAD 종속성을 추가합니다.
    - {version}란에 아래 버전명(녹색 배경의 흰색 글자)을 기입한다.

[![](https://jitpack.io/v/koreagpa-dev/gad.svg)](https://jitpack.io/#koreagpa-dev/gad)
```groovy
dependencies {
    implementation 'com.github.koreagpa-dev:gad:{version}'
}
```
#### GAD 초기화 및 실행
- GAD SDK를 사용하기 위해서는 가장 먼저 초기화 함수를 호출해야 합니다.
- GAD는 새창(액티비티) 형태와 인레이아웃(프레그먼트) 형식을 모두 지원합니다.
- 샘플앱은 MEDIA KEY와 USER ID 변경 테스트를 위한 코드를 포함하고 있으며 GAD SDK 연동을 위한 코드는 아래와 같습니다.
```java
public class MainActivity extends AppCompatActivity {
    
    // GAD 초기화 함수. 가장 먼저 호출 필요
    // (로그인 후 1회만) 앱 로그인 완료 후 미디어키와 유저ID(유저를 식별할 수 있는 유니크한 값)로 초기화 한다.
    private void initializeGad(String mediaKey, String userId) {
        Gad.init(this, mediaKey, userId);
        // (Optional) 고객의 성별, 나이를 알고 있다면 아래 설정을 통해 타겟팅 된 광고를 받아 볼 수 있다.
        Gad.setUserInfo("M", 22);
    }

    // 새창(액티비티)으로 GAD를 보여준다.
    private void startGadActivity() {
        Gad.showAdList(this);
    }

    // GAD Fragment를 보여준다.
    private void showGadFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, Gad.getAdListFragment(this))
                .commitAllowingStateLoss();
    }
}
```

- 광고 참여를 완료하면 [미디어에 등록된 URL로 포스트백](https://github.com/koreagpa-dev/gad-sample-android/blob/master/guide_media.md#%EB%AF%B8%EB%94%94%EC%96%B4-%EC%97%B0%EB%8F%99-%EC%A0%95%EB%B3%B4-%EC%9E%85%EB%A0%A5%ED%95%98%EA%B8%B0)을 전송합니다.
    - 링크 : [포스트백 API DOCUMENT](https://github.com/koreagpa-dev/gad-sample-android/blob/master/api-doc.md#%ED%8F%AC%EC%8A%A4%ED%8A%B8%EB%B0%B1)
    
#### API 방식 사용시
- GAD 광고 참여시 내부 로직을 사용하므로 SDK 연동은 동일하게 필요합니다.
- [광고목록](https://github.com/koreagpa-dev/gad-sample-android/blob/master/api-doc.md#%EA%B4%91%EA%B3%A0-%EB%AA%A9%EB%A1%9D)을 요청해서 리스트를 구성합니다.
- 리스트 아이템이 클릭 됐을때 GAD 광고인지 체크해서 분기해 줍니다.
```java
public class MainActivity extends AppCompatActivity {
    
    // GAD 초기화 함수. 가장 먼저 호출 필요
    // (로그인 후 1회만) 앱 로그인 완료 후 미디어키와 유저ID(유저를 식별할 수 있는 유니크한 값)로 초기화 한다.
    private void initializeGad(String mediaKey, String userId) {
        Gad.init(this, mediaKey, userId);
        // 고객의 성별, 나이를 알고 있다면 아래 설정을 통해 타겟팅 된 광고를 받아 볼 수 있다.
        Gad.setUserInfo("M", 22);
    }

    // 리스트 아이템 클릭 시 GAD 광고인 경우 SDK를 통해 광고 상세 화면으로 이동한다.
    private void onItemClick(...) {
        if (클릭된 광고가 GAD 인 경우) {
            Gad.goDetail(MainActivity.this, "{광고키}");
        } else {
            ...
        }
    }
}
```
- 상세화면으로 이동후의 과정은 SDK 방식과 동일합니다.