package com.example.android.photography;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.android.photography.fragments.MainFragment;

class ActivityUtils {

    static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        if (!(fragment instanceof MainFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    static void replaceFragmentInActivity(FragmentManager fragmentManager,
                                          Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        if (!(fragment instanceof MainFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    static void removeFragmentFromActivity(FragmentManager fragmentManager,
                                           Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
}
