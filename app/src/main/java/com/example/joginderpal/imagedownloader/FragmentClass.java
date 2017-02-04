package com.example.joginderpal.imagedownloader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentClass extends Fragment {
    List<String> li;
    List<String> li1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    public FragmentClass(){
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            recyclerView= (RecyclerView) rootView.findViewById(R.id.rvactivitymain);
            new doit().execute();
            return rootView;
        }
    public class doit extends AsyncTask<Void,Void,Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                li=new ArrayList<String>();
                li1=new ArrayList<String>();
                Document doc = Jsoup.connect("http://www.hdwallpapers.in/").get();
                Elements ul = doc.getElementsByTag("ul");
                for(Element e:ul) {
                    String class1 = e.attr("class");
                    if (class1.equals("side-panel categories")) {
                        Elements elements = e.getElementsByTag("li");
                        for (Element ele : elements) {

                            Elements elements1 = ele.getElementsByTag("a");
                            for (Element category : elements1) {
                                String links=category.attr("href");
                                li1.add(links);
                                String categories = category.text();
                                li.add(categories);


                            }}
                    }
                }

            }catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter=new RecyclerAdapter(li,li1,getActivity());
            recyclerView.setAdapter(adapter);
        }
    }
}
