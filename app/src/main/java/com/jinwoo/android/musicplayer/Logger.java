package com.jinwoo.android.musicplayer;

import android.util.Log;

/** 로깅 객체
 *
 * @author jinwoo
 * @version 1.0
 * @since 2017
 *
 */

public class Logger {

    // Debug Mode에서만 Log가 작동하도록 한다.
    public final static boolean DEBUG_MODE = BuildConfig.DEBUG; // 빌드 타입을 아려준다.

    /** 로그내용을 콘솔에 출력
     *
     * @param string
     * @param className
     */

    public static void print(String string, String className){
        if(DEBUG_MODE) {
            Log.d(className, string);

            // 로그내용을 로그파일에 저장.....
            // File.append()
        }
    }

}
