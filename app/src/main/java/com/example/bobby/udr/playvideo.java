package com.example.bobby.udr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;


public class playvideo extends AppCompatActivity {

    public static VideoView myVideoView;
    public static int position1;
    int position;
    public ProgressDialog progressDialog;
    public MediaController mediaControls;
    String VideoURL = "http://sixthsenseit.com/toshow/udr/videos/";

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playvideo);

        position1=0;

    }

    @Override
    protected void onResume() {
        super.onResume();

    //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(playvideo.this);
        }
        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.VideoView);
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(playvideo.this);
        // set a title for the progress bar
        progressDialog.setTitle("Please Wait");
        // set a message for the progress bar
        progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);

        mediaControls.setAnchorView(myVideoView);
        position=getIntent().getIntExtra("position",0);
        // show the progress bar
        progressDialog.show();
        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);
            //set the uri of the video to be played

            String  url1=VideoURL+(position+1)+".3gp";
            myVideoView.setVideoURI(Uri.parse(url1));
            myVideoView.requestFocus();
        }
        catch (Exception e)
        {
            //Log.e("Error", e.getMessage());
            //e.printStackTrace();
        }


        //we also set an setOnPreparedListener in order to know when the video file is ready for playback

        myVideoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                myVideoView.seekTo(position1);

                if (position1 == 0) {

                    myVideoView.start();

                } else {

                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position1", myVideoView.getCurrentPosition());
        myVideoView.pause();

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position

        position1 = savedInstanceState.getInt("Position1");
        myVideoView.seekTo(position1);
    }
}
