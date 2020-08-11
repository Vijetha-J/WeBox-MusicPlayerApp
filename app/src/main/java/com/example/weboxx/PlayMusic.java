package com.example.weboxx;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity implements View.OnClickListener {

    ImageButton btprev,btnext,btplay,btff,btrev;
    MediaPlayer mediaPlayer;
    ArrayList<File> arrayList;
    SeekBar seekBar;
    Uri uri;
    int position;
    Thread thread;
    TextView tvSongName;
    Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);



        btnext=(ImageButton)findViewById(R.id.idNext);
        btplay=(ImageButton)findViewById(R.id.idPlay);
        btprev=(ImageButton)findViewById(R.id.idPrev);
        btff=(ImageButton)findViewById(R.id.idff);
        btrev=(ImageButton)findViewById(R.id.idrev);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        tvSongName=(TextView)findViewById(R.id.idSongName);
        thread=new Thread(){
            @Override
            public void run() {
                int totalDuration=mediaPlayer.getDuration();
                int currentDuration=0;
                while(currentDuration<totalDuration){
                    try {
                        sleep(500);
                        currentDuration=mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };


        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        arrayList= (ArrayList)bundle.getParcelableArrayList("SongList");
        position= bundle.getInt("Song",0);
        uri=Uri.parse(arrayList.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        tvSongName.setText(arrayList.get(position).toString().replace("/storage/emulated/0/Download/",""));
        mediaPlayer.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        btplay.setOnClickListener(this);
        btprev.setOnClickListener(this);
        btnext.setOnClickListener(this);
        btff.setOnClickListener(this);
        btrev.setOnClickListener(this);
        seekBar.setMax(mediaPlayer.getDuration());
        thread.start();


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

         switch (id){
             case R.id.idPlay:
                 if(mediaPlayer.isPlaying()){
                     mediaPlayer.pause();
                     btplay.setImageResource(android.R.drawable.ic_media_play);

                 }
                 else {
                     mediaPlayer.start();
                     btplay.setImageResource(android.R.drawable.ic_media_pause);
                 }
                 break;

             case R.id.idNext:
                 mediaPlayer.stop();
                 mediaPlayer.release();
                 position = (position+1)%arrayList.size();
                 uri= Uri.parse(arrayList.get(position).toString());
                 mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                 mediaPlayer.start();
                 seekBar.setMax(mediaPlayer.getDuration());
                 Toast.makeText(PlayMusic.this,">|",Toast.LENGTH_SHORT).show();
                 break;

             case R.id.idPrev:
                 mediaPlayer.stop();
                 mediaPlayer.release();
                 position = (position-1<0)?arrayList.size()-1:position-1;
                 uri= Uri.parse(arrayList.get(position).toString());
                 mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                 mediaPlayer.start();
                 seekBar.setMax(mediaPlayer.getDuration());
                 Toast.makeText(PlayMusic.this,"|<",Toast.LENGTH_SHORT).show();
                 break;

             case  R.id.idff:
                 mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                 break;

             case  R.id.idrev:
                 mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                 break;

         }
    }
}
