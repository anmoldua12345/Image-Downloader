package com.example.joginderpal.imagedownloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 23-12-2016.
 */
public class second extends Activity {
    List<String> l1;
    List<String> l2;
    Bitmap bmp;
   // ListView listview;
   RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        recyclerView= (RecyclerView) findViewById(R.id.rvsecond);
      //  listview= (ListView) findViewById(R.id.listView2);
        new doit().execute();
    }

    public class doit extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(second.this);
            progressDialog.setMessage("Loading");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                l1=new ArrayList<String>();
                l2=new ArrayList<String>();
                String link=getIntent().getExtras().getString("link");
                Document doc= Jsoup.connect("http://www.hdwallpapers.in"+link).get();
                Elements elements=doc.getElementsByTag("ul");
                for (Element e1:elements) {
                    String a = e1.attr("class");
                    if (a.equals("wallpapers")) {
                        Elements el1=e1.getElementsByTag("li");
                        for (Element el2:el1){
                            Elements el3=el2.getElementsByTag("img");
                            for (Element el4:el3){
                                String image=el4.attr("src");
                                l1.add(image);

                            }
                            Elements e14=el2.getElementsByTag("a");
                            for (Element e15:e14){
                                String llink=e15.attr("href");
                                l2.add(llink);
                            }
                        }

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

           // InputStream in = new URL(IMAGE_URL).openStream();
          //  bmp = BitmapFactory.decodeStream(in);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            layoutManager=new LinearLayoutManager(second.this);
           recyclerView.setLayoutManager(layoutManager);
            adapter=new RecyclerAdapterone(l1,l2,second.this);
            recyclerView.setAdapter(adapter);


        }
    }
}
