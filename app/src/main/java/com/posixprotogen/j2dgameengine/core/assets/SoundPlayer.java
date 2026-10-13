package com.posixprotogen.j2dgameengine.core.assets;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

public class SoundPlayer {

    private Context context;
    private SoundPool soundPool;
    private HashMap<String, Integer> sounds = new HashMap<>();
    private MediaPlayer music;
    private boolean musicPaused;

    public SoundPlayer(Context context) {
        this.context = context;

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(16)
                .setAudioAttributes(attributes)
                .build();
    }

    public void addSound(String file) {
        if (sounds.containsKey(file)) {
            return;
        }

        try {
            AssetFileDescriptor afd = context.getAssets().openFd(file);
            int id = soundPool.load(afd, 1);
            afd.close();
            sounds.put(file, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playSound(String file, float volume) {
        Integer id = sounds.get(file);
        if (id == null) {
            throw new RuntimeException("Som não carregado: " + file);
        }

        soundPool.play(id, volume, volume, 0, 0, 1f);
    }

    public void playMusic(String file, float volume, boolean repeat, int sec) {
        if (music != null) {
            music.release();
            music = null;
        }

        music = new MediaPlayer();
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(file);
            music.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

            music.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            music.setLooping(repeat);
            music.setVolume(volume, volume);
            music.prepare();
            music.seekTo(sec*1000);
            music.start();
            musicPaused = false;
        } catch (IOException e) {
            music.release();
            music = null;
            throw new RuntimeException(e);
        }
    }

    public void pause() {
        if (music != null && music.isPlaying()) {
            music.pause();
            musicPaused = true;
        }
        soundPool.autoPause();
    }

    public void resume() {
        if (music != null && musicPaused) {
            music.start();
            musicPaused = false;
        }
        soundPool.autoResume();
    }

    public void release() {
        if (music != null) {
            music.release();
            music = null;
        }
        soundPool.release();
        sounds.clear();
    }
}
