package com.novoda.noplayer.exoplayer;

import com.google.android.exoplayer.ExoPlayer;
import com.novoda.noplayer.listeners.BufferStateListeners;

class BufferStateForwarder extends ExoPlayerStateChangedListener {

    private final BufferStateListeners bufferStateListeners;

    BufferStateForwarder(BufferStateListeners bufferStateListeners) {
        this.bufferStateListeners = bufferStateListeners;
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_PREPARING || playbackState == ExoPlayer.STATE_BUFFERING) {
            bufferStateListeners.onBufferStarted();
        } else if (playbackState == ExoPlayer.STATE_READY) {
            bufferStateListeners.onBufferCompleted();
        }
    }

}
