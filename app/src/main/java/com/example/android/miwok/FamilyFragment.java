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
public class FamilyFragment extends Fragment {
    private MediaPlayer.OnCompletionListener mCompletionListner= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

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

    MediaPlayer mMediaPlayer;

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_word,container,false);
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> familyEng= new ArrayList<Word>();
        familyEng.add(new Word("Father","Abbu",R.drawable.family_father,R.raw.family_father));
        familyEng.add(new Word("Mother","Ammu",R.drawable.family_mother,R.raw.family_mother));
        familyEng.add(new Word("Sister","Bon",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyEng.add(new Word("Brother","Bhai",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyEng.add(new Word("Son","Chele",R.drawable.family_son,R.raw.family_younger_brother));
        familyEng.add(new Word("Daughter","Meye",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyEng.add(new Word("Grandmother","Dadi",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyEng.add(new Word("GrandFather","Dada",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordApapter itemsAdapter = new WordApapter(getActivity(), familyEng, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){



            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = familyEng.get(i);
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

    void releaseMediaPlayer(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();}
        mMediaPlayer=null;
        am.abandonAudioFocus(afChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
