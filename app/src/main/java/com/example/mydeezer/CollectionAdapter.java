package com.example.mydeezer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    Context context;
    List<Song> data;
    private ArtistAdapter.OnItemClickListener listener;

    public CollectionAdapter(Context context, List<Song> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.ViewHolder holder, int position) {
        Song song=data.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvAlbumTitle.setText(song.getAlbumTitle());
        holder.tvDuration.setText(song.getDuration());
        Picasso.get().load(song.getCover()).into(holder.imgAlbum);
        holder.imageView2.setImageResource(android.R.drawable.star_on);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is  "+(position+1) +" item !", Snackbar.LENGTH_SHORT).show();

                if (listener != null) {
                    listener.onItemClick(position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAlbum;
        private TextView tvTitle;
        private TextView tvAlbumTitle;
        private TextView tvDuration;
        private ImageView imageView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = (ImageView) itemView.findViewById(R.id.img_album);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvAlbumTitle = (TextView) itemView.findViewById(R.id.tv_album_title);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
    public void setOnItemClickListener(ArtistAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
