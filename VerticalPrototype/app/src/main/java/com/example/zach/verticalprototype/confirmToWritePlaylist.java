package com.example.zach.verticalprototype;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class confirmToWritePlaylist extends AppCompatActivity {
    int pID;
    private ListView confirmPlaylists;
    private ArrayList<Song> songList;
    Long pIdLong = Long.valueOf(pID);
    private ArrayList<String> pathList;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/playlistplacer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_to_write_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String sID = getIntent().getStringExtra("playlist_id_from_spinner");
        //Toast.makeText(getApplicationContext(), "Furthermore, the id is '" + text + "'", Toast.LENGTH_SHORT).show();
        pID = Integer.parseInt(sID);
        //Toast.makeText(getApplicationContext(), "Furthermore, the id is, as an integer, '" + pID + "'", Toast.LENGTH_SHORT).show();
        confirmPlaylists = (ListView) findViewById(R.id.confirmPlaylists);
        songList = new ArrayList<Song>();
        pathList = new ArrayList<String>();
        getSongList();

        /*Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });*/
        SongAdapter songAdt = new SongAdapter(this, songList);
        confirmPlaylists.setAdapter(songAdt);
    }

    public void getSongList(){
        String[] proj = {//MediaStore.Audio.Playlists.Members.AUDIO_ID,
                MediaStore.Audio.Playlists.Members._ID,
                MediaStore.Audio.Playlists.Members.TITLE,
                MediaStore.Audio.Playlists.Members.ARTIST,
                MediaStore.Audio.Playlists.Members.DATA


        };
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Playlists.Members.getContentUri("external", pID);
        Cursor musicCursor = musicResolver.query(musicUri, proj, null, null, null);
        System.out.println(">>>>>Going to gather info; how many rows?" + (musicCursor.getCount()));

        if (musicCursor!=null && musicCursor.moveToFirst()){
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int filePath = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            System.out.println(">>>>>Printing Song Titles");
            System.out.println(">>>>>Printing Song Paths");

            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisPath = musicCursor.getString(filePath);
                System.out.println(">>>>>" + thisTitle);
                songList.add(new Song(thisId, thisTitle, thisArtist));
                System.out.println(">>>>>" + thisPath);
                pathList.add(thisPath);
            }
            while (musicCursor.moveToNext());
        }
    }

    public void commitFile(View view) {
        // add-write text into file
        String pendingWrite;
        String givenPath;

        try {
            //givenPath = "";
            File playlistResult = new File(path + "/CreatedPlaylist.txt");
            playlistResult.createNewFile();
            FileOutputStream outfile = new FileOutputStream(playlistResult);
            OutputStreamWriter outwriter = new OutputStreamWriter(outfile);
            outwriter.append("#EXTM3U" + "\n");
            for (int i = 0; i < pathList.size(); i++) {
                givenPath = pathList.get(i);
                pendingWrite = givenPath.substring(givenPath.lastIndexOf('/') + 1);
                System.out.println(">>>>>pendingWrite has the value:  " + pendingWrite);
                outwriter.append(pendingWrite + "\n");
                }
            outwriter.close();
            outfile.close();
            }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Saved .m3u file to location:  " + path + "." + "\n" + "Please move this file into your Music folder.", Toast.LENGTH_LONG).show();

    }
}
