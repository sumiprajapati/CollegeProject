package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{
    SearchView searchView;
    String[] place={"Kathmandu","Bhaktapur","Lalitpur","Anamnagar","Sanga","Pokhara","Muktinath"};
    ArrayAdapter<String> adapter;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search,null);
        searchView=v.findViewById(R.id.searchView);
        lv=v.findViewById(R.id.listview);
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,place);
        searchView.setOnQueryTextListener(this);
        lv.setAdapter(adapter);
        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String text="";
        text=newText;
        adapter.getFilter().filter(text);
        return false;
    }
}
