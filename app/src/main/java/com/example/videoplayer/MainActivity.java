package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public final static String LOCAL_VIDEO = "draw_video";
    public final static String ONLINE_FILE = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
    private VideoView mVideoView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video_view);
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
        mTextView = findViewById(R.id.textview);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.seekTo(1);
                mVideoView.start();
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mTextView.setVisibility(VideoView.INVISIBLE);
                mVideoView.start();
            }
        });

    }
    public Uri getMedia(String mediaName){

        if (URLUtil.isValidUrl(mediaName)){
            return Uri.parse(mediaName);
        }
        else {
            return Uri.parse("android.resourse://"+getPackageName()+
                    "/raw/"+ LOCAL_VIDEO);

        }

    }
    public void initializePlayer(){
        mTextView.setVisibility(VideoView.VISIBLE);
        Uri videoUri = getMedia(ONLINE_FILE);
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }
    public void releasePlayer(){
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            mVideoView.pause();
        }
    }
}