package com.example.mydeezer;

import android.annotation.SuppressLint;
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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    Context context;
    List<Artist> data;
    private OnItemClickListener listener;

    public ArtistAdapter(Context context, List<Artist> data) {
        this.context = context;
        this.data = data;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.artist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Artist artist=data.get(position);
        holder.tvName.setText("Artist : "+artist.getName());
        holder.textView2.setText("Fan : "+artist.getNb_fan());
        holder.textView3.setText("Album : "+artist.getNb_album());

        Picasso.get().load(artist.getPicture()).into(holder.imageView);
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
        private ImageView imageView;
        private TextView tvName;
        private TextView textView2;
        private TextView textView3;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            textView2 = (TextView) itemView.findViewById(R.id.textView2);
            textView3 = (TextView) itemView.findViewById(R.id.textView3);
        }
    }
}
