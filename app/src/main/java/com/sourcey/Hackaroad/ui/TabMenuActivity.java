package com.sourcey.Hackaroad.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.model.Case_List;
import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.service.BackPressCloseHandler;
import com.sourcey.Hackaroad.utils.ApiRequester;

import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TabMenuActivity extends ActionBarActivity implements MaterialTabListener{

    private BackPressCloseHandler backPressCloseHandle;
    FragmentTransaction fragmentTransaction;


    SharedPreferences setting;
    SharedPreferences.Editor editor;

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;
    Resources res;

//    private String username;
    private String loginid;
    private String list_arr[];
    private String list_habit_arr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabmenu_activity);
        final Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));

        backPressCloseHandle = new BackPressCloseHandler(this);

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();
        loginid = setting.getString("Id", "");

        Driver.getInstance().setloginid(setting.getString("Id", ""));

        ApiRequester.getInstance().getDriver(Driver.getInstance().getLoginid(), new ApiRequester.UserCallback<Driver>() {
            @Override
            public void onSuccess(Driver result) {
                toolbar.setTitle(result.getname());
            }
            @Override
            public void onFail() {
            }
        });

        ApiRequester.getInstance().getList(new ApiRequester.UserCallback<List<Case_List>>() {
            @Override
            public void onSuccess(List<Case_List> result) {
                    if(result==null)
                    {
                        Toast.makeText(TabMenuActivity.this, "정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int size = result.size();
                        list_arr = new String[size];
                        int count = 0;

                        for(Case_List list : result)
                        {
                            list_arr[count] = list.gethabbitname();
                            count++;
                        }
                                for(int i=0; i<list_arr.length; i++)
                                    {
                                               System.out.println(i+"number"+list_arr[i]);
                                    }

                    }
            }
            @Override
            public void onFail() {
            }
        });

        Frag_ListActivity frament = new Frag_ListActivity();
        Bundle bundle = new Bundle();
        bundle.putInt("id", 1);
        frament.setArguments(bundle);

        int test = bundle.getInt("id");
        System.out.println("테스트"+test);

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

    public void setMyData(String[] habitname) {
        this.list_habit_arr = habitname;
        getMyData();
    }

    public String[] getMyData()
    {
        return list_habit_arr;
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