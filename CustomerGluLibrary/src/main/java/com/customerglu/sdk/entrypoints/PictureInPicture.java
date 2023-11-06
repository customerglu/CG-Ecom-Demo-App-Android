package com.customerglu.sdk.entrypoints;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
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

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.customerglu.sdk.Interface.PIPVideoDownloadedListener;
import com.customerglu.sdk.R;
import com.customerglu.sdk.Utils.CGConstants;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.pip.MovieView;
import com.customerglu.sdk.pip.PIPHelper;

import java.util.ArrayList;
import java.util.List;


public class PictureInPicture extends View implements View.OnClickListener, View.OnTouchListener, PIPVideoDownloadedListener {
    private final static float CLICK_DRAG_TOLERANCE = 20; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
    private final Context context;
    private float downRawX, downRawY;
    private float dX, dY;
    private MovieView movieView;
    int curntPositionX = 0, curntPositionY = 0;
    RelativeLayout rootContainer;
    RelativeLayout pipControls;
    ImageView audioView;
    private final String TAG = "CustomerGlu";
    BroadcastReceiver broadcastReceiver;
    PIPHelper pipHelper;
    String screenName = "";
    List<String> allowedActivityList;
    List<String> disAllowedActivityList;
    String entrypointId = "";

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.expand_view) {
            //   rootContainer.setVisibility(GONE);
            pipHelper.sendPIPAnalyticsEvents(CGConstants.ENTRY_POINT_CLICK, screenName, false);
            openFullScreenView();

        } else if (id == R.id.close_view) {
            pipHelper.isPIPDismissed();
            pipHelper.sendPIPAnalyticsEvents(CGConstants.ENTRY_POINT_DISMISS, screenName, false);
            removePIPView();
            if (movieView.isPlaying()) {
                movieView.pause();
            }
        } else if (id == R.id.audio_view) {
            if (movieView.getAudioEnabled()) {
                movieView.setAudioEnabled(false);
                movieView.muteVolume();
                audioView.setImageResource(R.drawable.ic_mute);
            } else {
                movieView.setAudioEnabled(true);
                movieView.unMuteVolume();
                audioView.setImageResource(R.drawable.ic_unmute);
            }
        }

    }

    private void openFullScreenView() {
        if (movieView.isPlaying()) {
            movieView.pause();
        }
        int videoCurPosition = movieView.getCurrentPosition();
        Intent intent = new Intent(context, CGPiPFullScreenActivity.class);
        intent.putExtra("currentPosition", videoCurPosition);
        context.startActivity(intent);
    }

    @Override
    public void onVideoDownloaded() {
        //  if (!pipHelper.isLoaded) {
        // pipHelper.initPIP();
        getEntryPointData();
        //}
    }


    public enum PiPCTAType {
        PIP_CLOSE_CTA,
        PIP_OPEN_CTA,
        PIP_MUTE_CTA
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        int action = motionEvent.getAction();
        if (id == R.id.pip_card_view) {
            return touchActionHandler(view, motionEvent);
        } else {
            return false;
        }

    }


    private boolean touchActionHandler(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (pipHelper.isDraggable()) {
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

            }
        }
        if (action == MotionEvent.ACTION_UP) {
            float upRawX = motionEvent.getRawX();
            float upRawY = motionEvent.getRawY();

            float upDX = upRawX - downRawX;
            float upDY = upRawY - downRawY;

            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) {
//                if (pipControls != null) {
//                    pipControls.setVisibility(VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            pipControls.setVisibility(GONE);
//                        }
//                    }, 2000);
//
//                }
            }

        } else {
            //   printErrorLogs("CustomerGlu", "");
        }
        return false;
    }


    public PictureInPicture(Context context, String screenName) {
        super(context);
        this.context = context;
        this.screenName = screenName;
        initView();
    }

    public PictureInPicture(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public PictureInPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public PictureInPicture(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    public void hidePIPView() {
        if (rootContainer != null) {
            movieView.pause();
            rootContainer.setVisibility(GONE);
        }

    }

    public void showPIPView() {
        if (rootContainer != null) {
            movieView.play();
            rootContainer.setVisibility(VISIBLE);
        }
    }

    public void removePIPView() {

        @SuppressLint("ResourceType")
        View myView = ((Activity) context).findViewById(R.id.pip_small_window);
        if (myView != null) {
            //   printDebugLogs("Remove" + "" + CustomerGlu.entryPointId.get(i));
            ViewGroup parent = (ViewGroup) myView.getParent();
            if (parent != null) {
                parent.removeView(myView);

            }
        }


    }

    private void initMoviePlayer() {
        if (pipHelper.muteOnDefaultPIP) {
            movieView.muteVolume();
        }
        if (pipHelper.loopVideoPIP) {
            movieView.enableAutoLoopingVideo();
        } else {
            movieView.disableAutoLoopingVideo();
        }
        //  movieView.setVideoResourceId(R.raw.demo_video);
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
        initializeMediaSession(0);
    }


    public void initializeMediaSession(int currentVideoPosition) {
        MediaSessionCompat mSession = new MediaSessionCompat(context, TAG);
        mSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mSession.setActive(true);
        MediaControllerCompat.setMediaController((Activity) context, mSession.getController());

        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, " ")
                .build();
        mSession.setMetadata(metadata);
        if (movieView != null) {
            if (currentVideoPosition > 0) {
                movieView.setCurrentPosition(currentVideoPosition);
                movieView.startVideo();
            } else {
                movieView.play();
            }
        }

    }


    public void initView() {
        allowedActivityList = new ArrayList<>();
        disAllowedActivityList = new ArrayList<>();
        pipHelper = PIPHelper.getInstance();
        pipHelper.setPIPDownloadListener(this);
        this.setId(R.id.main_pip_view);

        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equalsIgnoreCase("CUSTOMERGLU_ENTRY_POINT_DATA")) {

                        pipHelper.initPIP();
                    }
                }
            };
        }
        if (context != null) {
            context.registerReceiver(broadcastReceiver, new IntentFilter("CUSTOMERGLU_ENTRY_POINT_DATA"));

            pipHelper.initPIP();
            if (pipHelper.pipEntryPointsData != null) {
                getEntryPointData();
            }

        }


    }


    private void getEntryPointData() {
        if (pipHelper.getAllowedActivityListList() != null) {
            allowedActivityList = pipHelper.getAllowedActivityListList();
        }
        if (pipHelper.getDisAllowedActivityListList() != null) {
            disAllowedActivityList = pipHelper.getDisAllowedActivityListList();
        }
        if (allowedActivityList.size() > 0 && disAllowedActivityList.size() > 0) {
            if (!disAllowedActivityList.contains(screenName)) {
                renderPIPView();
            }
        } else if (allowedActivityList.size() > 0) {
            if (allowedActivityList.contains(screenName)) {
                renderPIPView();
            }
        } else if (disAllowedActivityList.size() > 0) {
            if (!disAllowedActivityList.contains(screenName)) {
                renderPIPView();
            }
        }

    }

    private void renderPIPView() {
        if (pipHelper.checkShowOnDailyRefresh()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    RelativeLayout myLayout = addLayout();
                    RelativeLayout.LayoutParams layoutParams;
                    if (pipHelper.isDraggable()) {
                        layoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.FILL_PARENT,
                                RelativeLayout.LayoutParams.FILL_PARENT
                        );
                    } else {
                        layoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                    }
                    if (pipHelper.viewPosition.equalsIgnoreCase("BOTTOM-LEFT")) {


                        layoutParams.setMargins(pipHelper.horizontalPadding, 0, 0, pipHelper.verticalPadding);
                    } else if (pipHelper.viewPosition.equalsIgnoreCase("TOP-LEFT")) {

                        layoutParams.setMargins(pipHelper.horizontalPadding, pipHelper.verticalPadding, 0, 0);

                    } else if (pipHelper.viewPosition.equalsIgnoreCase("TOP-RIGHT")) {

                        layoutParams.setMargins(0, pipHelper.verticalPadding, pipHelper.horizontalPadding, 0);


                    } else {

                        layoutParams.setMargins(0, 0, pipHelper.horizontalPadding, pipHelper.verticalPadding);

                    }
                    pipHelper.sendPIPAnalyticsEvents(CGConstants.ENTRY_POINT_LOAD, screenName, false);
                    ((Activity) context).addContentView(myLayout, layoutParams);

                }
            };
            new Handler().postDelayed(runnable, pipHelper.getPIPDelay());

        }
    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    private RelativeLayout addLayout() {
        CardView cardView = new CardView(context);
        cardView.setRadius(Comman.convertDpToPixel(16f, context));
        cardView.setElevation(12);
        cardView.setClipToPadding(true);
        rootContainer = new RelativeLayout(context);
        rootContainer.setBackgroundColor(Color.TRANSPARENT);
        // rootContainer.setVisibility(INVISIBLE);
        rootContainer.setId(R.id.pip_small_window);
        if (pipHelper.isDraggable()) {
            rootContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            rootContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        int widthSize = 0;
        int heightSize = 0;

        windowManager.getDefaultDisplay().getSize(size);
        widthSize = (int) (size.x * 0.35);

        heightSize = (int) (1.78 * widthSize);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthSize, heightSize);
        if (pipHelper.viewPosition.equalsIgnoreCase("BOTTOM-LEFT")) {

            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.setMargins(pipHelper.horizontalPadding, 0, 0, pipHelper.verticalPadding);
        } else if (pipHelper.viewPosition.equalsIgnoreCase("TOP-LEFT")) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.setMargins(pipHelper.horizontalPadding, pipHelper.verticalPadding, 0, 0);

        } else if (pipHelper.viewPosition.equalsIgnoreCase("TOP-RIGHT")) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutParams.setMargins(0, pipHelper.verticalPadding, pipHelper.horizontalPadding, 0);


        } else {
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutParams.setMargins(0, 0, pipHelper.horizontalPadding, pipHelper.verticalPadding);

        }

        movieView = new MovieView(context, null, 0, false);
        movieView.setBackground(context.getDrawable(R.drawable.movie_view_bg));
        movieView.setClipToOutline(true);
        movieView.setId(R.id.movie_view);
