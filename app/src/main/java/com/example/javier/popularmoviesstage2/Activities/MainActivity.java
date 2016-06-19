package com.example.javier.popularmoviesstage2.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.Utils.Constants;
import com.example.javier.popularmoviesstage2.adapters.PagerAdapter;
import com.example.javier.popularmoviesstage2.fragments.GenericFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private boolean mTwoPane = false;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureView();


        if (findViewById(R.id.movie_detail_container) != null) {

            mTwoPane = true;

        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }


    }

    private void configureView() {
        //Binding the views.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<Integer> lqueryCodes = new ArrayList<>();
        //Configuring the tabLayout and the queryCode List.
        tabLayout.addTab(tabLayout.newTab().setText(Constants.POPULAR_MOVIES_TEXT));
        lqueryCodes.add(PagerAdapter.POPULAR);
        tabLayout.addTab(tabLayout.newTab().setText(Constants.TOP_RATED_MOVIES_TEXT));
        lqueryCodes.add(PagerAdapter.TOP_RATED);
        tabLayout.addTab(tabLayout.newTab().setText(Constants.MY_FAVORITES_TEXT));
        lqueryCodes.add(PagerAdapter.FAVORITES);

        List<Fragment> listFraments = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.QUERY_FETCH_KEY, lqueryCodes.get(i));

            listFraments.add(GenericFragment
                    .instantiate(this, GenericFragment.class.getName(), bundle));
        }

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), listFraments));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }


}
