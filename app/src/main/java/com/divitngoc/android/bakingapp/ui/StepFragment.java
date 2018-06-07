package com.divitngoc.android.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.divitngoc.android.bakingapp.R;
import com.divitngoc.android.bakingapp.model.Step;
import com.divitngoc.android.bakingapp.utils.ConstantUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements Player.EventListener {

    @BindView(R.id.simple_exo_player_view)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.thumbnail_image)
    ImageView ivThumbnail;
    @BindView(R.id.step_detail_method)
    TextView tvStepDetail;

    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private long currentPosition;
    private static final String EXO_CURRENT_POSITION_KEY = "currentPosition";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ConstantUtils.STEP_KEY)) {
            mStep = bundle.getParcelable(ConstantUtils.STEP_KEY);
        }

        currentPosition = 0;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong(EXO_CURRENT_POSITION_KEY);
        }

        String mediaStr = mStep.getVideoUrlPath();

        setupPlayer(mediaStr);

        if (currentPosition != 0) {
            mExoPlayer.seekTo(currentPosition);
            mExoPlayer.setPlayWhenReady(true);
        }

        tvStepDetail.setText(mStep.getDescription());
        return rootView;
    }

    private void setupPlayer(String mediaStr) {
        String thumbnail = mStep.getThumbnailUrlPath();
        if (!TextUtils.isEmpty(mediaStr)) {
            initializePlayer(Uri.parse(mediaStr));
        } else if (!TextUtils.isEmpty(thumbnail)) {
            mPlayerView.setVisibility(View.GONE);
            Picasso.with(getContext())
                    .load(thumbnail)
                    //.error()
                    //.placeholder()
                    .into(ivThumbnail);
        } else {
            mPlayerView.setVisibility(View.GONE);
            ivThumbnail.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "Baking App");
            MediaSource videoSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(false);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null && mExoPlayer.getPlayWhenReady()) {
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.removeListener(this);
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (Player.STATE_ENDED == playbackState) {
            mExoPlayer.seekTo(0);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mExoPlayer != null) {
            outState.putLong(EXO_CURRENT_POSITION_KEY, mExoPlayer.getCurrentPosition());
        }
    }

}
