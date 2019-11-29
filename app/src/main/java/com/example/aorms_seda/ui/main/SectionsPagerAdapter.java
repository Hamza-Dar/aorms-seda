package com.example.aorms_seda.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aorms_seda.R;
import com.example.aorms_seda.fragment_Frame;
import com.example.aorms_seda.fragment_assign_tablet;
import com.example.aorms_seda.fragment_constraint;
import com.example.aorms_seda.fragment_linear;
import com.example.aorms_seda.fragment_relative;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position){
            case 0:

                fragment =new fragment_relative();
                break;
            case 1:
                fragment=new fragment_assign_tablet();
                break;
            case 2:
                fragment= new fragment_Frame();
                break;
            case 3:
                fragment= new fragment_linear();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}