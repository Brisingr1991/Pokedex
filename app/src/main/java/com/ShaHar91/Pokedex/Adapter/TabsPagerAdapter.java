package com.ShaHar91.Pokedex.Adapter;

import com.ShaHar91.Pokedex.moveTab1;
import com.ShaHar91.Pokedex.moveTab2;
import com.ShaHar91.Pokedex.moveTab3;
import com.ShaHar91.Pokedex.moveTab4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new moveTab1();
            case 1:
                return new moveTab2();
            case 2:
                return new moveTab3();
            case 3:
                return new moveTab4();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
