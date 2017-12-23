package com.example.bobby.udr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder>
{
    ImageView bj;
    Bitmap image,bitmap;
    public List<commentshelper> commentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,comments,timestamp;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            comments = (TextView) view.findViewById(R.id.comments);
            timestamp=(TextView) view.findViewById(R.id.timestamp);

            bj=(ImageView)view.findViewById(R.id.imageView16);
        }
    }

    public CommentsAdapter(List<commentshelper> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        commentshelper comments = commentsList.get(position);
        holder.name.setText(comments.getName());
        holder.comments.setText(comments.getComments());
        holder.timestamp.setText(comments.getTimestamp());

        new LongOperation(bj).execute(comments.getUrl());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
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

    private class LongOperation extends AsyncTask<String, Void,Bitmap> {
        ImageView img;

        LongOperation(ImageView img)
        {
            this.img=img;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            if(!params[0].contains("http"))  {
                Log.i("message",params[0]);
               image= decodeBase64(params[0]);
            }
            else
            {
                try
                {
                    image = DownloadImage(params[0]);
                } catch (Exception e)
                {

                }
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);

            img.setImageBitmap(s);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        public  Bitmap decodeBase64(String input)
        {
            byte[] decodedBytes = Base64.decode(input, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }

    }
}
