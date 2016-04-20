package com.quintet.littleweather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quintet.littleweather.R;
import com.quintet.littleweather.adapter.RecycleView;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.bean.item;
import com.quintet.littleweather.config.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class weatherapplication extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private List<item> list1;
    private RecycleView adapter;
    private List<item> list2;
    private List<item> list3;
    private List<item> list4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏：在此选择变透明的方式
        setstatusbar();

        setContentView(R.layout.activity_weatherapplication);
        //设置工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle实现了DrawerLayout.DrawerListener（我也不知道这三行代码什么意思!）
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //设置NavigationView的监听事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //找到swiperefresh控件和recyleview控件
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        list1 = new ArrayList<item>();
        list2 = new ArrayList<item>();
        list3 = new ArrayList<item>();
        list4 = new ArrayList<item>();
        //加载RecyelerView控件
        InitRecycleView();

        //加载SwipeRefreshLayout控件
        InitSwipeRefresh();
    }
        //设置下拉刷新
        public void InitSwipeRefresh()
        {
            //设置卷内的颜色
            mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            //设置下拉刷新监听
            mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    // TODO Auto-generated method stub
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            adapter.notifyDataSetChanged();
                            mSwipeRefreshWidget.setRefreshing(false);
                        }
                    },6000);
                }
            });
        }
        //设置RecycleView列表,瀑布流!
        public void InitRecycleView()
        {
            //设置layoutManager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            //设置adapter
            initData();
            adapter = new RecycleView(list1,list2,list3,list4);
            mRecyclerView.setAdapter(adapter);
            //设置item之间的间隔
            SpacesItemDecoration decoration=new SpacesItemDecoration(16);
            mRecyclerView.addItemDecoration(decoration);
        }
            //在此处将想要填充的数据填入bean中，并且将填充好的bean装入list中，以便给RecycleView.adapter用。
        public void initData()
        {
            for(int i=0;i<4;i++)
            {
                list1.add(new item());
                list2.add(new item());
                list3.add(new item());
                list4.add(new item());
            }
        }

    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weatherapplication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_city)
        {
            // Handle the camera action
        } else if (id == R.id.nav_set)
        {

        } else if (id == R.id.nav_about)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
