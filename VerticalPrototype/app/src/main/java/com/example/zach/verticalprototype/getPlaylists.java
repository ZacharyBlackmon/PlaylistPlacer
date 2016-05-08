package com.example.zach.verticalprototype;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class getPlaylists extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public View view;
    public Spinner playlists;
    public ArrayList<String> listOfPlaylists;
    public ArrayList<String> listOfPlaylistIDs;
    public Button confirmButton;
    public String actualID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_playlists);
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

        confirmButton = (Button) findViewById(R.id.confirmButton);

        listOfPlaylists = getAllPlaylistNames(view);
        listOfPlaylistIDs = getAllPlaylistIDs(view);
        playlists = (Spinner) findViewById(R.id.playlists);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfPlaylists);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        playlists.setAdapter(aa);
        playlists.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        //intent, pass 'id' of playlist to use
        Toast.makeText(getApplicationContext(), "Id:  " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void confirmChoice(View view){
        Intent intent = new Intent(this, confirmToWritePlaylist.class);
        int idValue = playlists.getSelectedItemPosition();
        String idValue2 = listOfPlaylistIDs.get(idValue);
        intent.putExtra("playlist_id_from_spinner", idValue2);
        //Toast.makeText(getApplicationContext(), "Selected is:  " + playlists.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public ArrayList<String> getAllPlaylistNames(View view){
        ArrayList<String> arrayListForPlaylists = new ArrayList<String>();

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        Cursor playlistCursor = this.managedQuery(tempPlaylistURI, proj, null, null, null);

        if(playlistCursor == null) {
            System.out.println("No playlists found");
            return arrayListForPlaylists; //No playlists on the phone.
        }

        System.gc();

        String playlistName = null;

        System.out.println(">>>Creating and displaying list of all created playlists");
        for (int i = 0; i <playlistCursor.getCount(); i++) {
            playlistCursor.moveToPosition(i);
            playlistName = playlistCursor.getString(playlistCursor.getColumnIndex("name"));
            System.out.println("> " + i + " : " + playlistName);
            arrayListForPlaylists.add(playlistName);
        }

        if (playlistCursor != null)
            playlistCursor.close();

        return arrayListForPlaylists;
    }

    public ArrayList<String> getAllPlaylistIDs(View view){
        ArrayList<String> arrayListForPlaylistIDs = new ArrayList<String>();

        String[] proj = {"*"};
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        Cursor playlistCursor = this.managedQuery(tempPlaylistURI, proj, null, null, null);

        if(playlistCursor == null) {
            System.out.println("No playlists found");
            return arrayListForPlaylistIDs; //No playlists on the phone.
        }

        System.gc();

        String playlistID = null;

        System.out.println(">>>Creating and displaying list of all created playlist IDs");
        for (int i = 0; i <playlistCursor.getCount(); i++) {
            playlistCursor.moveToPosition(i);
            playlistID = playlistCursor.getString(playlistCursor.getColumnIndex("_id"));
            System.out.println("> " + i + " : " + playlistID);
            arrayListForPlaylistIDs.add(playlistID);
        }

        if (playlistCursor != null)
            playlistCursor.close();

        return arrayListForPlaylistIDs;
    }
}
