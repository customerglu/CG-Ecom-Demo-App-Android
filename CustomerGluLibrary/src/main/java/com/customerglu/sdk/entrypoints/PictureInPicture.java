package com.customerglu.sdk.entrypoints;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.customerglu.sdk.MovieView;
import com.customerglu.sdk.R;

import androidx.annotation.Nullable;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class PictureInPicture extends View implements View.OnTouchListener, View.OnClickListener {
    private final static float CLICK_DRAG_TOLERANCE = 20; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    private Context context;
    private boolean isDraggable = true;
    private float downRawX, downRawY;
    private float dX, dY;
    private MovieView movieView;
    int curntPositionX = 0, curntPositionY = 0;
    RelativeLayout rootContainer;
    RelativeLayout pipControls;
    private MediaSessionCompat mSession = null;
    private String TAG = PictureInPicture.class.getSimpleName();

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.expand_view) {
            Intent intent = new Intent(context,CGPiPFullScreenActivity.class);
            context.startActivity(intent);

        } else if (id == R.id.close_view) {
            rootContainer.setVisibility(GONE);
            if (movieView.isPlaying()) {
                movieView.pause();
            }
        }

    }

    public enum PiPCTAType {
        PIP_CLOSE_CTA,
        PIP_OPEN_CTA
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        int action = motionEvent.getAction();
        if (id == R.id.movie_view) {
            return touchActionHandler(view, motionEvent);
        } else {
            return false;
        }

    }


    private boolean touchActionHandler(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {

            downRawX = motionEvent.getRawX();
            downRawY = motionEvent.getRawY();
            dX = view.getX() - downRawX;
            dY = view.getY() - downRawY;

            return true; // Consumed

        } else if (action == MotionEvent.ACTION_MOVE) {
            curntPositionX = (int) ((int) motionEvent.getRawX() + dX);
            curntPositionY = (int) ((int) motionEvent.getRawY() + dY);
//                        closeButtonLayout.setVisibility(VISIBLE);
//                        closeLayout.setVisibility(VISIBLE);
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();

            View viewParent = (View) view.getParent();
            int parentWidth = viewParent.getWidth();
            int parentHeight = viewParent.getHeight();

            float newX = motionEvent.getRawX() + dX;
            newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
            newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

            float newY = motionEvent.getRawY() + dY;
            newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
            newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

            view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start();

//
            curntPositionX = (int) newX;
            curntPositionY = (int) newY;

            return true; // Consumed

        } else if (action == MotionEvent.ACTION_UP) {
            float upRawX = motionEvent.getRawX();
            float upRawY = motionEvent.getRawY();

            float upDX = upRawX - downRawX;
            float upDY = upRawY - downRawY;

            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) {
                if (pipControls != null) {
                    pipControls.setVisibility(VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pipControls.setVisibility(GONE);
                        }
                    }, 2000);

                }
            }

        } else {
            //   printErrorLogs("CustomerGlu", "");
        }
        return false;
    }


    public PictureInPicture(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initMoviePlayer() {
        movieView.setVideoResourceId(R.raw.demo_video);
        movieView.setMovieListener(new MovieView.MovieListener() {
            @Override
            public void onMovieStarted() {

            }

            @Override
            public void onMovieStopped() {

            }

            @Override
            public void onMovieMinimized() {

            }
        });
        movieView.hideControls();
        initializeMediaSession();
    }


    private void initializeMediaSession() {
        mSession = new MediaSessionCompat(context, TAG);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mSession.setActive(true);
        MediaControllerCompat.setMediaController((Activity) context, mSession.getController());

        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, movieView.getTitle())
                .build();
        mSession.setMetadata(metadata);


        movieView.play();
    }


    public PictureInPicture(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureInPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PictureInPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView() {
        RelativeLayout myLayout = addLayout();
        ((Activity) context).addContentView(myLayout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    private RelativeLayout addLayout() {
        rootContainer = new RelativeLayout(context);

        rootContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        int widthSize = 0;
        int heightSize = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(size);
            widthSize = (int) (size.x * 0.45);
            heightSize = size.y;
        } else {

            widthSize = (int) (display.getWidth() * 0.45);
            heightSize = display.getHeight();
        }

        heightSize = (int) (1.78 * widthSize);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthSize, heightSize);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        layoutParams.setMargins(10, 0, 0, 20);


        movieView = new MovieView(context, null);
        movieView.setId(R.id.movie_view);
        movieView.setBackground(context.getDrawable(R.drawable.movie_view_bg));
        movieView.setClipToOutline(true);

        ImageView expandView = addViewIcon(PiPCTAType.PIP_OPEN_CTA);
        expandView.setClickable(true);
        expandView.setId(R.id.expand_view);
        ImageView closeView = addViewIcon(PiPCTAType.PIP_CLOSE_CTA);
        closeView.setClickable(true);
        closeView.setId(R.id.close_view);
        movieView.setLayoutParams(layoutParams);

        pipControls = new RelativeLayout(context);
        pipControls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pipControls.addView(expandView);
        pipControls.addView(closeView);
        pipControls.setVisibility(GONE);
        movieView.addView(pipControls);
        rootContainer.addView(movieView);
        closeView.setOnClickListener(this);
        expandView.setOnClickListener(this);

        if (isDraggable) {
            movieView.setOnTouchListener(this);
        }
        initMoviePlayer();
        return rootContainer;

    }


    private ImageView addViewIcon(PiPCTAType ctaType) {
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(64, 64);
        layoutParams.setMargins(0, 8, 8, 20);
        switch (ctaType) {
            case PIP_CLOSE_CTA:
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                imageView.setImageResource(R.drawable.ic_close);
                imageView.setLayoutParams(layoutParams);
                break;
            case PIP_OPEN_CTA:
                layoutParams = new RelativeLayout.LayoutParams(96, 96);
                layoutParams.addRule(CENTER_IN_PARENT, RelativeLayout.TRUE);
                imageView.setImageResource(R.drawable.ic_expand);
                imageView.setLayoutParams(layoutParams);
                break;
        }
        return imageView;
    }


}
