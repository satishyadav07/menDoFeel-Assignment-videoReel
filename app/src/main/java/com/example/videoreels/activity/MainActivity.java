package com.example.videoreels.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.videoreels.R;
import com.example.videoreels.adapter.ReelAdapter;
import com.example.videoreels.api.RetrofitClient;
import com.example.videoreels.api.apiInterface;
import com.example.videoreels.model.videoreels.Hit;
import com.example.videoreels.model.videoreels.Large;
import com.example.videoreels.model.videoreels.Tiny;
import com.example.videoreels.model.videoreels.VideoModel;
import com.example.videoreels.model.videoreels.Videos;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    List<String> urlList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private List<MediaItem> streamURls =new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        prepareReelData();

    }


    private void prepareReelData() {

        Call<VideoModel> videoModelCall = RetrofitClient
                .getClient(getApplicationContext())
                .create(apiInterface.class)
                .getReelData("35854377-4cdf7e1ad04fb2bf8c898184a","yellow flowers");
                 videoModelCall.enqueue(new Callback<VideoModel>() {
                    @Override
                    public void onResponse(@NonNull Call<VideoModel> call, @NonNull Response<VideoModel> response) {


                        Log.d(TAG, "onResponse: "+response.code());
                        VideoModel videoModel = response.body();

                        if (videoModel != null) {
                            List<Hit> hitList = videoModel.getHits();
                            for(int i=0; i< hitList.size();i++){
                                Videos videos = hitList.get(i).getVideos();
                                Tiny tiny = videos.getTiny();
                                urlList.add(tiny.getUrl());
                                ReelAdapter reelAdapter = new ReelAdapter(urlList,getApplicationContext());
                                recyclerView.setAdapter(reelAdapter);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<VideoModel> call, Throwable t) {

                    }
                });


    }


}