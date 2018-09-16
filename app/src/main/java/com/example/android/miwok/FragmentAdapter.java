package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by User on 8/26/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm, MainActivity mainActivity){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return new NumberFragment();
        else if (position == 1)
            return new FamilyFragment();
        else if (position == 2)
            return new ColorFragment();
        else if(position == 3)
            return new PhrasesFragment();
        else return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Numbers";
        else if (position == 1)
            return "Family";
        else if (position == 2)
            return "Colors";
        else if(position == 3)
            return "Phrases";
        else return null;
    }
}
