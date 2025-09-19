package com.teic.trueris.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.teic.trueris.App;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;

public class SoundManager {
    private static final Map<Sound, SoundData> soundList = new HashMap<>();

    private Clip clip;

    public static void initialize() {
        loadSoundToList(Sound.MENU_MOVE, "/menu_move.wav");
        warmupSound(Sound.MENU_MOVE);

        loadSoundToList(Sound.MENU_SELECT, "/menu_select.wav");
        warmupSound(Sound.MENU_SELECT);

        loadSoundToList(Sound.MENU_BACK, "/menu_back.wav");
        warmupSound(Sound.MENU_BACK);

        loadSoundToList(Sound.BG_MUSIC, "/bg_music.wav");
        warmupSound(Sound.BG_MUSIC);
    }

    private static void loadSoundToList(Sound type, String path) {
        try {
            Logging.writeLog(LogType.DEBUG, 
                    "Loading sound: " + type);

            AudioInputStream stream = AudioSystem
                .getAudioInputStream(
                        App.class.getResource(path));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byte[] audioData = baos.toByteArray();
            AudioFormat audioFormat = stream.getFormat();
            long audioFrameLength = stream.getFrameLength();

            SoundData sData = new SoundData(
                    audioData, 
                    audioFormat, 
                    audioFrameLength);

            soundList.put(type, sData);
        }
        catch (IOException e) {
            Logging.writeLog(LogType.WARN, 
                    "I/O failed on file");
            Logging.writeStackTrace(LogType.WARN, e);
        }
        catch (UnsupportedAudioFileException e) {
            Logging.writeLog(LogType.WARN, 
                    "Unsupported Format");
            Logging.writeStackTrace(LogType.WARN, e);
        }
        catch (NullPointerException e) {
            Logging.writeLog(LogType.WARN, 
                    "Audio missing: " + path);
        }
    }

    public void playMusic(Sound type) {
        clip = prepareSound(type);

        if (clip == null)
            return;

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stopMusic() {
        if (clip == null)
            return;

        clip.stop();
        clip.close();
    }

    public static void playSFX(Sound type) {
        Clip clip = prepareSound(type);
        
        if (clip == null)
            return;

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.drain();
                clip.close();
            }
        });

        clip.start();
    }

    private static void warmupSound(Sound type) {
        Clip clip = prepareSound(type);
        
        if (clip == null)
            return;

        clip.close();
    }

    private static Clip prepareSound(Sound type) {
        Clip clip = null;

        try {
            SoundData sData = soundList.get(type);
            
            if (sData == null)
                return clip;

            ByteArrayInputStream bais = 
                new ByteArrayInputStream(sData.audioData());

            AudioInputStream stream = new AudioInputStream(
                    bais, 
                    sData.format(), 
                    sData.frameLength());

            clip = AudioSystem.getClip();
            clip.open(stream);
        }
        catch (IOException | LineUnavailableException e) {
            Logging.writeLog(LogType.WARN, 
                    "Error preparing " + type);
            Logging.writeStackTrace(LogType.WARN, e);
        }
        
        return clip;
    }
}

