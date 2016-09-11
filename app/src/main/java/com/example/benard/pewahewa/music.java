package com.example.benard.pewahewa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.benard.pewahewa.R.layout.activity_music;

public class music extends AppCompatActivity {
    ListView lv;
    String[] items;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_music);
        lv = (ListView) findViewById(R.id.listView);
        // int position=0;
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];
        // if (items.length == 0) {
        // try {
        //throw new IllegalArgumentException("No mp3 Found");

        for (int i = 0; i < mySongs.size(); i++) {
            // toast(mySongs.get(i).getName().toString());
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");

        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_play_list, R.id.textView, items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //passing seleceted music to player class
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getApplicationContext(), player.class).putExtra("pos", position).putExtra("songlist", mySongs));

            }
        });
    }

//
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    public ArrayList<File> findSongs(File root) {
        try {
            ArrayList<File> al = new ArrayList<File>();
            File[] files = root.listFiles();
            for (File singlefile : files) {
                if (singlefile.isDirectory() && !singlefile.isHidden())
                    al.addAll(findSongs(singlefile));
                else {
                    if (singlefile.getName().endsWith(".mp3")) {
                        final boolean add = al.add(singlefile);
                    }

                }
            }
            return al;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }



    }