//        movieView.setPIPDownloadListener(new MovieView.PIPVideoDownloadedListener() {
//            @Override
//            public void onVideoDownloaded() {
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        rootContainer.setVisibility(VISIBLE);
//                    }
//                });
//            }
//        });
        ImageView expandView = addViewIcon(PiPCTAType.PIP_OPEN_CTA);
        expandView.setClickable(true);
        expandView.setId(R.id.expand_view);
        ImageView closeView = addViewIcon(PiPCTAType.PIP_CLOSE_CTA);
        closeView.setClickable(true);
        closeView.setId(R.id.close_view);
        audioView = addViewIcon(PiPCTAType.PIP_MUTE_CTA);
        audioView.setClickable(true);
        audioView.setId(R.id.audio_view);
//        movieView.setLayoutParams(layoutParams);

        pipControls = new RelativeLayout(context);
        pipControls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        pipControls.addView(expandView);
        pipControls.addView(closeView);
        pipControls.addView(audioView);
        pipControls.setVisibility(VISIBLE);
        movieView.addView(pipControls);
        cardView.addView(movieView);
        cardView.setLayoutParams(layoutParams);
        cardView.setId(R.id.pip_card_view);
        rootContainer.addView(cardView);
        cardView.setClickable(false);
        cardView.setFocusable(false);
        closeView.setOnClickListener(this);
        expandView.setOnClickListener(this);
        audioView.setOnClickListener(this);
        if (!pipHelper.isDraggable()) {
            movieView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    pipHelper.sendPIPAnalyticsEvents(CGConstants.ENTRY_POINT_CLICK, screenName, false);
                    openFullScreenView();
                }
            });
            //  movieView.setMovieViewInteractionListener(this::);
        } else {
            cardView.setOnTouchListener(this);
        }
        initMoviePlayer();
        return rootContainer;

    }


    private ImageView addViewIcon(PiPCTAType ctaType) {
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(64, 64);
        switch (ctaType) {
            case PIP_CLOSE_CTA:
                layoutParams.setMargins(16, 16, 0, 0);
                layoutParams.addRule(ALIGN_PARENT_LEFT);
                imageView.setImageResource(R.drawable.ic_close);
                imageView.setLayoutParams(layoutParams);
                break;
            case PIP_OPEN_CTA:
                layoutParams.setMargins(0, 16, 16, 0);
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                imageView.setImageResource(R.drawable.ic_expand);
                imageView.setLayoutParams(layoutParams);
                break;
            case PIP_MUTE_CTA:
                layoutParams.setMargins(0, 0, 16, 16);
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                if (pipHelper.muteOnDefaultPIP) {
                    imageView.setImageResource(R.drawable.ic_mute);
                } else {
                    imageView.setImageResource(R.drawable.ic_unmute);
                }
                imageView.setLayoutParams(layoutParams);
                break;

        }

        return imageView;
    }


}
