package com.example.androdev.hotello.UserFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.androdev.hotello.SearchFragments.SearchCollege;
import com.example.androdev.hotello.SearchFragments.SearchEvents;
import com.example.androdev.hotello.SearchFragments.SearchOrganizers;
import com.example.androdev.hotello.SearchFragments.SearchPeople;


/**
 * Created by Asus on 3/3/2019.
 */

public class SearchPagerAdapter extends FragmentPagerAdapter {
    int mnumtabs;
    public SearchPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mnumtabs=NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new SearchCollege();
            case 1:
                return new SearchPeople();
            case 2:
                return new SearchOrganizers();
            case 3:
                return new SearchEvents();
            /*case 4:
                return new SearchLocation();*/
           /* case 4:
                return new SearchTags();
*/

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mnumtabs;
    }
}
