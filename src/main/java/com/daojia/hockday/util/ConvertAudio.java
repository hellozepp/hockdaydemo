package com.daojia.hockday.util;

import ws.schild.jave.*;

import java.io.File;

public class ConvertAudio {
    public static void audioToWav(File source, File target) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(16000);
        audio.setChannels(1);
        audio.setSamplingRate(16000);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("s16le");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder(new FFMPEGLocator() {
            @Override
            protected String getFFMPEGExecutablePath() {
                return "/usr/bin/ffmpeg";
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
