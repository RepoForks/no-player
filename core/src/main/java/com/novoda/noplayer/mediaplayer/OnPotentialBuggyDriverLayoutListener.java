package com.novoda.noplayer.mediaplayer;

import android.view.View;

import com.novoda.noplayer.Player;

class OnPotentialBuggyDriverLayoutListener implements View.OnLayoutChangeListener {

    private final Player player;

    OnPotentialBuggyDriverLayoutListener(Player player) {
        this.player = player;
    }

    @SuppressWarnings("checkstyle:parameternumber") //Checkstyle should not complain about interface methods. No way to workaround this.
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (statusMightBeCorrupted()) {
            forceAlignNativeMediaPlayerStatus();
        }
    }

    private boolean statusMightBeCorrupted() {
        return player.isPlaying();
    }

    private void forceAlignNativeMediaPlayerStatus() {
        player.play();
    }

}
