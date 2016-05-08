package com.example.zach.verticalprototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {
    //Set up variables to start
    public EditText metadataTextField1;
    public EditText metadataTextField2;
    public Button createM3Ubutton;
    public Button helpButton;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/playlistplacer";
    public Button gotoPlaylistsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create References to objects
        metadataTextField1 = (EditText) findViewById(R.id.metadataTextField1);
        metadataTextField2 = (EditText) findViewById(R.id.metadataTextField2);
        createM3Ubutton = (Button) findViewById(R.id.createM3Ubutton);
        helpButton = (Button) findViewById(R.id.helpButton);
        gotoPlaylistsButton = (Button) findViewById(R.id.gotoPlaylistsButton);

        //Create directory for file; dir.mkdirs creates all necessary folders
        File dir = new File(path);
        dir.mkdirs();

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
    }

    public void displayHelpDialog(View view){
        new AlertDialog.Builder(this)
                .setTitle("How To Format Data")

                .setMessage("For testing purposes, you can create a playlist from two songs, provided that you know their file names." + "\n" + "\n"
                + "Otherwise, if you're ready to prepare your Android Playlists for a destination computer, click on 'Go To Playlists'." + "\n" + "\n"
                + "The playlists created will  appear on your phone as 'CreatedPlaylist.m3u' in the 'playlistplacer' directory in Android." + "\n" + "\n"
                + "Upon restart, the phone will detect this playlist and add it to your mediaplayer's playlists.")
                .show();
    }

    public void buttonCreateFile(View view){
        File testfile = new File (path + "/savedFile.txt");
        String area1 = metadataTextField1.getText().toString();
        String area2 = metadataTextField2.getText().toString();

        StringBuilder resolveFinal = new StringBuilder();
        resolveFinal.append("#EXTM3U" + "\n" + area1 + "\n");
        resolveFinal.append(area2 + "\n");

        String resolveFinal2 = resolveFinal.toString();


        Save(resolveFinal2);

        //metadataTextField.setText("");
        Toast.makeText(getApplicationContext(), "Saved .m3u file to location:  " + path + "." + "\n" + "Please move this file into your Music folder.", Toast.LENGTH_LONG).show();
    }

    public void Save(String preparedPlaylist) {
        // add-write text into file
        try {
            File playlistResult = new File(path + "/CreatedPlaylist.m3u");
            playlistResult.createNewFile();
            FileOutputStream outfile = new FileOutputStream(playlistResult);
            OutputStreamWriter outwriter = new OutputStreamWriter(outfile);
            outwriter.append(preparedPlaylist);
            outwriter.close();
            outfile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToGetPlaylists(View view) {
        Intent intent = new Intent(this, getPlaylists.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
