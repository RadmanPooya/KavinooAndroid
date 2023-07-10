package com.kavinoo.kavinoo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.kavinoo.kavinoo.R;

import org.greenrobot.eventbus.EventBus;

public class FullScreenTizerActivity extends AppCompatActivity {

    PlayerView playerView;
    ProgressBar progressBar;
    CardView fullScreenCardView;
    SimpleExoPlayer simpleExoPlayer;

    String globalVideoPath;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    DefaultHttpDataSourceFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_tizer);

        globalVideoPath=getIntent().getStringExtra("path");

        playerView = findViewById(R.id.player_view);
        fullScreenCardView = playerView.findViewById(R.id.full_screen_card_view);
        progressBar = findViewById(R.id.progress_bar);

        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }


        Uri videoUrl = Uri.parse(globalVideoPath);


        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(FullScreenTizerActivity.this, trackSelector, loadControl);
        factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource;

        mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);

        playerView.setPlayer(simpleExoPlayer);

        simpleExoPlayer.prepare(mediaSource, true, false);

        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.seekTo(0,getIntent().getLongExtra("position_now",0));

        simpleExoPlayer.addListener(new Player.DefaultEventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                super.onTimelineChanged(timeline, manifest, reason);
            }


            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                super.onTracksChanged(trackGroups, trackSelections);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                super.onLoadingChanged(isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                super.onRepeatModeChanged(repeatMode);
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                super.onShuffleModeEnabledChanged(shuffleModeEnabled);
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                super.onPlayerError(error);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                super.onPositionDiscontinuity(reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                super.onPlaybackParametersChanged(playbackParameters);
            }

            @Override
            public void onSeekProcessed() {
                super.onSeekProcessed();
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                super.onTimelineChanged(timeline, manifest);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        releasePlayer();
        finish();
    }
}