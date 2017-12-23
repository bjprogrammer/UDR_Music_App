package com.example.bobby.udr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class playnews extends AppCompatActivity {
    TextView t1,t2,t3,t4;
    ImageView bj;
    Bitmap bitmap,image;

int position3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playnews);

        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView10);
        t3=(TextView)findViewById(R.id.textView11);
        t4=(TextView)findViewById(R.id.textView12);
        bj=(ImageView) findViewById(R.id.imageView13);

        position3=getIntent().getIntExtra("position3",0);
    }

    @Override
    protected void onResume() {
        super.onResume();


        t1.setText(news.newsList.get(position3).getTitle());
        t2.setText("Source-"+news.newsList.get(position3).getSource());
        t3.setText(news.newsList.get(position3).getTime());
        t4.setText(news.newsList.get(position3).getContent());

        new LongOperation().execute();
    }

    private Bitmap DownloadImage() throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL("http://sixthsenseit.com/toshow/udr/newspic/"+(position3+1)+".jpg");
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

            Toast.makeText(playnews.this,"Image", Toast.LENGTH_SHORT).show();
            bj.setImageBitmap(image);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
