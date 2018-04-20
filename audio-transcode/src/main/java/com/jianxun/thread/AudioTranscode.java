package com.jianxun.thread;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;

public class AudioTranscode implements Runnable {
    final private String cmdTmp = "%s -i %s -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -y %s";
    private String ffmpegPath;
    private String inputPath;
    private String outputPath;
    private String cmd;

    public AudioTranscode(String ffmpegPath, String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.inputPath = outputPath;
        this.ffmpegPath = ffmpegPath;
        this.cmd = String.format(cmdTmp, ffmpegPath, inputPath, outputPath);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("thread: " + threadName + " start...");
        CommandLine cmdLine = CommandLine.parse(cmd);
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(30000);// 设置超时时间：30秒
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(System.out, stderr));
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        try {
            executor.execute(cmdLine, resultHandler);
            resultHandler.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int exitValue = resultHandler.getExitValue();
        if (exitValue != 0) {
            System.out.println(threadName + " transcode failed: " + exitValue + ",err: " + stderr.toString());
        } else {
            System.out.println(threadName + "transcode success: " + exitValue);
        }
    }
}
