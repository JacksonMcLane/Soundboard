package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SoundBoardActivity.class.getSimpleName();
    private Map<Integer, Integer> noteMap;
    private Button a_key, b_flat_key, b_key, c_key, c_sharp_key, d_key, d_sharp_key;
    private Button e_key, f_key, f_sharp_key, g_key, g_sharp_key, high_a_key, g_minor_scale;
    private Button customSong, record, reset_song, click_me;
    private SoundPool soundPool;
    private AudioManager audioManager;
    private float actualVolume;
    private float maxVolume;
    private Song song;
    boolean loaded = false;
    boolean isFirstClick = true;
    boolean isRecording = false;
    boolean isFirstNote = true;
    long oldMillis, newMillis;
    int currentClick, oldClick;
    private int anote, bbnote, bnote, cnote, csnote, dnote, dsnote, enote, fnote, fsnote, gnote, gsnote, higha;
    private int bestSong;
    private ArrayList<Integer> gMinorNotes;

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
        song = new Song();
        gMinorNotes = new ArrayList<>();
        wireWidgets();
        loadSounds();
        setListeners();


    }

    private void loadSounds() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        anote = soundPool.load(this, R.raw.scalea, 1);
        bnote = soundPool.load(this, R.raw.scaleb, 1);
        bbnote = soundPool.load(this, R.raw.scalebb, 1);
        cnote = soundPool.load(this, R.raw.scalec, 1);
        csnote = soundPool.load(this, R.raw.scalecs, 1);
        dnote = soundPool.load(this, R.raw.scaled, 1);
        dsnote = soundPool.load(this, R.raw.scaleds, 1);
        enote = soundPool.load(this, R.raw.scalee, 1);
        fnote = soundPool.load(this, R.raw.scalef, 1);
        fsnote = soundPool.load(this, R.raw.scalefs, 1);
        gnote = soundPool.load(this, R.raw.scaleg, 1);
        gsnote = soundPool.load(this, R.raw.scalegs, 1);
        higha = soundPool.load(this, R.raw.higha, 1);
        bestSong = soundPool.load(this, R.raw.bestsong, 1);
        noteMap = new HashMap<>();
        noteMap.put(a_key.getId(), anote);
        noteMap.put(b_flat_key.getId(), bbnote);
        noteMap.put(b_key.getId(), bnote);
        noteMap.put(c_key.getId(), cnote);
        noteMap.put(c_sharp_key.getId(), csnote);
        noteMap.put(d_key.getId(), dnote);
        noteMap.put(d_sharp_key.getId(), dsnote);
        noteMap.put(e_key.getId(), enote);
        noteMap.put(f_key.getId(), fnote);
        noteMap.put(f_sharp_key.getId(), fsnote);
        noteMap.put(g_key.getId(), gnote);
        noteMap.put(g_sharp_key.getId(), gsnote);
        noteMap.put(high_a_key.getId(), higha);
        noteMap.put(click_me.getId(), bestSong);
        gMinorNotes.add(anote);
        gMinorNotes.add(bbnote);
        gMinorNotes.add(cnote);
        gMinorNotes.add(dnote);
        gMinorNotes.add(dsnote);
        gMinorNotes.add(fnote);
        gMinorNotes.add(gnote);
        gMinorNotes.add(higha);
    }

    private void setListeners() {
        KeyboardListener keyboardListener = new KeyboardListener();
        a_key.setOnClickListener(keyboardListener);
        b_flat_key.setOnClickListener(keyboardListener);
        b_key.setOnClickListener(keyboardListener);
        c_key.setOnClickListener(keyboardListener);
        c_sharp_key.setOnClickListener(keyboardListener);
        d_key.setOnClickListener(keyboardListener);
        d_sharp_key.setOnClickListener(keyboardListener);
        e_key.setOnClickListener(keyboardListener);
        f_key.setOnClickListener(keyboardListener);
        f_sharp_key.setOnClickListener(keyboardListener);
        g_key.setOnClickListener(keyboardListener);
        g_sharp_key.setOnClickListener(keyboardListener);
        click_me.setOnClickListener(keyboardListener);
        customSong.setOnClickListener(this);
        g_minor_scale.setOnClickListener(this);
        record.setOnClickListener(this);
        reset_song.setOnClickListener(this);

    }


    private void wireWidgets() {
        a_key = findViewById(R.id.button_main_key_a);
        b_flat_key = findViewById(R.id.button_main_key_b_flat);
        b_key = findViewById(R.id.button_main_key_b);
        c_key = findViewById(R.id.button_main__key_c);
        c_sharp_key = findViewById(R.id.button_main_key_c_sharp);
        d_key = findViewById(R.id.button_main_key_d);
        d_sharp_key = findViewById(R.id.button_main_key_d_sharp);
        e_key = findViewById(R.id.button_main_key_e);
        f_key = findViewById(R.id.button_main_key_f);
        f_sharp_key = findViewById(R.id.button_main_key_f_sharp);
        g_key = findViewById(R.id.button_main_key_g);
        g_sharp_key = findViewById(R.id.button_main_key_g_sharp);
        high_a_key = findViewById(R.id.button_main_key_high_a);
        customSong = findViewById(R.id.button_main_custom_song);
        g_minor_scale = findViewById(R.id.button_main_scale);
        record = findViewById(R.id.button_main_record_stop);
        reset_song = findViewById(R.id.button_main_reset_song);
        click_me = findViewById(R.id.button_main_click_me);

    }

    private void delay(int millisDelay) {
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_main_custom_song: {
                if (loaded && song != null) {
                    float volume = actualVolume / maxVolume;
                    for(Note note : song.getNotes()) {
                        if(isFirstNote) {
                            isFirstNote = false;
                        }
                        delay(note.getDelayInMillis());
                        soundPool.play(note.getSoundID(), volume, volume, 1, 0, 1f);
                    }
                }
                break;
            }
            case R.id.button_main_scale: {
                float volume = actualVolume / maxVolume;
                for(int i : gMinorNotes) {
                    soundPool.play(i, volume, volume, 1, 0, 1f);
                    delay(300);
                }
                for(int i = gMinorNotes.size()-1; i >= 0; i--) {
                    soundPool.play(gMinorNotes.get(i), volume, volume, 1, 0, 1f);
                    delay(300);
                }
                break;
            }
            case R.id.button_main_record_stop: {
                if(isRecording) {
                    isRecording = false;
                    record.setText(getString(R.string.record));
                }
                else {
                    isRecording = true;
                    oldMillis = SystemClock.elapsedRealtime();
                    record.setText(getString(R.string.stop));
                }
                break;
            }
            case R.id.button_main_reset_song: {
                song.resetSong();
                break;
            }
        }
    }

    private class KeyboardListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //read from map of key+value pairs
            //look up button pressed
            int soundID = noteMap.get(view.getId());
            //Note.setSoundID(songID);
            float volume = actualVolume / maxVolume;
            if (soundID != 0 || soundID != bestSong) {
                Note note = new Note(0,0);
                if (isFirstClick) {
                    oldMillis = SystemClock.elapsedRealtime();
                    currentClick = 0;
                    oldClick = 0;
                }
                if (currentClick - 1 == oldClick) {
                    newMillis = SystemClock.elapsedRealtime();
                    oldClick++;
                }
                if(!isFirstClick) {
                    note.setDelayInMillis((int) (newMillis - oldMillis));
                    oldMillis = newMillis;
                }
                isFirstClick = false;
                note.setSoundID(soundID);
                soundPool.play(soundID, volume, volume, 1, 0, 1f);
                currentClick++;
                if(isRecording) {
                    song.addNote(note);
                }
            }
        }
    }
}
