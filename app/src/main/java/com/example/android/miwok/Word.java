package com.example.android.miwok;

/**
 * Created by User on 8/6/2017.
 */
public class Word {
    private String Miowk = null;
    private String Defaultrans = null;
    private final int NO_IMAGE_RES = -1;
    private int image_Id = NO_IMAGE_RES;
    private int audio_id;

    public Word(String miowk, String defaultrans, int image_Id, int audio) {
        Miowk = miowk;
        Defaultrans = defaultrans;
        this.image_Id = image_Id;
        this.audio_id=audio;
    }

    public int getAudio_id() {
        return audio_id;
    }

    public Word(String Miowk, String English , int audio) {
        this.Miowk = Miowk;
        this.Defaultrans = English;
        this.audio_id=audio;
    }

    public String getDefaultrans() {

        return Defaultrans;
    }

    public String getMiowk() {
        return Miowk;
    }

    public int getImage_Id() {
        return image_Id;
    }

    public boolean hasImage(){
        return image_Id!=NO_IMAGE_RES;
    }
}

