package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SoundBoardActivity extends AppCompatActivity {

    private Button a_key;
    private Button b_flat_key;
    private Button b_key;
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);
        wireWidgets();
        setListeners();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.scalea, 1);

    }

    private void setListeners() {
        a_key.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
    }



    private void wireWidgets() {
        a_key = findViewById(R.id.button_main_key_a);
        b_flat_key = findViewById(R.id.button_main_key_b_flat);
        b_key = findViewById(R.id.button_main_key_b);
    }
}
