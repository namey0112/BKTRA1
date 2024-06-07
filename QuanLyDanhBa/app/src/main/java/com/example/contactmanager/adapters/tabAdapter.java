package com.example.contactmanager.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.contactmanager.fragments.dvManager;
import com.example.contactmanager.fragments.staffManager;

public class tabAdapter extends FragmentStateAdapter {

    public tabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public tabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new staffManager();
        }
        return new dvManager();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
