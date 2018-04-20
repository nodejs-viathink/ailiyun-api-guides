package com.jianxun;

import com.jianxun.thread.AudioTranscode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String ffmpegPath = "/Users/jianxun/tools/ffmpeg-static/bin/ffmpeg";
        String inFilePath = System.getProperty("user.dir") + "/src/main/resources/a.mp3";
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            String outPath = System.getProperty("user.dir") + "/src/main/resources/a_out" + i + ".pcm";
            AudioTranscode audioTranscode = new AudioTranscode(ffmpegPath, inFilePath, outPath);
            executorService.submit(audioTranscode);
        }
    }
}
