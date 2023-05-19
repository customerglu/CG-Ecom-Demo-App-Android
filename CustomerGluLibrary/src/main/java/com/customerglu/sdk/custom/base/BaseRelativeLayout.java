package com.customerglu.sdk.custom.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Prefs;
import com.customerglu.sdk.custom.views.ProgressLottieView;

import java.io.ByteArrayInputStream;

public class BaseRelativeLayout extends RelativeLayout {
    private ProgressLottieView progressLottieView;

    public BaseRelativeLayout(Context context) {
        super(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setupProgressView(ProgressLottieView progressLottieView) {
        this.progressLottieView = progressLottieView;
        this.progressLottieView.setupView();
    }

    public void startLottieProgressView() {
        String lottiePath = "";
        if (CustomerGlu.isDarkModeEnabled(CustomerGlu.globalContext)) {
            getLottieFile(CGConstants.EMBED_DARK_LOTTIE_FILE, CGConstants.EMBED_DARK_LOTTIE_FILE_NAME);
        } else {
            getLottieFile(CGConstants.EMBED_LIGHT_LOTTIE_FILE, CGConstants.EMBED_LIGHT_LOTTIE_FILE_NAME);
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
        if (!Prefs.getKey(getContext(), file).isEmpty()) {
            try {
                String filename = Prefs.getKey(getContext(), fileName);
                String lottieFile = Prefs.getKey(getContext(), file);
                byte[] lottieByteArray = lottieFile.getBytes();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(lottieByteArray);
                progressLottieView.setAnimationInputStream(inputStream, filename);
            } catch (Exception e) {
                CustomerGlu.getInstance().sendCrashAnalytics(getContext(), e.toString());
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

}
