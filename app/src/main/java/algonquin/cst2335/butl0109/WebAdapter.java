package algonquin.cst2335.butl0109;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.webViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private Context jContext;
    private ArrayList<WebInfo> jInfoList;
    private OnItemClickListener jListener;

    public void setOnItemClickListener(WebAdapter.OnItemClickListener listener) {
        jListener = listener;
    }
    public WebAdapter(Context context, ArrayList<WebInfo> infoList) {
        jContext = context;
        jInfoList = infoList;
    }

    @NonNull
    @Override
    public webViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(jContext).inflate(R.layout.recipe_search, parent, false);
        return new webViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull webViewHolder holder, int position) {
        WebInfo currentItem = jInfoList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String title = currentItem.getTitle();
        String id = currentItem.getIdInfo();

        holder.jTitle.setText(title);
        holder.jId.setText("Id: " + id);
        Picasso.with(jContext).load(imageUrl).fit().centerInside().into(holder.jImage);
    }

    @Override
    public int getItemCount() {
        return jInfoList.size();
    }



    public class webViewHolder extends RecyclerView.ViewHolder {
        public ImageView jImage;
        public TextView jTitle;
        public TextView jId;
        public webViewHolder(@NonNull View itemView) {
            super(itemView);
            jImage = itemView.findViewById(R.id.webImage);
            jTitle = itemView.findViewById(R.id.titleText);
            jId = itemView.findViewById(R.id.idText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jListener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            jListener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
