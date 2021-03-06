package com.novoda.noplayer.exoplayer;

import com.novoda.noplayer.listeners.VideoSizeChangedListeners;

class VideoSizeChangedForwarder extends ExoPlayerVideoSizeChangedListener {

    private final VideoSizeChangedListeners videoSizeChangedListeners;

    VideoSizeChangedForwarder(VideoSizeChangedListeners videoSizeChangedListeners) {
        this.videoSizeChangedListeners = videoSizeChangedListeners;
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        videoSizeChangedListeners.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
    }
}
