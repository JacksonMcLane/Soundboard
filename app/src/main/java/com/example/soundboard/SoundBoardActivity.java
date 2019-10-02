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

    private Map<Integer, Integer> noteMap;
    private Button buttonAKey, buttonBFlatKey, buttonBKey, buttonCKey, buttonCSharpKey, buttonDKey;
    private Button buttonDSharpKey, buttonEKey, buttonFKey, buttonFSharpKey, buttonGKey, buttonGSharpKey;
    private Button buttonGMinorScale, buttonCustomSong, buttonRecord, buttonResetSong;
    private Button buttonClickMe;
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
    private int aNote, bbNote, bNote, cNote, csNote, dNote, dsNote, eNote, fNote, fsNote, gNote, gsNote;
    private int bestSong;
    private ArrayList<Integer> gMinorNotes;

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
        aNote = soundPool.load(this, R.raw.scalea, 1);
        bNote = soundPool.load(this, R.raw.scaleb, 1);
        bbNote = soundPool.load(this, R.raw.scalebb, 1);
        cNote = soundPool.load(this, R.raw.scalec, 1);
        csNote = soundPool.load(this, R.raw.scalecs, 1);
        dNote = soundPool.load(this, R.raw.scaled, 1);
        dsNote = soundPool.load(this, R.raw.scaleds, 1);
        eNote = soundPool.load(this, R.raw.scalee, 1);
        fNote = soundPool.load(this, R.raw.scalef, 1);
        fsNote = soundPool.load(this, R.raw.scalefs, 1);
        gNote = soundPool.load(this, R.raw.scaleg, 1);
        gsNote = soundPool.load(this, R.raw.scalegs, 1);
        bestSong = soundPool.load(this, R.raw.bestsong, 1);
        noteMap = new HashMap<>();
        noteMap.put(buttonAKey.getId(), aNote);
        noteMap.put(buttonBFlatKey.getId(), bbNote);
        noteMap.put(buttonBKey.getId(), bNote);
        noteMap.put(buttonCKey.getId(), cNote);
        noteMap.put(buttonCSharpKey.getId(), csNote);
        noteMap.put(buttonDKey.getId(), dNote);
        noteMap.put(buttonDSharpKey.getId(), dsNote);
        noteMap.put(buttonEKey.getId(), eNote);
        noteMap.put(buttonFKey.getId(), fNote);
        noteMap.put(buttonFSharpKey.getId(), fsNote);
        noteMap.put(buttonGKey.getId(), gNote);
        noteMap.put(buttonGSharpKey.getId(), gsNote);
        noteMap.put(buttonClickMe.getId(), bestSong);
        gMinorNotes.add(aNote);
        gMinorNotes.add(bbNote);
        gMinorNotes.add(cNote);
        gMinorNotes.add(dNote);
        gMinorNotes.add(dsNote);
        gMinorNotes.add(fNote);
        gMinorNotes.add(gNote);
    }

    private void setListeners() {
        KeyboardListener keyboardListener = new KeyboardListener();
        buttonAKey.setOnClickListener(keyboardListener);
        buttonBFlatKey.setOnClickListener(keyboardListener);
        buttonBKey.setOnClickListener(keyboardListener);
        buttonCKey.setOnClickListener(keyboardListener);
        buttonCSharpKey.setOnClickListener(keyboardListener);
        buttonDKey.setOnClickListener(keyboardListener);
        buttonDSharpKey.setOnClickListener(keyboardListener);
        buttonEKey.setOnClickListener(keyboardListener);
        buttonFKey.setOnClickListener(keyboardListener);
        buttonFSharpKey.setOnClickListener(keyboardListener);
        buttonGKey.setOnClickListener(keyboardListener);
        buttonGSharpKey.setOnClickListener(keyboardListener);
        buttonClickMe.setOnClickListener(keyboardListener);
        buttonCustomSong.setOnClickListener(this);
        buttonGMinorScale.setOnClickListener(this);
        buttonRecord.setOnClickListener(this);
        buttonResetSong.setOnClickListener(this);

    }


    private void wireWidgets() {
        buttonAKey = findViewById(R.id.button_main_key_a);
        buttonBFlatKey = findViewById(R.id.button_main_key_b_flat);
        buttonBKey = findViewById(R.id.button_main_key_b);
        buttonCKey = findViewById(R.id.button_main__key_c);
        buttonCSharpKey = findViewById(R.id.button_main_key_c_sharp);
        buttonDKey = findViewById(R.id.button_main_key_d);
        buttonDSharpKey = findViewById(R.id.button_main_key_d_sharp);
        buttonEKey = findViewById(R.id.button_main_key_e);
        buttonFKey = findViewById(R.id.button_main_key_f);
        buttonFSharpKey = findViewById(R.id.button_main_key_f_sharp);
        buttonGKey = findViewById(R.id.button_main_key_g);
        buttonGSharpKey = findViewById(R.id.button_main_key_g_sharp);
        buttonCustomSong = findViewById(R.id.button_main_custom_song);
        buttonGMinorScale = findViewById(R.id.button_main_scale);
        buttonRecord = findViewById(R.id.button_main_record_stop);
        buttonResetSong = findViewById(R.id.button_main_reset_song);
        buttonClickMe = findViewById(R.id.button_main_click_me);

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
                    for (Note note : song.getNotes()) {
                        if (isFirstNote) {
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
                for (int i : gMinorNotes) {
                    soundPool.play(i, volume, volume, 1, 0, 1f);
                    delay(300);
                }
                for (int i = gMinorNotes.size() - 1; i >= 0; i--) {
                    soundPool.play(gMinorNotes.get(i), volume, volume, 1, 0, 1f);
                    delay(300);
                }
                break;
            }
            case R.id.button_main_record_stop: {
                if (isRecording) {
                    isRecording = false;
                    buttonRecord.setText(getString(R.string.record));
                } else {
                    isRecording = true;
                    oldMillis = SystemClock.elapsedRealtime();
                    buttonRecord.setText(getString(R.string.stop));
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
            int soundID = noteMap.get(view.getId());
            float volume = actualVolume / maxVolume;
            if (soundID != 0 || soundID != bestSong) {
                Note note = new Note(0, 0);
                if (isFirstClick) {
                    oldMillis = SystemClock.elapsedRealtime();
                    currentClick = 0;
                    oldClick = 0;
                }
                if (currentClick - 1 == oldClick) {
                    newMillis = SystemClock.elapsedRealtime();
                    oldClick++;
                }
                if (!isFirstClick) {
                    note.setDelayInMillis((int) (newMillis - oldMillis));
                    oldMillis = newMillis;
                }
                isFirstClick = false;
                note.setSoundID(soundID);
                soundPool.play(soundID, volume, volume, 1, 0 ,1f);
                currentClick++;
                if (isRecording) {
                    song.addNote(note);
                }
            }
        }
    }
}
