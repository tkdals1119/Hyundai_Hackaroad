package com.sourcey.Hackaroad.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.service.BackPressCloseHandler;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TabMenuActivity extends ActionBarActivity implements MaterialTabListener{

    private BackPressCloseHandler backPressCloseHandle;

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabmenu_activity);
        backPressCloseHandle = new BackPressCloseHandler(this);
        res = this.getResources();

        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setTitle("일해라노예야");
        toolbar.setTitleTextColor(Color.parseColor("#000"));

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager);


        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        tabHost.setTextColor(Color.parseColor("#00aeef"));

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }
    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        backPressCloseHandle.onBackPressed();
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return new Frag_ListActivity();
                case 1:
                    return new Frag_StatisticsActivity();

                default:
                    return null;
            }
        }


        @Override
        public int getCount() { // 페이지 수
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position==0)
            {
                return "리스트";
            }
            else if(position==1)
            {
                return "통계";
            }
            else return null;
        }
    }

    public Drawable getIcon(int position) {

        if(position==0)
        {
            return  res.getDrawable(R.drawable.ic_navigate_next_black_48dp);
        }
        else
        {
            return res.getDrawable(R.drawable.ic_navigate_next_black_48dp);
        }

    }
}