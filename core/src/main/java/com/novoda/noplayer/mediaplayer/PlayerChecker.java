package com.novoda.noplayer.mediaplayer;

import android.os.Build;

public class PlayerChecker {

    private static final String PROP_USE_NU_PLAYER = "media.stagefright.use-nuplayer";
    private static final String PROP_USE_AWESOME_PLAYER_PERSIST = "persist.sys.media.use-awesome";
    private static final String PROP_USE_AWESOME_PLAYER_MEDIA = "media.stagefright.use-awesome";

    private final int deviceOSVersion;
    private final SystemProperties systemProperties;

    public static PlayerChecker newInstance() {
        return new PlayerChecker(new SystemProperties(), Build.VERSION.SDK_INT);
    }

    PlayerChecker(SystemProperties systemProperties, int deviceOSVersion) {
        this.systemProperties = systemProperties;
        this.deviceOSVersion = deviceOSVersion;
    }

    public AndroidMediaPlayerType getPlayerType() {
        AndroidMediaPlayerType playerType;
        try {
            playerType = getMediaPlayerType();
        } catch (SystemProperties.MissingSystemPropertiesException e) {
            playerType = AndroidMediaPlayerType.UNKNOWN;
        }
        return playerType;
    }

    private AndroidMediaPlayerType getMediaPlayerType() throws SystemProperties.MissingSystemPropertiesException {
        return deviceOSVersion >= Build.VERSION_CODES.LOLLIPOP ? getPlayerTypeLollipop() : getPlayerTypePreLollipop();
    }

    private AndroidMediaPlayerType getPlayerTypeLollipop() throws SystemProperties.MissingSystemPropertiesException {
        // NuPlayer is enabled if property is false or absent
        // http://androidxref.com/5.0.0_r2/xref/frameworks/av/media/libmediaplayerservice/MediaPlayerFactory.cpp#63
        boolean isAwesomePlayerEnabled = getBooleanProp(PROP_USE_AWESOME_PLAYER_PERSIST) || getBooleanProp(PROP_USE_AWESOME_PLAYER_MEDIA);
        return isAwesomePlayerEnabled ? AndroidMediaPlayerType.AWESOME : AndroidMediaPlayerType.NU;
    }

    private AndroidMediaPlayerType getPlayerTypePreLollipop() throws SystemProperties.MissingSystemPropertiesException {
        // NuPlayer is disabled if property is false or absent
        // http://androidxref.com/4.4.4_r1/xref/frameworks/av/media/libmediaplayerservice/MediaPlayerFactory.cpp#63
        return getBooleanProp(PROP_USE_NU_PLAYER) ? AndroidMediaPlayerType.NU : AndroidMediaPlayerType.AWESOME;
    }

    private boolean getBooleanProp(String prop) throws SystemProperties.MissingSystemPropertiesException {
        String value = systemProperties.get(prop);
        return "true".equalsIgnoreCase(value) || "1".equals(value);
    }

}
