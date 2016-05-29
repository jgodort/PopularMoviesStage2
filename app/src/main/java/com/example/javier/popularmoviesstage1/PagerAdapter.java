package com.example.javier.popularmoviesstage1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by javie on 29/05/2016.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {


    List<Fragment> mFragments;

    public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case GenericFragment.POPULAR_MOVIES_QUERY:
                return "Popular";
            case GenericFragment.TOP_RATED_MOVIES_QUERY:
                return "Top Rated";
            case GenericFragment.MY_FAVORITES:
                return "Favorites";
            default:
                throw new IllegalArgumentException("Requesting a non existing fragment");
        }
    }
}
