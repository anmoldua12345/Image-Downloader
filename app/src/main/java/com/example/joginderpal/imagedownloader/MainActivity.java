package com.example.joginderpal.imagedownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> li;
    List<String> li1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
   // ListView ls;
    public static final String EXTRA_YOUR_KEY = "Ammy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //  ls= (ListView) findViewById(R.id.listView);
        recyclerView= (RecyclerView) findViewById(R.id.rvactivitymain);
        new doit().execute();
    }
    public class doit extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
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
          //  ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,li);
          //  ls.setAdapter(arrayAdapter);
           // ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             /*   @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   for(int a=0;a<28;a++){
                       if (i==a){
                           Intent intent=new Intent(MainActivity.this,second.class);
                           intent.putExtra("link",li1.get(a));
                           startActivity(intent);
                       }
                   }

                }
            });*/

            layoutManager=new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            adapter=new RecyclerAdapter(li,li1,MainActivity.this);
             recyclerView.setAdapter(adapter);

        }
    }
}
