package com.asesolution.mobile.lorempixel;

public interface FragmentContract {
    interface FragmentView {
        void displayFragment();
    }

    interface View {
        void displayFragment(int position);
    }

    interface UserAction {
        void switchFragment(int position);
    }
}
