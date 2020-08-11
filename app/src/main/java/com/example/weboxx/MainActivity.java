package com.example.weboxx;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<File> mySongs;
    String[] Songs;
    public Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        listView = (ListView) findViewById(R.id.lv);
        mySongs= findMusic(Environment.getExternalStorageDirectory());
        Songs= new String[mySongs.size()];
        for(int i=0;i<mySongs.size();i++)
        {
            toast(mySongs.get(i).toString());
            Songs[i]=mySongs.get(i).toString().replace(".mp3","");
        }

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.song_list,R.id.textView,Songs);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getApplicationContext(),PlayMusic.class).putExtra("Song",position).putExtra("SongList",mySongs));
            }
        });

    }

    public ArrayList<File> findMusic (File root)
    {
        ArrayList<File> arrayList = new ArrayList<File>();
       File[] files =root.listFiles();
       for(File singleFile:files){

           if(singleFile.isDirectory())
           {
               arrayList.addAll(findMusic(singleFile));
           }
           else
               if(singleFile.getName().endsWith(".mp3"))
               {
                   arrayList.add(singleFile);
               }
       }

        return arrayList;
    }

    public void toast(String SongName)
    {
        Toast.makeText(getApplicationContext(),SongName,Toast.LENGTH_SHORT).show();
    }
}
