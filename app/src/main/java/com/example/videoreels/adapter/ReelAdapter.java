package com.example.videoreels.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoreels.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.PriorityTaskManager;

import java.util.ArrayList;
import java.util.List;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ViewHolder> {
    private Context reelAdapterContext;
    private final List<String> urls;
    private static final String TAG = "ReelAdapter";;

    public ReelAdapter(List<String> urls,Context reelAdapterContext) {
        this.reelAdapterContext = reelAdapterContext;
        this.urls = urls;
    }

    @NonNull
    @Override
    public ReelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reel_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReelAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:" + urls.size());
        String videoUrl = urls.get(position);
        Log.d(TAG,"videoUrl"+" "+videoUrl);
        holder.bind(videoUrl);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final StyledPlayerView reelPlayerView;
        private ExoPlayer exoPlayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reelPlayerView = itemView.findViewById(R.id.reelPlayer);
            //setting playerĀ
            reelPlayerView.setKeepContentOnPlayerReset(true);
            reelPlayerView.setShowBuffering(StyledPlayerView.SHOW_BUFFERING_ALWAYS);
            reelPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            reelPlayerView.setUseController(false);

        }

        public void bind(String videoUrl) {
                TrackSelector trackSelector = new DefaultTrackSelector(reelAdapterContext, new AdaptiveTrackSelection.Factory());
                exoPlayer = new ExoPlayer.Builder(reelAdapterContext)
                        .setTrackSelector(trackSelector)
                        .build();

                reelPlayerView.setPlayer(exoPlayer);
                exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl));
                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(true);
                exoPlayer.play();

        }

    }
}
