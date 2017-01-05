package com.example.joginderpal.imagedownloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by joginderpal on 03-01-2017.
 */
public class RecyclerAdapterone extends RecyclerView.Adapter<RecyclerAdapterone.ViewHolder> {
 //   int j=0;
    List<String> l1,l2;
    Context ctx;

    public RecyclerAdapterone(List<String> l1, List<String> l2, Context ctx) {

        this.l1=l1;
        this.l2=l2;
        this.ctx=ctx;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public ImageView imageview;

        public ViewHolder(View itemView) {
            super(itemView);
            imageview= (ImageView) itemView.findViewById(R.id.imageone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent intent=new Intent(ctx,Third.class);
                    intent.putExtra("image",l1.get(position));
                    intent.putExtra("linking",l2.get(position));
                    ctx.startActivity(intent);
                }
            });
        }
    }


    @Override
    public RecyclerAdapterone.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout1,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (RecyclerAdapterone.ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(ctx).load("http://www.hdwallpapers.in"+l1.get(position)).into(holder.imageview);
        Animation animation= AnimationUtils.loadAnimation(ctx,R.anim.bounce);
        holder.itemView.startAnimation(animation);

    }


    @Override
    public int getItemCount() {
        return l1.size();
    }
  /*  public Bitmap getImage(String url) throws IOException {

        Bitmap bmp;
        InputStream in = new URL("http://www.hdwallpapers.in/thumbs/2016/paintwave_yellow-t1.jpg").openStream();
        bmp = BitmapFactory.decodeStream(in);
        return bmp;

    }*/


    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "http://www.hdwallpapers.in/thumbs/2016/paintwave_yellow-t1.jpg");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
