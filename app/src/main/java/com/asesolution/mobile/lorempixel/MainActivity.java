package com.asesolution.mobile.lorempixel;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.asesolution.mobile.lorempixel.favorites.FavoritesFragment;
import com.asesolution.mobile.lorempixel.gallery.GalleryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements FragmentContract.View {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    FragmentsPresenter userAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("OnCreate");
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        initializeViewPager();

        userAction = new FragmentsPresenter(this);

        Timber.d("OnCreate... complete");
    }

    private void initializeViewPager() {
        Timber.d("Initializing view pager");

        // Create/add the adapter to the viewpager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Add a change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                userAction.switchFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        Timber.d("Initializing view pager... complete");
    }

    @Override
    public void displayFragment(int position) {
        Timber.d("Displaying fragment: %d", position);

        Fragment fragment = ((ViewPagerAdapter) viewPager.getAdapter()).getItem(position);

        if (fragment == null) {
            Timber.d("Fragment is null");
        } else {
            Timber.d("Fragment is not null, calling displayFragment %s", fragment);
            // Since switching fragments via the viewpager may not call onResume/onStart we need
            // to manually
            fragment.onResume();
//            ((FragmentContract.FragmentView) fragment).on();
        }
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private static final int COUNT = MyFragmentManager.FragmentType.values().length;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Timber.d("ViewPagerAdapter.getItem %d", position);
            return MyFragmentManager.getFragment(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyFragmentManager.getTitle(position);
        }
    }

    static class MyFragmentManager {
        static Fragment getFragment(int number) {
            switch (FragmentType.values()[number]) {
                case FAVORITES:
                    return new FavoritesFragment();
                case GALLERY:
                    return new GalleryFragment();
            }

            return null;
        }

        static String getTitle(int number) {
            return FragmentType.values()[number].getTitle();
        }

        enum FragmentType {
            GALLERY("Gallery"),
            FAVORITES("Favorites");

            String title;

            FragmentType(String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }
        }
    }
}
