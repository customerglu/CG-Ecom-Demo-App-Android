package com.customerglu.sdk.entrypoints

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.customerglu.sdk.CustomerGlu
import com.customerglu.sdk.CustomerGlu.*
import com.customerglu.sdk.Modal.NudgeConfiguration
import com.customerglu.sdk.R
import com.customerglu.sdk.Utils.CGConstants
import com.customerglu.sdk.Utils.Comman
import com.customerglu.sdk.pip.MovieView
import com.customerglu.sdk.pip.MovieView.MovieListener
import com.customerglu.sdk.pip.PIPHelper
import org.json.JSONObject

class CGPiPFullScreenActivity : AppCompatActivity() {

    lateinit var pipPrimaryCTA: AppCompatButton
    lateinit var movieView: MovieView
    lateinit var backCTA: AppCompatImageView
    lateinit var collapseCTA: AppCompatImageView
    lateinit var learnMoreCTA: AppCompatButton
    lateinit var soundBtn: ImageView

    lateinit var mSession: MediaSessionCompat
    private val TAG = CGPiPFullScreenActivity::class.java.simpleName
    private var curPosition = 0
    private var isAudioEnable = true
    private lateinit var pipHelper: PIPHelper

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cg_pip_full_screen)

        pipPrimaryCTA = findViewById(R.id.learn_more_cta)
        movieView = findViewById(R.id.movie_view_container)
        learnMoreCTA = findViewById(R.id.learn_more_cta)
        pipHelper = PIPHelper.getInstance()

        backCTA = findViewById(R.id.back_cta)
        collapseCTA = findViewById(R.id.collapse_cta)
        soundBtn = findViewById(R.id.sound_btn)
        if (pipHelper.muteOnDefaultExpanded) {
            soundBtn.setImageResource(R.drawable.ic_mute)
            movieView.muteVolume()
            movieView.audioEnabled = false
            isAudioEnable = false
        } else {
            soundBtn.setImageResource(R.drawable.ic_unmute)
            movieView.unMuteVolume()
            movieView.audioEnabled = true
            isAudioEnable = true
        }
        learnMoreCTA.setOnClickListener {

            if (pipHelper.ctaAction != null) {
                when (pipHelper.ctaAction.type) {
                    CGConstants.OPEN_DEEPLINK -> if (pipHelper.ctaAction
                            .url != null && pipHelper.ctaAction.url.isNotEmpty()
                    ) {
                        try {
                            val dataObject = JSONObject()
                            dataObject.put(
                                "deepLink",
                                pipHelper.ctaAction.getUrl()
                            )
                            val intent = Intent("CUSTOMERGLU_DEEPLINK_EVENT")
                            intent.putExtra("data", dataObject.toString())
                            sendBroadcast(intent)
                            if (pipHelper.getCTAAction().isHandledBySDK != null && pipHelper.getCTAAction().isHandledBySDK
                            ) {
                                val uri = Uri.parse(
                                    pipHelper.getCTAAction().url
                                )
                                val actionIntent = Intent(Intent.ACTION_VIEW)
                                actionIntent.data = uri
                                startActivity(intent)
                            }
                        } catch (e: Exception) {
                            Comman.printErrorLogs("" + e)
                        }
                    }

                    CGConstants.OPEN_WEBLINK -> {
                        val nudgeConfiguration = NudgeConfiguration()
                        nudgeConfiguration.isHyperlink = true
                        if (pipHelper.getCTAAction()
                                .url != null && pipHelper.getCTAAction().url.isNotEmpty()
                        ) {
                            CustomerGlu.getInstance().displayCGNudge(
                                applicationContext,
                                pipHelper.getCTAAction().url,
                                "",
                                nudgeConfiguration
                            )
                        }
                    }

                    CGConstants.OPEN_WALLET -> {

                        CustomerGlu.getInstance()
                            .openWallet(applicationContext)
                    }

                    else -> {
                        CustomerGlu.getInstance().openWallet(applicationContext)
                    }
//
                }
            }
            Handler().postDelayed({
                pipHelper.isPIPDismissed()

                //   rootContainer.setVisibility(GONE);

                pipHelper.sendPIPAnalyticsEvents(
                    CGConstants.PIP_ENTRY_POINT_CTA_CLICK,
                    currentScreenName, true
                )

                if (pictureInPicture != null) {
                    pictureInPicture.removePIPView()
                    pictureInPicture = null
                }
                finish()
            }, 700)

        }

        soundBtn.setOnClickListener {
            if (!isAudioEnable) {
                soundBtn.setImageResource(R.drawable.ic_unmute)
                movieView.unMuteVolume()
                movieView.audioEnabled = true
                isAudioEnable = true
            } else {
                soundBtn.setImageResource(R.drawable.ic_mute)
                movieView.muteVolume()
                movieView.audioEnabled = false
                isAudioEnable = false

            }
        }
        backCTA.setOnClickListener {
            pipHelper.isPIPDismissed()
            pipHelper.sendPIPAnalyticsEvents(
                CGConstants.ENTRY_POINT_DISMISS,
                currentScreenName,
                true
            )

            if (pictureInPicture != null) {
                pictureInPicture.removePIPView()
                pictureInPicture = null
            }
            finish()
        }

        collapseCTA.setOnClickListener {
            finish()
        }

        // movieView.setVideoResourceId(R.raw.demo_video)

        curPosition = intent.getIntExtra("currentPosition", 0)
        initMoviePlayer()
        pipHelper.sendPIPAnalyticsEvents(CGConstants.ENTRY_POINT_LOAD, currentScreenName, true)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        currentVideoPosition = movieView.getCurrentPosition()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initMoviePlayer() {
        //   movieView.setVideoResourceId(R.raw.demo_video)
        movieView.setMovieListener(object : MovieListener() {
            override fun onMovieStarted() {}
            override fun onMovieStopped() {}
            override fun onMovieMinimized() {}
        })
        movieView.hideControls()

        if (pipHelper.loopVideoExpanded) {
            movieView.enableAutoLoopingVideo()
        } else {
            movieView.disableAutoLoopingVideo()
        }
        movieView.setCurrentPosition(curPosition)
        initializeMediaSession()
    }


    private fun initializeMediaSession() {
        mSession = MediaSessionCompat(this, TAG)
        mSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mSession.isActive = true
        MediaControllerCompat.setMediaController((this), mSession.getController())
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, " ")
            .build()
        mSession.setMetadata(metadata)
        movieView.play()

    }
}