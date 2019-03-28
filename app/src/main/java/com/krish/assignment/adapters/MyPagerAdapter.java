package com.krish.assignment.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.SearchView;

import com.krish.assignment.fragments.FragmentOne;
import com.krish.assignment.fragments.FragmentThree;
import com.krish.assignment.fragments.FragmentTwo;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    int no_of_pages;
    SearchView.OnQueryTextListener context;
    public MyPagerAdapter(FragmentManager fm, int numofPages) {
        super(fm);
        this.no_of_pages=numofPages;
    }

    public MyPagerAdapter(FragmentManager fm, SearchView.OnQueryTextListener context) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();

        }
        return null;
    }

    @Override
    public int getCount() {
        return no_of_pages;
    }
}
