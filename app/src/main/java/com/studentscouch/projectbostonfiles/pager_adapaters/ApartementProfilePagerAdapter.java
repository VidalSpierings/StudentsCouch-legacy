package com.studentscouch.projectbostonfiles.pager_adapaters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApartementProfilePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentlistTitles = new ArrayList<>();

    public ApartementProfilePagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);

        // assign numTabs of method to numTabs of local variable

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);

        // get item at index of current fragmentList item

    }

    @Override
    public int getCount() {
        return fragmentlistTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return fragmentlistTitles.get(position);

        // get item title at index of current fragmentList item

    }

    public void AddFragment(Fragment fragment, String title){

        fragmentList.add(fragment);
        fragmentlistTitles.add(title);

        // add fragment and item title

    }



}
