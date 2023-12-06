package com.example.mydeezer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    Context context;
    List<Song> data;

    public DetailsAdapter(Context context, List<Song> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song=data.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvAlbumTitle.setText(song.getAlbumTitle());
        holder.tvDuration.setText(song.getDuration());
        Picasso.get().load(song.getCover()).into(holder.imgAlbum);
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageView2.setImageResource(android.R.drawable.star_on);
                Toast.makeText(context.getApplicationContext(), "collection successful!",Toast.LENGTH_SHORT).show();
                add(song);
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
    public void add(Song song){
        SQLiteDatabase db = new SQHelper(context.getApplicationContext()).getWritableDatabase();
        db.execSQL("insert into "+SQHelper.TABLE_NAME_SONG+"(id,title,duration,album_title,cover,collection) values(?,?,?,?,?,?)",
                new Object[]{song.getId(),song.getTitle(),song.getDuration(),song.getAlbumTitle(),song.getCover(),1});
    }
}

