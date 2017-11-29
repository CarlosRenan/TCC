package com.crdev.papaleguas.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crdev.papaleguas.R;

import java.util.ArrayList;

public class EmbreveFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> corridas;

    public EmbreveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        corridas = new ArrayList<>();
        corridas.add("Corrida da PM");
        corridas.add("Corrida do MP");
        corridas.add("Corrida da UECSA");
        corridas.add("Corrida da Selva");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_embreve, container, false);
        listView = view.findViewById(R.id.id_lvempreve);
        adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                corridas
        );
        listView.setAdapter(adapter);



        return view;
    }

}
