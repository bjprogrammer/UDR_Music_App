package com.example.bobby.udr;

import android.support.v4.app.FragmentPagerAdapter;


public class TestPagerAdapter extends FragmentPagerAdapter

    {

        public TestPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return new video();
                case 1:
                    // Games fragment activity
                    return new music();
                case 2:
                    // Movies fragment activity
                    return new radio();
                case 3:

                    return new news();
                case 4:

                    return new ondemand();
            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 5;
        }

    }

