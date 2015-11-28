package com.asesolution.mobile.lorempixel;

public class FragmentsPresenter implements FragmentContract.UserAction {
    private FragmentContract.View mainView;

    public FragmentsPresenter(FragmentContract.View mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchFragment(int position) {
        mainView.displayFragment(position);
    }
}
