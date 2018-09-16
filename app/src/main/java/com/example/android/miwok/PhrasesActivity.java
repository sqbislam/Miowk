package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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

        final ArrayList<Word> phraseEng= new ArrayList<Word>();
        phraseEng.add(new Word("Where are you going?","Tumi koi jaccho?",R.raw.phrase_where_are_you_going));
        phraseEng.add(new Word("What is your name","Tomar naam ki?",R.raw.phrase_what_is_your_name));
        phraseEng.add(new Word("My name is...","Amar nam...",R.raw.phrase_my_name_is));
        phraseEng.add(new Word("How are you feeling","Tomar kemon lagche?",R.raw.phrase_how_are_you_feeling));
        phraseEng.add(new Word("I'm feeling good","Amar bhalo lagche",R.raw.phrase_im_feeling_good));
        phraseEng.add(new Word("Are you coming","Tumi ashteso?",R.raw.phrase_are_you_coming));
        phraseEng.add(new Word("Yes I'm coming","Hai ami ashtesi",R.raw.phrase_yes_im_coming));
        phraseEng.add(new Word("I'm coming","Ami Ashtesi",R.raw.phrase_im_coming));
        phraseEng.add(new Word("Let's Go","Cholo jai",R.raw.phrase_lets_go));
        phraseEng.add(new Word("Come here","Eidik asho",R.raw.phrase_come_here));


        WordApapter itemsAdapter = new WordApapter(this, phraseEng, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word= phraseEng.get(i);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudio_id());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListner);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    void releaseMediaPlayer(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();}
        mMediaPlayer=null;
        am.abandonAudioFocus(afChangeListener);
    }
}
