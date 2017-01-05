package com.example.joginderpal.imagedownloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 26-12-2016.
 */
public class Try extends AppCompatActivity {

    //  TextView ls;
    ListView ls1;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tryout);
        ls1= (ListView) findViewById(R.id.ls1);
        new doIt().execute();

    }

    public class doIt extends AsyncTask<Void,Void,Void> {

        //  String line="";

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                list=new ArrayList<String>();
                Document doc = Jsoup.connect("http://164.100.158.135/ExamResults/ExamResultsmain.htm").get();
                Elements tBody=doc.getElementsByTag("tbody");
                for (Element td: tBody){
                    Elements td1=td.getElementsByTag("td");

                    for(Element a:td1) {
                        Elements a1 = a.getElementsByTag("a");
                        for (Element tex : a1) {
                            String words = tex.text();
                            //  line+=words;
                            list.add(words);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Try.this,android.R.layout.simple_list_item_1,list);
            ls1.setAdapter(arrayAdapter);
            //  ls.setText(line);

        }
    }
}
