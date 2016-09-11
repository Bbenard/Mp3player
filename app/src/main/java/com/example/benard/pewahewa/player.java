package com.example.benard.pewahewa;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

import static android.net.Uri.*;

public class player extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mp;
    ArrayList<File> mysongs;
    SeekBar sb;
    Uri u;
    int position;
    Button btplay,btff,btfw ,btnxt,btpv,btfb;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btplay=(Button) findViewById(R.id.bt_play);
        btff=(Button)(Button)findViewById(R.id.btfb) ;
        //=(Button) findViewById(R.id.btfb);
        btnxt=(Button) findViewById(R.id.btnxt);
        btpv=(Button) findViewById(R.id.btprv);
        //btfb=(Button)findViewById(R.id.btfb) ;
        btfw=(Button)findViewById(R.id.bt_ffw) ;
        btff.setOnClickListener(this);
        btfw.setOnClickListener(this);
        btnxt.setOnClickListener(this);
        btpv.setOnClickListener(this);
        btplay.setOnClickListener(this);

        sb=(SeekBar)findViewById(R.id.seek_Bar);
        updateSeekBar=new Thread(){
            @Override
            public void run() {
                //super.run();
                int totalDuration=mp.getDuration();
                int currentPosition=0;
                sb.setMax(totalDuration);
                while(currentPosition<totalDuration){
                    try {
                        sleep(5000);
                        currentPosition=mp.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        if(mp!=null){
            mp.stop();
            mp.release();
        }

        Intent i= getIntent();
        Bundle b=i.getExtras();
        mysongs=(ArrayList)b.getParcelableArrayList("songlist");
        position =b.getInt("pos",0);
         u = parse(mysongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        sb.setMax(mp.getDuration());
        updateSeekBar.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());

            }
        });
    }
    @Override
    public void onClick(View view) {
        int id =view.getId();
        switch (id){

            case R.id.bt_play:
                if(mp.isPlaying()) {
                    //to pause click on >
                    btplay.setText(">");
                    mp.pause();
                }
                else {
                    //to  replay click 'II'after pause
                    btplay.setText("II");
                    mp.start();
                }
                break;
            //forward
            case  R.id.btfb:
                position=(position+1)%mysongs.size();
                mp.seekTo(mp.getCurrentPosition()-50);
                //mp.seekTo();
                break;
//            case  R.id.bt_ffw:
//                position=(position+1)%mysongs.size();
//                mp.seekTo(mp.getCurrentPosition()+5);
//                //mp.seekTo();
//                break;
            case R.id.btnxt:
                mp.stop();
                mp.release();
                position=(position+1)%mysongs.size();
                Uri u = parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;
            case R.id.btprv:
                mp.stop();
                mp.release();
                position=(position-1)%mysongs.size();
                 u = parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;
            case R.id.bt_ffw:
                mp.stop();
                mp.release();
                position=(position-2)%mysongs.size();
                u = parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                break;
            default:
                mp.stop();
                mp.release();
                position=(position+2)%mysongs.size();
                u = parse(mysongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
        }

    }
}
