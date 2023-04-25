package com.customerglu.sdk.custom.views;

import static com.customerglu.sdk.Utils.Comman.printDebugLogs;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.R;

import java.io.InputStream;


public class ProgressLottieView extends RelativeLayout {

    private LottieAnimationView animationView;
    private ProgressBar progressBar;
    private int progressBarColor;

    public ProgressLottieView(Context context) {
        super(context);
        setupView();
    }

    public ProgressLottieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    public ProgressLottieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    public ProgressLottieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public void setupView() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.layout_lottie_progressbar_view, this, true);
        animationView = layout.findViewById(R.id.lottie_progress_view);
        animationView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        progressBar = findViewById(R.id.progress_bar);
        try {
            progressBarColor = Color.parseColor(CustomerGlu.configure_loader_color);
            // color is a valid color
        } catch (IllegalArgumentException ex) {
            progressBarColor = Color.parseColor("#65DCAB");
        }
        progressBar.getIndeterminateDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.MULTIPLY);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                printDebugLogs("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                printDebugLogs("onAnimationEnd");

            }

            @Override
            public void onAnimationCancel(Animator animator) {
                printDebugLogs("onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                printDebugLogs("onAnimationRepeat");

            }
        });
    }

    public void setAnimationPath(String animationPath) {
        animationView.setAnimationFromUrl(animationPath);
    }

    public void setAnimationRawResource(int rawResource) {
        animationView.setAnimation(rawResource);
    }

    public void showDefaultLoader() {
        progressBar.setVisibility(VISIBLE);
        // animationView.setAnimation();
    }

    public void setAnimationInputStream(InputStream inputStream, String fileKey) {
        animationView.setVisibility(VISIBLE);
        animationView.setAnimation(inputStream, fileKey);
    }

    public Boolean isRunningAnimation() {
        return animationView.isAnimating();
    }

    public void startAnimation() {
        animationView.playAnimation();
    }

    public void pauseAnimation() {
        animationView.pauseAnimation();
    }

    public void resumeAnimation() {
        animationView.resumeAnimation();
    }

    public void cancelAnimation() {
        animationView.cancelAnimation();
    }

    public void animationShouldLoop(boolean shouldLoop) {
        animationView.setRepeatCount(shouldLoop ? LottieDrawable.INFINITE : 1);
    }


    public void invalidateLottie() {
        cancelAnimation();
        animationView.invalidate();
    }


}
