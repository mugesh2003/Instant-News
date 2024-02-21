package com.example.instantnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsrecyclerAdapter extends RecyclerView.Adapter<NewsrecyclerAdapter.NewsViewHolder> {
    List<Article> articleList;
    NewsrecyclerAdapter(List<Article>articleList){
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
      Article article = articleList.get(position);
      holder.titletextview.setText(article.getTitle());
      holder.sourcetextview.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.baseline_hide_image_24)
                .placeholder(R.drawable.baseline_hide_image_24)
                .into(holder.imageView);

    }
    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView titletextview,sourcetextview;
        ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titletextview = itemView.findViewById(R.id.article_title);
            sourcetextview = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);

        }
    }
}
