package com.example.bobby.udr;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,SearchView.OnQueryTextListener{

    private FragmentDrawer drawerFragment;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.mipmap.ic_theaters_black_48dp,
            R.mipmap.ic_queue_music_black_48dp,
            R.mipmap.ic_rss_feed_black_48dp,
            R.mipmap.ic_subscriptions_black_48dp,
            R.mipmap.ic_radio_black_48dp
    };
    TextView t1;
    ImageView bj;
    public static String email,password,logout,fbid,fbname,twid,twname,pic;
    public static int ID;
    public Bitmap image,bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("UDR Music");

        t1=(TextView) findViewById(R.id.textView14);
        bj=(ImageView) findViewById(R.id.imageView14);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(MainActivity.this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new video(), "Video");
        adapter.addFrag(new music(), "Music");
        adapter.addFrag(new news(), "News");
        adapter.addFrag(new ondemand(),"On Demand");
        adapter.addFrag(new radio(), "Radio");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(MainActivity.this, "Searching", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(MainActivity.this,SearchableActivity.class);
        startActivity(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = "Explore UDR";
                //finish();
                break;
            case 1:
                fragment = new FriendsFragment();
                title = "Invite";
                break;
            case 2:
                fragment = new Playlist();
                title = "Playlist";
                break;
            case 3:
                fragment = new MessagesFragment();
                title = "Logout";
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(splashscreen.logout==true)
        {
            DatabaseHelper dh=new DatabaseHelper(MainActivity.this);
            Cursor b10=dh.getalldata();
            if(b10.getCount()>0)
            {
                while(b10.moveToNext())
                {
                   ID= b10.getInt(0);
                   email= b10.getString(1);
                   password= b10.getString(2);
                   logout= b10.getString(3);
                    t1.setText(email);
                }
                new JSONP().execute();
            }
        }

        else if(splashscreen.fblogout==true)
        {
            FbDatabaseHelper dh1=new FbDatabaseHelper(MainActivity.this);
            dh1.getalldata();

            Cursor b10=dh1.getalldata();
            if(b10.getCount()>0)
            {
                while(b10.moveToNext())
                {
                    ID= b10.getInt(0);
                    logout= b10.getString(1);
                    fbid= b10.getString(2);
                    fbname= b10.getString(3);
                    pic= b10.getString(4);
                    t1.setText(fbname);
                }
                new LongOperation().execute();
            }
        }

        else if(splashscreen.twlogout==true)
        {
            TwDatabaseHelper dh2=new TwDatabaseHelper(MainActivity.this);
            Cursor b10=dh2.getalldata();
            if(b10.getCount()>0)
            {
                while(b10.moveToNext())
                {
                    ID= b10.getInt(0);
                    logout= b10.getString(1);
                    twid= b10.getString(2);
                    twname= b10.getString(3);
                    pic= b10.getString(4);
                    t1.setText(twname);
                }
                new LongOperation().execute();
            }
        }
    }

    private Bitmap DownloadImage() throws InterruptedException {
        URL imageURL = null;

        try {
            imageURL = new URL(pic);
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
            bj.setImageBitmap(image);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    class JSONP extends AsyncTask<Void,Void,Bitmap>
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
                StringBuilder  stringBuilder=new StringBuilder();
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