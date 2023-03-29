package com.customerglu.sdk.custom.base;

import android.app.Activity;
import android.view.View;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.custom.views.ProgressLottieView;

import java.io.ByteArrayInputStream;

public class BaseActivity extends Activity {

    private ProgressLottieView progressLottieView;

    public void setupProgressView(ProgressLottieView progressLottieView) {
        this.progressLottieView = progressLottieView;
        this.progressLottieView.setupView();
    }

    public void startLottieProgressView() {
        String lottiePath = "";
        if (CustomerGlu.isDarkModeEnabled(getApplicationContext())) {
            getLottieFile(CGConstants.DARK_LOTTIE_FILE, CGConstants.DARK_LOTTIE_FILE_NAME);
        } else {
            getLottieFile(CGConstants.LIGHT_LOTTIE_FILE, CGConstants.LIGHT_LOTTIE_FILE_NAME);
        }

        progressLottieView.animationShouldLoop(true);
        if (progressLottieView.isRunningAnimation()) {
            progressLottieView.resumeAnimation();
        } else {
            progressLottieView.startAnimation();
        }
        progressLottieView.setVisibility(View.VISIBLE);
    }

    private void getLottieFile(String file, String fileName) {
        if (!Prefs.getKey(getApplicationContext(), file).isEmpty()) {
            try {
                String filename = Prefs.getKey(getApplicationContext(), fileName);
                String lottieFile = Prefs.getKey(getApplicationContext(), file);
                byte[] lottieByteArray = lottieFile.getBytes();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(lottieByteArray);
                progressLottieView.setAnimationInputStream(inputStream, filename);
            } catch (Exception e) {
                CustomerGlu.getInstance().sendCrashAnalytics(getApplicationContext(), e.toString());
            }
        } else {
            progressLottieView.showDefaultLoader();
            // progressLottieView.setAnimationRawResource(R.raw.default_loader);
        }
    }

    public void stopLottieProgressView() {
        if (progressLottieView != null) {
            progressLottieView.setVisibility(View.GONE);
            progressLottieView.pauseAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        if (progressLottieView != null) {
            progressLottieView.invalidateLottie();
        }
        super.onDestroy();

    }
}