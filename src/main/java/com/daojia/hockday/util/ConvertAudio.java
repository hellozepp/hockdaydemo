package com.daojia.hockday.util;

import ws.schild.jave.*;

import java.io.File;

public class ConvertAudio {
    public static void audioToWav(File source, File target) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder(new FFMPEGLocator() {
            @Override
            protected String getFFMPEGExecutablePath() {
                return "/usr/local/bin/ffmpeg";
            }
        });
        try {
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

}