package com.crdev.papaleguas.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.crdev.papaleguas.fragment.EmbreveFragment;
import com.crdev.papaleguas.fragment.PerfilFragment;
import com.crdev.papaleguas.fragment.RecentesFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"Em breve", "Recentes", "Perfil"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new EmbreveFragment();
                break;
            case 1:
                fragment = new RecentesFragment();
                break;
            case 2:
                fragment = new PerfilFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
