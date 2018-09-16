package com.example.android.miwok;


import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 8/6/2017.
 */

public class WordApapter extends ArrayAdapter<Word> {

    int colorID;
    MediaPlayer mediaPlayer;

    public WordApapter(Context context,ArrayList<Word> list, int colorID){
        super(context,0,list);
        this.colorID=colorID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Word current=getItem(position);
        View listItemView= convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView name=(TextView)listItemView.findViewById(R.id.textViewEng);
        name.setText(current.getDefaultrans());
        TextView trans= (TextView) listItemView.findViewById(R.id.textViewMiowk);
        trans.setText(current.getMiowk());
        ImageView image = (ImageView) listItemView.findViewById(R.id.image_view);


        if(current.hasImage()) {
            image.setImageResource(current.getImage_Id());
            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }

        View text_container = listItemView.findViewById(R.id.color_layout);
        text_container.setBackgroundColor(ContextCompat.getColor(getContext(),colorID));

        View playbutton= listItemView.findViewById(R.id.play);
        playbutton.setBackgroundColor(ContextCompat.getColor(getContext(),colorID));
        return listItemView;
    }
}
