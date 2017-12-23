package com.example.bobby.udr;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class splashscreen extends AppCompatActivity {
    DatabaseHelper dh;
    TwDatabaseHelper dh2;
    FbDatabaseHelper dh1;

    public static Boolean logout,fblogout,twlogout;
    public static String emailsignin,passwordsignin;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MGJFShoTe7sbllCXSWqIwaVWe";
    private static final String TWITTER_SECRET = "S4hk3T9fddo6p1Im8xFtQXDaMa1UhI6UqJbSuCsIXeIvtUzBGA";

    private static int SPLASH_TIME_OUT = 5000;
    RadioButton rg1, rg2, rg3, rg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.splashscreen);

        dh = new DatabaseHelper(splashscreen.this);
        dh1 = new FbDatabaseHelper(splashscreen.this);
        dh2 = new TwDatabaseHelper(splashscreen.this);

        final MediaPlayer song = MediaPlayer.create(splashscreen.this, R.raw.udr);
        song.start();

        rg1 = (RadioButton) findViewById(R.id.radioButton);
        rg2 = (RadioButton) findViewById(R.id.radioButton2);
        rg3 = (RadioButton) findViewById(R.id.radioButton3);
        rg4 = (RadioButton) findViewById(R.id.radioButton4);

            startHeavyProcessing();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    song.release();
                    Intent i = new Intent(splashscreen.this, Signin.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    private void startHeavyProcessing(){
        new LongOperation().execute();
    }

    private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Cursor b10 = dh.getalldata();
            if (b10.getCount() == 0) {
                logout = false;
            }
            else
            {
                while (b10.moveToNext())
                {
                    emailsignin = b10.getString(1);
                    passwordsignin = b10.getString(2);
                    if (b10.getString(3).equalsIgnoreCase("True"))
                    {
                        logout = true;
                    }
                    else
                    {
                        logout = false;
                    }
                }
            }

            Cursor b11 = dh1.getalldata();
            if (b11.getCount() == 0) {
                fblogout = false;
            } else {
                while (b11.moveToNext()) {
                    if (b11.getString(1).equalsIgnoreCase("True")) {
                        fblogout = true;
                    }
                    else
                    {
                        fblogout = false;
                    }
                }
            }

            Cursor b12 = dh2.getalldata();
            if (b12.getCount() == 0) {
                twlogout = false;
            } else {
                while (b12.moveToNext()) {;
                    if (b12.getString(1).equalsIgnoreCase("True")) {
                        twlogout = true;
                    }
                    else
                    {
                        twlogout = false;
                    }
                }
            }

            for (int i = 1; i <= 20; i++) {
                try
                {
                    Thread.sleep(200);
                    publishProgress();
                }
                catch (InterruptedException e)
                {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if (rg1.isChecked()) {
                rg1.setChecked(false);
                rg2.setChecked(true);
            } else if (rg2.isChecked()) {
                rg2.setChecked(false);
                rg3.setChecked(true);
            } else if (rg3.isChecked()) {
                rg3.setChecked(false);
                rg4.setChecked(true);

            } else if (rg4.isChecked()) {
                rg4.setChecked(false);
                rg1.setChecked(true);
            }


            else
            {
            }
        }
    }
}
