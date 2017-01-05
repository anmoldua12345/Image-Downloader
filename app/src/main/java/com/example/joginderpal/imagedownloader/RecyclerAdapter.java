package com.example.joginderpal.imagedownloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joginderpal on 03-01-2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    int j=0;
    List<String> li1,li;
  Context ctx;
    public RecyclerAdapter(List<String> li, List<String> li1,Context ctx) {

        this.li=li;
        this.li1=li1;
        this.ctx=ctx;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private int currentitem;
        public TextView itemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle= (TextView) itemView.findViewById(R.id.tx);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent i=new Intent(ctx,second.class);
                    i.putExtra("link",li1.get(position));
                    ctx.startActivity(i);

                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        RecyclerView.ViewHolder v=new ViewHolder(view);



        return (ViewHolder) v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(li.get(position));
        for (int i=1;i<li.size();i++) {
            if (j == 0) {
                holder.itemView.setBackgroundColor(Color.CYAN);
                j=1;
            }
            else if (j==1){
                holder.itemView.setBackgroundColor(Color.GREEN);
                j=0;
            }
        }
        Animation animation= AnimationUtils.loadAnimation(ctx,R.anim.bounce1);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return li.size();
    }
}
