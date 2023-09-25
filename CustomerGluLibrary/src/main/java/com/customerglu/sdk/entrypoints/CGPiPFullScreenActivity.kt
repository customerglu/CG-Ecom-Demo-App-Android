package com.customerglu.sdk.entrypoints

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.customerglu.sdk.MovieView
import com.customerglu.sdk.MovieView.MovieListener
import com.customerglu.sdk.R

class CGPiPFullScreenActivity : AppCompatActivity() {

    lateinit var pipPrimaryCTA: AppCompatButton
    lateinit var movieView: MovieView
    lateinit var backCTA: AppCompatImageView

    lateinit var mSession: MediaSessionCompat
    private val TAG = CGPiPFullScreenActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cg_pip_full_screen)

        pipPrimaryCTA = findViewById(R.id.learn_more_cta)
        movieView = findViewById(R.id.movie_view_container)
        backCTA = findViewById(R.id.back_cta)

        backCTA.setOnClickListener {
            finish()
        }
        movieView.setVideoResourceId(R.raw.demo_video)


        initMoviePlayer()

    }


    private fun initMoviePlayer() {
        movieView.setVideoResourceId(R.raw.demo_video)
        movieView.setMovieListener(object : MovieListener() {
            override fun onMovieStarted() {}
            override fun onMovieStopped() {}
            override fun onMovieMinimized() {}
        })
        movieView.hideControls()
        initializeMediaSession()
    }


    private fun initializeMediaSession() {
        mSession = MediaSessionCompat(this, TAG)
        mSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mSession.isActive = true
        MediaControllerCompat.setMediaController((this)!!, mSession.getController())
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, movieView.title)
            .build()
        mSession.setMetadata(metadata)
        movieView.play()
    }
}