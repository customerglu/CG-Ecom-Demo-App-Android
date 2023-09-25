//package com.customerglu.sdk;
//
//import android.app.Activity;
//import android.app.PictureInPictureParams;
//import android.app.PictureInPictureParams.Builder;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.media.MediaMetadataCompat;
//import android.support.v4.media.session.MediaControllerCompat;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
//import android.util.Rational;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ScrollView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//
//import in.gsrathoreniks.pictureinpicture.MovieView;
//
//public class MediaSessionPlaybackActivity extends Activity {
//
//
//    private static String TAG = "MediaSessionPlaybackActivity";
//
//    static long MEDIA_ACTIONS_PLAY_PAUSE =
//            PlaybackStateCompat.ACTION_PLAY |
//                    PlaybackStateCompat.ACTION_PAUSE |
//                    PlaybackStateCompat.ACTION_PLAY_PAUSE;
//
//    static long MEDIA_ACTIONS_ALL =
//            MEDIA_ACTIONS_PLAY_PAUSE |
//                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
//                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
//
//    private MediaSessionCompat mSession;
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private final Builder mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
//
//    /**
//     * This shows the video.
//     */
//    private MovieView mMovieView;
//
//    /**
//     * The bottom half of the screen; hidden on landscape
//     */
//    private ScrollView mScrollView;
//
//
//    //    private val mOnClickListener = View.OnClickListener { view ->
////            when (view.id) {
////        R.id.pip -> minimize()
////    }
////    }
//    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.pip) {
//                minimize();
//            }
//        }
//    };
//
//    /**
//     * Callbacks from the [MovieView] showing the video playback.
//     */
//
//    private MovieView.MovieListener mMovieListener = new MovieView.MovieListener() {
//        @Override
//        public void onMovieStarted() {
//            if (mMovieView != null) {
//                updatePlaybackState(PlaybackStateCompat.STATE_PLAYING,
//                        MEDIA_ACTIONS_ALL,
//                        mMovieView.getCurrentPosition(),
//                        mMovieView.getVideoResourceId());
//
//            }
//        }
//
//        @Override
//        public void onMovieStopped() {
//            if (mMovieView != null) {
//                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED,
//                        MEDIA_ACTIONS_ALL,
//                        mMovieView.getCurrentPosition(),
//                        mMovieView.getVideoResourceId());
//            }
//        }
//
//        @Override
//        public void onMovieMinimized() {
//            if (mMovieView != null) {
//                minimize();
//            }
//        }
//    };
//
////    private val mMovieListener = object :MovieView.MovieListener()
////
////    {
////
////        override fun onMovieStarted() {
////        // We are playing the video now. Update the media session state and the PiP window will
////        // update the actions.
////        mMovieView ?.let {
////            view ->
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PLAYING,
////                            view.getCurrentPosition(),
////                            view.getVideoResourceId()
////                    )
////        }
////    }
////
////        override fun onMovieStopped() {
////        // The video stopped or reached its end. Update the media session state and the PiP window will
////        // update the actions.
////        mMovieView ?.let {
////            view ->
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PAUSED,
////                            view.getCurrentPosition(),
////                            view.getVideoResourceId()
////                    )
////        }
////    }
////
////        override fun onMovieMinimized() {
////        // The MovieView wants us to minimize it. We enter Picture-in-Picture mode now.
////        minimize()
////    }
////
////    }
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.media_session_player);
//        mMovieView = findViewById(R.id.movie);
//        mScrollView = findViewById(R.id.scroll);
//
//        Button switchExampleButton = findViewById(R.id.switch_example);
//        switchExampleButton.setText(getString(R.string.switch_custom));
//        //TODO
//        //switchExampleButton.setOnClickListener(SwitchActivityOnClick());
//        mMovieView.setMovieListener(mMovieListener);
//        findViewById(R.id.pip).setOnClickListener(mOnClickListener);
//        minimize();
//    }
//
//    private void minimize() {
//        // Hide the controls in picture-in-picture mode.
//        mMovieView.hideControls();
//        // Calculate the aspect ratio of the PiP screen.
//        Rational aspectRatio = new Rational(mMovieView.getWidth(), mMovieView.getHeight());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
//        }
//    }
//
//    private void initializeMediaSession() {
//        mSession = new MediaSessionCompat(this, TAG);
//        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//        mSession.setActive(true);
//        MediaControllerCompat.setMediaController(this, mSession.getController());
//        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
//                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, mMovieView.getTitle())
//                .build();
//        mSession.setMetadata(metadata);
//        //TODO
//        MediaSessionCallback mMediaSessionCallback = new MediaSessionCallback(mMovieView);
//        mSession.setCallback(mMediaSessionCallback);
//        int state;
//        if (mMovieView.isPlaying()) {
//            state = PlaybackStateCompat.STATE_PLAYING;
//        } else {
//            state = PlaybackStateCompat.STATE_PAUSED;
//        }
//
//        updatePlaybackState(state,
//                MEDIA_ACTIONS_ALL,
//                mMovieView.getCurrentPosition(),
//                mMovieView.getVideoResourceId());
//
//
//    }
//
//    private void updatePlaybackState(@PlaybackStateCompat.State int state, Long playbackActions, int position, int mediaId) {
//        PlaybackStateCompat builder = new PlaybackStateCompat.Builder()
//                .setActions(playbackActions)
//                .setActiveQueueItemId(mediaId)
//                .setState(state, position, 1.0f).build();
//        mSession.setPlaybackState(builder);
//
//
//    }
//
//
////
////    private fun initializeMediaSession() {
////        mSession = MediaSessionCompat(this, TAG)
////        mSession.setFlags(
////                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
////        )
////        mSession.isActive = true
////        MediaControllerCompat.setMediaController(this, mSession.controller)
////
////        val metadata = MediaMetadataCompat.Builder()
////                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, mMovieView.title)
////                .build()
////        mSession.setMetadata(metadata)
////
////        val mMediaSessionCallback = MediaSessionCallback(mMovieView)
////        mSession.setCallback(mMediaSessionCallback)
////
////        val state = if (mMovieView.isPlaying)
////            PlaybackStateCompat.STATE_PLAYING
////        else
////            PlaybackStateCompat.STATE_PAUSED
////        updatePlaybackState(
////                state,
////                MEDIA_ACTIONS_ALL,
////                mMovieView.getCurrentPosition(),
////                mMovieView.getVideoResourceId()
////        )
////    }
//
//
//    class MediaSessionCallback extends MediaSessionCompat.Callback {
//
//        private int indexInPlaylist = 1;
//        MovieView mMovieView;
//
//        public MediaSessionCallback(MovieView mMovieView) {
//            this.mMovieView = mMovieView;
//        }
//
//        @Override
//        public void onPlay() {
//            super.onPlay();
//            mMovieView.play();
//        }
//
//
//        @Override
//        public void onPause() {
//            super.onPause();
//            mMovieView.pause();
//        }
//
//        @Override
//        public void onSkipToNext() {
//            super.onSkipToNext();
//            mMovieView.startVideo();
//            int PLAYLIST_SIZE = 2;
//            if (indexInPlaylist < PLAYLIST_SIZE) {
//                indexInPlaylist++;
//                if (indexInPlaylist >= PLAYLIST_SIZE) {
//                    updatePlaybackState(
//                            PlaybackStateCompat.STATE_PLAYING,
//                            MEDIA_ACTIONS_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS,
//                            mMovieView.getCurrentPosition(),
//                            mMovieView.getVideoResourceId()
//                    );
//                } else {
//                    updatePlaybackState(
//                            PlaybackStateCompat.STATE_PLAYING,
//                            MEDIA_ACTIONS_ALL,
//                            mMovieView.getCurrentPosition(),
//                            mMovieView.getVideoResourceId()
//                    );
//                }
//
//            }
//
//        }
//
//        @Override
//        public void onSkipToPrevious() {
//            super.onSkipToPrevious();
//            mMovieView.startVideo();
//            if (indexInPlaylist > 0) {
//                indexInPlaylist--;
//                if (indexInPlaylist <= 0) {
//                    updatePlaybackState(
//                            PlaybackStateCompat.STATE_PLAYING,
//                            MEDIA_ACTIONS_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_NEXT,
//                            mMovieView.getCurrentPosition(),
//                            mMovieView.getVideoResourceId()
//                    );
//                } else {
//                    updatePlaybackState(
//                            PlaybackStateCompat.STATE_PLAYING,
//                            MEDIA_ACTIONS_ALL,
//
//                            mMovieView.getCurrentPosition(),
//
//                            mMovieView.getVideoResourceId()
//                    );
//                }
//            }
//        }
//
//    }
////    private inner class MediaSessionCallback(private val movieView: MovieView) :
////            MediaSessionCompat.Callback() {
////        private val PLAYLIST_SIZE = 2
////
////        private var indexInPlaylist: Int = 0
////
////        init {
////            indexInPlaylist = 1
////        }
////
////        override fun onPlay() {
////            super.onPlay()
////            movieView.play()
////        }
////
////        override fun onPause() {
////            super.onPause()
////            movieView.pause()
////        }
////
////        override fun onSkipToNext() {
////            super.onSkipToNext()
////            movieView.startVideo()
////            if (indexInPlaylist < PLAYLIST_SIZE) {
////                indexInPlaylist++
////                if (indexInPlaylist >= PLAYLIST_SIZE) {
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PLAYING,
////                            MEDIA_ACTIONS_PLAY_PAUSE or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS,
////                            movieView.getCurrentPosition(),
////                            movieView.getVideoResourceId()
////                    )
////                } else {
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PLAYING,
////                            MEDIA_ACTIONS_ALL,
////                            movieView.getCurrentPosition(),
////                            movieView.getVideoResourceId()
////                    )
////                }
////            }
////        }
////
////        override fun onSkipToPrevious() {
////            super.onSkipToPrevious()
////            movieView.startVideo()
////            if (indexInPlaylist > 0) {
////                indexInPlaylist--
////                if (indexInPlaylist <= 0) {
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PLAYING,
////                            MEDIA_ACTIONS_PLAY_PAUSE or PlaybackStateCompat.ACTION_SKIP_TO_NEXT,
////                            movieView.getCurrentPosition(),
////                            movieView.getVideoResourceId()
////                    )
////                } else {
////                    updatePlaybackState(
////                            PlaybackStateCompat.STATE_PLAYING,
////                            MEDIA_ACTIONS_ALL,
////                            movieView.getCurrentPosition(),
////                            movieView.getVideoResourceId()
////                    )
////                }
////            }
////        }
////    }
//
//
//}
