package com.example.bobby.udr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Feedback extends AppCompatActivity {
EditText t1,t2,t3,t4;
    Button b1,b2;
    RatingBar r1;
    ImageView bj;
    Bitmap image,bitmap;
    String name,email,mobile,comments,rating,title;
    String url1;
    StringBuilder  stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        setTitle("Feedback");

        t1=(EditText)findViewById(R.id.editText12);
        t2=(EditText)findViewById(R.id.editText13);
        t3=(EditText)findViewById(R.id.editText14);
        t4=(EditText)findViewById(R.id.editText15);
        r1=(RatingBar)findViewById(R.id.ratingBar);
        b1=(Button)findViewById(R.id.button10);
        b2=(Button)findViewById(R.id.button11);
        bj=(ImageView)findViewById(R.id.imageView15);
    }

    @Override
    protected void onResume() {
        super.onResume();

        url1=MainActivity.pic;
        if(splashscreen.logout==true) {
            t2.setText(MainActivity.email);

            new JSONP2().execute();
        }

        if(splashscreen.fblogout==true) {
            t1.setText(MainActivity.fbname);

            new LongOperation(MainActivity.pic).execute();
        }

        if(splashscreen.twlogout==true) {
            t1.setText(MainActivity.twname);

            new LongOperation(MainActivity.pic).execute();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              name=  t1.getText().toString();
              email= t2.getText().toString();
              mobile= t3.getText().toString();
                comments= t4.getText().toString();
               rating= String.valueOf(r1.getRating());
               title= getIntent().getStringExtra("title");
                if ( t1.getText().toString().isEmpty()   ||t4.getText().toString().isEmpty() || r1.getRating()==0)
                {
                    Toast.makeText(Feedback.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else if(!email.contains("@") || (!email.contains(".")))
                {
                    Toast.makeText(Feedback.this, "Check your email id", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(url1==null)
                    {
                        url1=stringBuilder.toString();
                    }
                    new JSONP().execute();
                }
            }
        });


        r1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating>=4)
                {
                    b2.setVisibility(View.VISIBLE);
                }
                else
                {
                    b2.setVisibility(View.INVISIBLE);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comments= t4.getText().toString();
                rating= String.valueOf(r1.getRating());
                title= getIntent().getStringExtra("title");

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Install UDR music app");
                share.putExtra(Intent.EXTRA_TEXT, "Hi! Check out this amazing music-"+title+" on UDR music app.Get the app on http://www.udr-music.com/en. My rating for this song-"+rating+"/5");

                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
    }

    private class LongOperation extends AsyncTask<Void, Void,Void> {
        String url;
        LongOperation(String url)
        {
            this.url=url;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                image = DownloadImage(url);

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            bj.setImageBitmap(image);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private Bitmap DownloadImage(String url) throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL(url);
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

    class JSONP extends AsyncTask<Void,Void,String>
    {
        String jsonurl,jsonstring;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            jsonurl="http://sixthsenseit.com/toshow/udr/reviews.php";
        }

        @Override
        protected String doInBackground(Void... params) {

            String data="";
            try {
                URL url=new URL(jsonurl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata= URLEncoder.encode("name")+"="+URLEncoder.encode(name)+"&"+URLEncoder.encode("email")+"=" +URLEncoder.encode(email)
                        +"&"+URLEncoder.encode("mobile")+"=" +URLEncoder.encode(mobile)+"&"+URLEncoder.encode("comments")+"=" +URLEncoder.encode(comments)
                        +"&"+URLEncoder.encode("rating")+"=" +URLEncoder.encode(rating)+"&"+URLEncoder.encode("url")+"=" +URLEncoder.encode(url1)
                        +"&"+URLEncoder.encode("title")+"=" +URLEncoder.encode(title);

                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder  stringBuilder=new StringBuilder();
                while((jsonstring= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(jsonstring+"\n");
                }
data=stringBuilder.toString();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return  data;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(Feedback.this, s, Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    class JSONP2 extends AsyncTask<Void,Void,Bitmap>
    {
        String jsonurl,jsonstring;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            jsonurl="http://sixthsenseit.com/toshow/udr/pic.php";
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            String data="";
            try {
                URL url=new URL(jsonurl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata= URLEncoder.encode("email")+"="+URLEncoder.encode(MainActivity.email);

                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder=new StringBuilder();
                while((jsonstring= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(jsonstring+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                //Log.i("message",Signin.encodedImage);
                image= decodeBase64(stringBuilder.toString());
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return  image;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);

            bj.setImageBitmap(s);
        }


        public  Bitmap decodeBase64(String input)
        {
            byte[] decodedBytes = Base64.decode(input, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }
    }

}
