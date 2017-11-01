package com.sourcey.Hackaroad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.sourcey.Hackaroad.LoginActivity;
import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.SignupActivity;
import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.service.BackPressCloseHandler;
import com.sourcey.Hackaroad.utils.ApiRequester;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TabMenuActivity extends ActionBarActivity implements MaterialTabListener {

    private BackPressCloseHandler backPressCloseHandle;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

//    private String username;
    private String loginid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabmenu_activity);
        final Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        backPressCloseHandle = new BackPressCloseHandler(this);

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();
        loginid = setting.getString("Id", "");

        Driver.getInstance().setloginid(setting.getString("Id", ""));

        ApiRequester.getInstance().getDriver(Driver.getInstance().getLoginid(), new ApiRequester.UserCallback<Driver>() {
            @Override
            public void onSuccess(Driver result) {
                System.out.println("이름은"+result.getname());
                System.out.println("이름은"+result.getLoginid());

                toolbar.setTitle(result.getname());
            }
            @Override
            public void onFail() {
            }
        });


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
            else
            {
                return "통계";
            }
        }
    }
}