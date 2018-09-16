package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumberActivity extends AppCompatActivity {

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

    private MediaPlayer.OnCompletionListener mCompletionListner= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> numbersEng= new ArrayList<Word>();
        numbersEng.add(new Word("One","Ak",R.drawable.number_one, R.raw.number_one));
        numbersEng.add(new Word("Two","Dui",R.drawable.number_two,R.raw.number_two));
        numbersEng.add(new Word("Three","Tin",R.drawable.number_three,R.raw.number_three));
        numbersEng.add(new Word("Four","Char",R.drawable.number_four,R.raw.number_four));
        numbersEng.add(new Word("Five","Paach",R.drawable.number_five,R.raw.number_five));
        numbersEng.add(new Word("Six","Choi",R.drawable.number_six,R.raw.number_six));
        numbersEng.add(new Word("Seven","Shaat",R.drawable.number_seven,R.raw.number_seven));
        numbersEng.add(new Word("Eight","Aat",R.drawable.number_eight,R.raw.number_eight));
        numbersEng.add(new Word("Nine","Noi",R.drawable.number_nine,R.raw.number_nine));
        numbersEng.add(new Word("Ten","Dosh",R.drawable.number_ten,R.raw.number_ten));

        WordApapter itemsAdapter = new WordApapter(this, numbersEng, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Word word= numbersEng.get(i);
        releaseMediaPlayer();
            int result = am.requestAudioFocus(afChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(NumberActivity.this, word.getAudio_id());
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
