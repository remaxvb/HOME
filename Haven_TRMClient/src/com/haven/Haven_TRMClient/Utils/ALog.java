package com.haven.Haven_TRMClient.Utils;

import android.util.Log;

/**
 * Created by hieu.t.vo on 3/4/14.
 *
 * Use to show logcat with debug flag
 * Only show logcat when debug = true
 */
public class ALog {
    /**
     * Show debug logcat
     * @param debug
     * @param tag
     * @param message
     */
    public static void d(final boolean debug, String tag, String message) {
        if(debug) {
            Log.d(tag, message);
        }
    }

    /**
     * Show error logcat
     * @param debug
     * @param tag
     * @param message
     */
    public static void e(final boolean debug, String tag, String message) {
        if(debug) {
            Log.e(tag, message);
        }
    }
}
