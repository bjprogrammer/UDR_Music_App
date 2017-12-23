package com.example.bobby.udr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Invite extends AppCompatActivity {
ImageView bj;
    Bitmap bitmap,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        setTitle("Invite Friends");
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.buttonShareTextUrl:
                        shareTextUrl();
                        break;


                    case R.id.buttonShareImage:
                        new LongOperation().execute();
                        break;
                }
            }
        };
        findViewById(R.id.buttonShareTextUrl).setOnClickListener(handler);
        findViewById(R.id.buttonShareImage).setOnClickListener(handler);
        bj=(ImageView)findViewById(R.id.imageView12);
    }


    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Install UDR music app");
        share.putExtra(Intent.EXTRA_TEXT, "Check out latetst version of UDR music app.Get the app on http://www.udr-music.com/en");

        startActivity(Intent.createChooser(share, "Share link!"));
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private Bitmap DownloadImage() throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL("http://www.udr-music.com/images/UDR_logo_lila.png");
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStream inputStream=null;
        try {
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch(Exception e)
            {
                e.printStackTrace();
            }

            while(bitmap==null)
            {
                bitmap = BitmapFactory.decodeStream(inputStream);
                wait(100);
            }


        return bitmap;
    }

    private class LongOperation extends AsyncTask<Void, Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                image = DownloadImage();

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);

            bj.setImageBitmap(bitmap);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM, getImageUri(Invite.this, bitmap));
            startActivity(Intent.createChooser(share, "Share Image!"));

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
