package com.example.mydeezer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private RecyclerView rvCollection;
    List<Song> list=new ArrayList<>();
    CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
    }

    private void initView() {
        rvCollection = (RecyclerView) findViewById(R.id.rv_collection);
        adapter=new CollectionAdapter(this,list);
        rvCollection.setAdapter(adapter);
        rvCollection.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new ArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                show(position);
            }
        });
        initData();

    }
    @SuppressLint("Range")
    public void initData(){
        SQLiteDatabase db = new SQHelper(this).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from "+ SQHelper.TABLE_NAME_SONG,null);
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String duration = cursor.getString(cursor.getColumnIndex("duration"));
            String album_title = cursor.getString(cursor.getColumnIndex("album_title"));
            String cover = cursor.getString(cursor.getColumnIndex("cover"));
            int collection = cursor.getInt(cursor.getColumnIndex("collection"));
            Song song=new Song(id,title,duration,album_title,cover,collection);
            list.add(song);

        }
        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();

    }
    public void show(int position){
        Song song = list.get(position);
        new AlertDialog.Builder(this)
                .setTitle(R.string.msg_title)
                .setMessage(R.string.del_message)
                .setPositiveButton(
                        R.string.msg_confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //delete location
                                delete(song.getId());
                                list.remove(position);
                                adapter.notifyItemChanged(position);
                                Toast.makeText(getApplicationContext(), "Delete Successful!", Toast.LENGTH_SHORT).show();

                            }
                        }

                )

                .show();
    }
    public void delete(String id){
// 获取可写数据库
        SQLiteDatabase db = new SQHelper(this).getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{id}; // 请替换为你要删除的任务的标识符

// 执行删除操作
        int i= db.delete(SQHelper.TABLE_NAME_SONG, whereClause, whereArgs);

    }
}