package com.ShaHar91.Pokedex.Adapter;

import com.ShaHar91.Pokedex.Calc.CalcTab1;
import com.ShaHar91.Pokedex.Calc.CalcTab2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsCalc extends FragmentPagerAdapter {

    public TabsCalc(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new CalcTab1();
            case 1:
                return new CalcTab2();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
