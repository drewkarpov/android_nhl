package com.example.nhl;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhl.model.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoItem> videoItems;
    private Context context;


    public VideoAdapter(List<VideoItem> videoItems, Context context) {
        this.videoItems = videoItems;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_video, viewGroup, false);
        return new VideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem videoItem = videoItems.get(position);
        Picasso.get().load(videoItem.getPrewiewUrl()).into(holder.prewiewImage);

        try{
            String link = videoItem.getVideoUrl();
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            Uri video = Uri.parse(link);
            holder.videoView.setMediaController(mediaController);
        }
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

            VideoView videoView;
            ImageView prewiewImage;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            prewiewImage = itemView.findViewById(R.id.previewImage);
        }
    }
}

