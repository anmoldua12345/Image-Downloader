package com.example.joginderpal.imagedownloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 23-12-2016.
 */
public class Third extends Activity {
    List<String> ls1;
    TextView tx;
    Button b1;
    List<String> ls2;
   ImageView img;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
       // TextView tx = (TextView) findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.button);
       // tx.setText(a);
        img= (ImageView) findViewById(R.id.imageView);
        String linkimage=getIntent().getExtras().getString("image");
        Picasso.with(Third.this).load("http://www.hdwallpapers.in"+linkimage).into(img);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     new doit().execute();

            }
        });
    }

    public class doit extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String a = getIntent().getExtras().getString("linking");
                ls1 = new ArrayList<String>();
                ls2=new ArrayList<String>();
                Document document = Jsoup.connect("http://www.hdwallpapers.in"+a).get();
                Elements elements = document.getElementsByTag("div");
                for (Element el1 : elements) {
                    String resolution = el1.attr("class");
                    if (resolution.equals("wallpaper-resolutions")){
                        Elements elements1=el1.getElementsByTag("a");
                        for (Element e2:elements1){
                            String re=e2.attr("title");
                            String re1=e2.attr("href");
                            ls1.add(re);
                            ls2.add(re1);
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
            String[] array = new String[ls1.size()];
            for (int i = 0; i < ls1.size(); i++) {
                array[i] = ls1.get(i);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Third.this);
            builder.setTitle("Resolution")
                    .setItems(array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            for (int i=0;i<ls2.size();i++){
                                if (which==i){
                                    new download().execute(ls2.get(which));
                                    //Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();

                                }
                            }

                        }
                    });

            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }


    public class download extends AsyncTask<String,Integer,Void>{


        @Override
        protected Void doInBackground(String... voids) {


            String url="http://www.hdwallpapers.in"+voids[0];
            try {
                URL myurl=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) myurl.openConnection();
                connection.setDoOutput(true);
                int file_length=connection.getContentLength();
                connection.setRequestMethod("GET");
                connection.connect();
                File rootDirectory=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"My Pictures");
                if (!rootDirectory.exists()){
                    rootDirectory.mkdir();
                }

                String nameOfFile=URLUtil.guessFileName(url,null,
                        MimeTypeMap.getFileExtensionFromUrl(url));
                File file=new File(rootDirectory,nameOfFile);
                file.createNewFile();

                InputStream inputStream=connection.getInputStream();
                FileOutputStream output=new FileOutputStream(file);
                byte[] buffer=new byte[1024];
                int byteCount=0;
                int total=0;
                while((byteCount=inputStream.read(buffer))>0){
                    total+=byteCount;
                    output.write(buffer,0,byteCount);
                    int progress=total*100/file_length;
                    publishProgress(progress);
                }
                output.close();

                Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                getApplicationContext().sendBroadcast(intent);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),"Done ...",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Third.this);
            progressDialog.setTitle("Image is downloading......");
            progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(0);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
    }



}