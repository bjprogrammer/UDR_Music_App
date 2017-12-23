package com.example.bobby.udr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    ImageView bj;
    Bitmap image,bitmap;
    public  List<Newsdata> NewsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,time,source;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            source = (TextView) view.findViewById(R.id.source);
            time = (TextView) view.findViewById(R.id.time);
            bj=(ImageView)view.findViewById(R.id.imageView10);
        }
    }

    public NewsAdapter(List<Newsdata> NewsList) {
        this.NewsList = NewsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Newsdata news = NewsList.get(position);
        holder.title.setText(news.getTitle());
        holder.source.setText(news.getSource());
        holder.time.setText(news.getTime());

        new LongOperation(bj).execute(position);
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    private Bitmap DownloadImage(int position) throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL("http://sixthsenseit.com/toshow/udr/newspic/"+(position+1)+".jpg");
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

    private class LongOperation extends AsyncTask<Integer, Void,Bitmap> {

        ImageView img;

        LongOperation(ImageView img)
        {
            this.img=img;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            try {
                image = DownloadImage(params[0]);

            } catch (Exception e) {

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
    }
}

