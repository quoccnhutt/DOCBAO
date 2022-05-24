package com.example.bohay;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bohay.model.TinTuc;


import java.io.Serializable;
import java.util.ArrayList;

public class TinTucAdapter extends RecyclerView.Adapter<TinTucAdapter.TintucHolder> {
    private Activity activity;
    private ArrayList<TinTuc> tinTucArrayList;

    public TinTucAdapter(Activity activity, ArrayList<TinTuc> tinTucArrayList) {
        this.activity = activity;
        this.tinTucArrayList = tinTucArrayList;
    }

    @NonNull
    @Override
    public TintucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_tin_tuc,parent,false);
        return new TintucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TintucHolder holder, int position) {
         final TinTuc tinTuc = tinTucArrayList.get(position);
        holder.tvTieude.setText(tinTucArrayList.get(position).getTieude());

        Glide.with(activity)
            .load(tinTuc.getAnhminhhoa())
            .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(android.R.anim.fade_in)
                .approximate()
                .into(holder.imgMH);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = tinTuc.getId_tin();
                Toast.makeText(activity, tinTuc.getId_tin()+ "ok", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(activity, DetailsNewsActivity.class);
//                intent.putExtra("idtin", id);
//                activity.startActivity(intent);

//                activity.startActivity(new Intent(activity,DetailsNewsActivity.class).putExtra("tintuc", (Serializable) tinTuc));
            }
        });

    }

//    final Article article = listAticle.get(position);
//        holder.tvTitle.setText(article.getTitle());
//        Glide.with(activity)
//            .load(article.getThumnail())
//            .asBitmap()
//                .atMost()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .animate(android.R.anim.fade_in)
//                .approximate()
//                .into(holder.imgThumnal);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            activity.startActivity(new Intent(activity,DetailArticleActivity.class).putExtra("Article",article));
//        }
//    });













    @Override
    public int getItemCount() {
        return tinTucArrayList.size();
    }


    public class TintucHolder extends RecyclerView.ViewHolder  {

        private ImageView imgMH;
        private TextView tvTieude;

        public TintucHolder(@NonNull View itemView) {
            super(itemView);

            imgMH = itemView.findViewById(R.id.imgMH);
            tvTieude = itemView.findViewById(R.id.tv_tieude);

//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);

        }

//        public void setItemClickListener(ItemClickListener itemClickListener)
//        {
//            this.itemClickListener = itemClickListener;
//        }
//
//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),false);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),true);
//            return true;
//        }
    }
}