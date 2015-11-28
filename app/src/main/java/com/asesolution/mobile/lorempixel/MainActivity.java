package com.asesolution.mobile.lorempixel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.asesolution.mobile.lorempixel.favorites.fragments.FavoritesFragment;
import com.asesolution.mobile.lorempixel.gallery.fragments.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, FragmentContract.View {
    private static final String TAG = "MainActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    Adapter adapter;

    FragmentsPresenter userAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Create an adapter for the view pager
        adapter = new Adapter(getSupportFragmentManager());

        // Add fragments
        adapter.addFragment(new GalleryFragment(), "Gallery");
        adapter.addFragment(new FavoritesFragment(), "Favorites");

        // Add the adapter to the viewpager
        viewPager.setAdapter(adapter);

        // Add a change listener
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

        userAction = new FragmentsPresenter(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        userAction.switchFragment(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void displayFragment(int position) {
        ((FragmentContract.FragmentView) adapter.getItem(position)).displayFragment();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
