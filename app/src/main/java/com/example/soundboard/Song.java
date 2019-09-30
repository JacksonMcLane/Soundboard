package com.example.soundboard;

import java.util.ArrayList;

public class Song {
    private ArrayList<Note> notes;

    public Song() {
        notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void resetSong() {
        for(int i = notes.size() - 1; i >= 0; i--) {
            notes.remove(i);
        }
    }

}
