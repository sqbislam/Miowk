package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

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



    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_word,container,false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


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

        WordApapter itemsAdapter = new WordApapter(getActivity(), colorsEng,R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudio_id());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListner);
                }
            }
        });



        return rootView;
    }


    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            am.abandonAudioFocus(afChangeListener);
        }
    }

    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
