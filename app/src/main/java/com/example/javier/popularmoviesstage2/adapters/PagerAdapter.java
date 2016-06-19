package com.example.javier.popularmoviesstage2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;

import java.util.List;

/**
 * Created by javie on 29/05/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int FAVORITES = 2;

    private List<Fragment> mFragments;

    public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null) {
            return 0;
        } else {
            return mFragments.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence code;
        switch (position) {
            case POPULAR:
                code = Constants.POPULAR_MOVIES_TEXT;
                break;
            case TOP_RATED:
                code = Constants.TOP_RATED_MOVIES_TEXT;
                break;
            case FAVORITES:
                code = Constants.MY_FAVORITES_TEXT;
                break;
            default:
                throw new IllegalArgumentException("Requesting a non existing fragment");
        }
        return code;
    }
}
