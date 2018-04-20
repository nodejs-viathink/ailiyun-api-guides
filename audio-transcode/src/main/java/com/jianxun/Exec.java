package com.jianxun;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Exec {
    public static void main(String[] args) throws IOException, InterruptedException {
        // ./ffmpeg -i nihao.mp3 -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -y nihau_pcm.pcm
        String ffmpegPath = "/Users/jianxun/tools/ffmpeg-static/bin/ffmpeg";
        String inFilePath = System.getProperty("user.dir") + "/src/main/resources/a.mp3";
        String outPath = System.getProperty("user.dir") + "/src/main/resources/a_out.pcm";
        String cmdTmp = "%s -i %s -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -y %s";
        String cmd = String.format(cmdTmp, ffmpegPath, inFilePath, outPath);
        System.out.println("cmd:" + cmd);
        CommandLine cmdLine = CommandLine.parse(cmd);
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);// 设置超时时间：30秒
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out, stderr));
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        executor.execute(cmdLine, resultHandler);
        resultHandler.waitFor();
        int exitValue = resultHandler.getExitValue();
        if (exitValue != 0) {
            System.out.println("transcode failed: " + exitValue + ",err: " + stderr.toString());
        } else {
            System.out.println("transcode success: " + exitValue);
        }
    }
}

