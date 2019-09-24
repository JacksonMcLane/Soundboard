package com.example.soundboard;

public class Note {

    private int soundID;
    private int delayInMillis;

    public Note(int soundID, int delayInMillis) {
        this.soundID = soundID;
        this.delayInMillis = delayInMillis;
    }

    public int getSoundID() {
        return soundID;
    }

    public void setSoundID(int soundID) {
        this.soundID = soundID;
    }

    public int getDelayInMillis() {
        return delayInMillis;
    }

    public void setDelayInMillis(int delayInMillis) {
        this.delayInMillis = delayInMillis;
    }
}
