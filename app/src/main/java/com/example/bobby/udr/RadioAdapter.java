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

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.MyViewHolder>
{
ImageView bj;
    Bitmap image,bitmap;
    public List<radiostreaming> radioList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, language;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            language = (TextView) view.findViewById(R.id.language);

            bj=(ImageView)view.findViewById(R.id.imageView10);

        }
    }

    public RadioAdapter(List<radiostreaming> radioList) {
        this.radioList = radioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radio_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        radiostreaming radio = radioList.get(position);
        holder.title.setText(radio.getTitle());
        holder.language.setText(radio.getLanguage());

        new LongOperation(bj).execute(position);
    }

    @Override
    public int getItemCount() {
        return radioList.size();
    }



    private Bitmap DownloadImage(int position) throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL("http://sixthsenseit.com/toshow/udr/radiopic/"+(position+1)+".jpg");
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


