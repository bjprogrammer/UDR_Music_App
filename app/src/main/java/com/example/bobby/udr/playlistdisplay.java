package com.example.bobby.udr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

public class playlistdisplay extends AppCompatActivity {

    public static List<playlisthelper> audioList = new ArrayList<>();
    private RecyclerView recyclerView5;
    private playlistdisplayadapter mAdapter5;
    String playlistname;
    int musicid[];
    String  songid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistdisplay);


        setTitle("Playlist-"+getIntent().getStringExtra("playlistname"));

        mAdapter5 = new playlistdisplayadapter(audioList);
        recyclerView5 = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView5.setLayoutManager(mLayoutManager);
        recyclerView5.addItemDecoration(new DividerItemDecoration(playlistdisplay.this, LinearLayoutManager.VERTICAL));
        recyclerView5.setItemAnimator(new DefaultItemAnimator());
        recyclerView5.setAdapter(mAdapter5);

        recyclerView5.addOnItemTouchListener(new RecyclerTouchListener(playlistdisplay.this, recyclerView5, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                playlisthelper movie = audioList.get(position);
                Toast.makeText(playlistdisplay.this, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(playlistdisplay.this, playselectplaylist.class);
                i.putExtra("position1", position);
                i.putExtra("musicid",musicid);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
              playlistname= getIntent().getStringExtra("playlistname");
                songid=audioList.get(position).getID();
                Toast.makeText(playlistdisplay.this, songid, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder b2=new AlertDialog.Builder(playlistdisplay.this)
                        .setMessage("Do you want to remove this music from playlist?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new JSONP2().execute(playlistname,songid);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog b3=b2.create();
                b3.setTitle("Delete Playlist");
                b3.show();
            }
        }));

        new JSONP().execute();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    class JSONP extends AsyncTask<Void,Void,String>
    {
        String jsonurl,jsonstring;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            audioList.clear();
            jsonurl="http://sixthsenseit.com/toshow/udr/udrgetaudioplaylist.php";
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
                String postdata= URLEncoder.encode("email")+"="+URLEncoder.encode(MainActivity.email)+"&"+URLEncoder.encode("playlistname")+"=" +URLEncoder.encode(getIntent().getStringExtra("playlistname"));

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

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                JSONObject jsonRootObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonRootObject.optJSONArray("server_response");

                musicid=new int[jsonArray.length()];
                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String year = jsonObject.optString("year").toString();
                    String title = jsonObject.optString("title").toString();
                    String artist = jsonObject.optString("artist").toString();
                    String ID = jsonObject.optString("ID").toString();

                    musicid[i]=jsonObject.optInt("ID");
                    playlisthelper movie = new playlisthelper(title, artist,year,ID);
                    audioList.add(movie);

                }
data=stringBuilder.toString();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
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
            Toast.makeText(playlistdisplay.this, s, Toast.LENGTH_SHORT).show();
            mAdapter5.notifyDataSetChanged();

        }
    }

    class JSONP2 extends AsyncTask<String,Void,String>
    {
        String jsonurl,jsonstring;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            jsonurl="http://sixthsenseit.com/toshow/udr/deletemusic.php";
        }

        @Override
        protected String doInBackground(String... params) {

            String data="";
            try {
                URL url=new URL(jsonurl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata= URLEncoder.encode("email")+"="+URLEncoder.encode(MainActivity.email)+"&"+URLEncoder.encode("playlistname")+"=" +URLEncoder.encode(params[0])
                        +"&"+URLEncoder.encode("musicid")+"=" +URLEncoder.encode(params[1]);

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

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                data=stringBuilder.toString();
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

            if(s.contains("deleted")) {
                mAdapter5.notifyDataSetChanged();
                //audioList.clear();
                new JSONP().execute();

            }
            Toast.makeText(playlistdisplay.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}