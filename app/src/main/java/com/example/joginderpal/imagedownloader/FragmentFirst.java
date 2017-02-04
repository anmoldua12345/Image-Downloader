package com.example.joginderpal.imagedownloader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentFirst extends Fragment implements View.OnClickListener {
    Button btn;
    public FragmentFirst(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        btn = (Button) rootView.findViewById(R.id.button);
        btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),MainActivity.class));
    }
}
