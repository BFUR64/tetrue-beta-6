package com.teic.trueris.sound;

import javax.sound.sampled.AudioFormat;

public record SoundData (
        byte[] audioData, 
        AudioFormat format, 
        long frameLength
        ) {}

