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

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public Note getNote(int index) {
        return notes.get(index);
    }

    public void addNote(Note note) {
        notes.add(note);
    }
}
