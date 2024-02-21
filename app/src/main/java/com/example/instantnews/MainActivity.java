package com.example.instantnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsrecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        setupRecyclerView();
        getNews();
    }

    void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsrecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show){
        if(show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews(){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("85efef7b593342e1a96fe39d46dac9de");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                       runOnUiThread(()->{
                           changeInProgress(false);
                           articleList = response.getArticles();
                           adapter.updateData(articleList);
                           adapter.notifyDataSetChanged();
                       });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("GOT Failure",throwable.getMessage());

                    }
                }
        );
    }
}