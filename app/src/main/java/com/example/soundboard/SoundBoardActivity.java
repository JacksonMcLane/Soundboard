package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SoundBoardActivity extends AppCompatActivity {

    private Button a_key;
    private Button b_flat_key;
    private Button b_key;
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;
    private int anote;
    private int bnote;
    private int bbnote;
    private int cnote;
    private int csnote;
    private int dnote;
    private int dsnote;

    //A full octave will be: A, B♭, B, C, C♯, D, D♯, E, F, F♯, G, G♯

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        wireWidgets();
        loadSounds();
        setListeners();


    }

    private void loadSounds() {
        anote = soundPool.load(this, R.raw.scalea, 1);
        bnote = soundPool.load(this, R.raw.scaleb, 1);
        bbnote = soundPool.load(this, R.raw.scalebb, 1);
    }

    private void setListeners() {
        a_key.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                soundID = anote;
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                if (loaded) {
                    soundPool.play(soundID, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
            }
        });

        b_flat_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundID = bbnote;
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                if (loaded) {
                    soundPool.play(soundID, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
            }
        });

        b_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundID = bnote;
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                if (loaded) {
                    soundPool.play(soundID, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
            }
        });
    }



    private void wireWidgets() {
        a_key = findViewById(R.id.button_main_key_a);
        b_flat_key = findViewById(R.id.button_main_key_b_flat);
        b_key = findViewById(R.id.button_main_key_b);
    }
}
