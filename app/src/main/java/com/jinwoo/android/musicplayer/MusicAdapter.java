package com.jinwoo.android.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JINWOO on 2017-02-01.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder>{

    List<Music> muData;
    Context context;
    Intent intent = null;

    public MusicAdapter(Context context){

        this.muData = DataLoader.get(context);
        this.context = context;
        intent= new Intent(context, PlayerActivity.class);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        Holder holder = new Holder(view);
        return holder;

    }




    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Music mu = muData.get(position);
        holder.txtTitle.setText(mu.title);
        holder.txtArtist.setText(mu.artist);
        holder.position = position;

        // if(mu.bitmap_image != null) {
        //    holder.imgView.setImageBitmap(mu.bitmap_image);
        //  }
        // holder.imgView.setImageURI(mu.album_image);
        //                  1. 로드할 대상 Uri    2. 입력될 이미지뷰
        Glide.with(context).load(mu.album_image).placeholder(android.R.drawable.ic_menu_close_clear_cancel).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return muData.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView txtTitle, txtArtist;
        CardView cardView;
        int position;

        public Holder(View view) {

            super(view);
            Log.d("Pick","Holder");
            imgView = (ImageView)view.findViewById(R.id.imgView);
            txtTitle = (TextView)view.findViewById(R.id.txtTitle);
            txtArtist = (TextView)view.findViewById(R.id.txtArtist);
            cardView = (CardView)view.findViewById(R.id.cardView);
            cardView.setOnClickListener(listener);

        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        };


    }

}
