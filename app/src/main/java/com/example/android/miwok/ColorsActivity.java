package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer.OnCompletionListener mCompletionListner= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    MediaPlayer mMediaPlayer;


    AudioManager am;
    AudioManager.OnAudioFocusChangeListener afChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);}
            else if(i==AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if(i==AudioManager.AUDIOFOCUS_LOSS)
                mMediaPlayer.stop();
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> colorsEng= new ArrayList<Word>();
        colorsEng.add(new Word("Red","Lal",R.drawable.color_red,R.raw.color_red));
        colorsEng.add(new Word("Green","Shobuj",R.drawable.color_green,R.raw.color_green));
        colorsEng.add(new Word("Brown","Badami",R.drawable.color_brown,R.raw.color_brown));
        colorsEng.add(new Word("Gray","Dhushor",R.drawable.color_gray,R.raw.color_gray));
        colorsEng.add(new Word("Black","Kalo",R.drawable.color_black,R.raw.color_black));
        colorsEng.add(new Word("White","Shada",R.drawable.color_white,R.raw.color_white));
        colorsEng.add(new Word("Blue","Neel",R.drawable.color_blue,R.raw.color_dusty_yellow));
        colorsEng.add(new Word("Orange","Komola",R.drawable.color_orange,R.raw.color_mustard_yellow));
        colorsEng.add(new Word("SkyBlue","Akashi",R.drawable.color_skyblue,R.raw.color_green));
        colorsEng.add(new Word("Yellow","Holud",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordApapter itemsAdapter = new WordApapter(this, colorsEng,R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = colorsEng.get(i);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudio_id());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListner);
                }
            }
        });



    }

    void releaseMediaPlayer(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();}
        mMediaPlayer=null;
        am.abandonAudioFocus(afChangeListener);
        Log.v("ColorsActivity", "remove executed" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
