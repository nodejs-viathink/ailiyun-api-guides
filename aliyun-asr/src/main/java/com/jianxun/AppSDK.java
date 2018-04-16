package com.jianxun;

import com.jianxun.sdk.AsrSDK;

/**
 * Hello world!
 *
 */
public class AppSDK {
    public static void main( String[] args ) {
        String accessKey = args[0];
        String accessSecret = args[1];
        AsrSDK asr = new AsrSDK(accessKey, accessSecret);
        asr.startAsr();
        asr.shutDown();
    }
}
