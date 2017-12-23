package com.example.bobby.udr;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class Signin extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TwitterLoginButton loginButton1;
    TwitterSession session;

    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    byte[] b;
    static String encodedImage ;
    Bitmap bitm;

    private static final String TWITTER_KEY = "MGJFShoTe7sbllCXSWqIwaVWe";
    private static final String TWITTER_SECRET = "S4hk3T9fddo6p1Im8xFtQXDaMa1UhI6UqJbSuCsIXeIvtUzBGA";

    EditText e1=null, e2=null,e3=null,e4=null,e5=null,e6=null;
    Button b1,b2,b3;
    TextView t2;
    int c1=0;
    int c2=0;
    public static String name,email,password,phone,email1,password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.signin);

        setTitle("Login");

        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton1 = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton1.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                //doCrop();
                String s = getRealPathFromURI(mImageCaptureUri);
                decodeFile(s);
                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                String s1 = getRealPathFromURI(mImageCaptureUri);
                //doCrop();
                decodeFile(s1);
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.URL_SAFE);

                    //imageView.setImageBitmap(photo);
                    new JSONP().execute(encodedImage);

                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();

                break;

        }
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitm = BitmapFactory.decodeFile(filePath, o2);

        //imageView.setImageBitmap(bitm);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        new JSONP().execute(encodedImage);
    }

    public String getRealPathFromURI(Uri uri) {

        if (uri == null) {
            Toast.makeText(Signin.this, "Image not selected", Toast.LENGTH_LONG).show();
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    @Override
    protected void onResume() {
        super.onResume();

        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);

        e1=(EditText) findViewById(R.id.editText);
        e2=(EditText) findViewById(R.id.editText2);
        e3=(EditText) findViewById(R.id.editText3);
        e4=(EditText) findViewById(R.id.editText4);
        e5=(EditText) findViewById(R.id.editText5);
        e6=(EditText) findViewById(R.id.editText6);

        t2=(TextView)findViewById(R.id.textView2);
        final ImageView i1=(ImageView) findViewById(R.id.imageView2);
        final ImageView i2=(ImageView) findViewById(R.id.imageView3);
        final ImageView i3=(ImageView) findViewById(R.id.imageView7);
        final ImageView i4=(ImageView) findViewById(R.id.imageView4);
        final ImageView i5=(ImageView) findViewById(R.id.imageView8);
        final ImageView i6=(ImageView) findViewById(R.id.imageView5);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Signin.this,Forgetpassword.class);
                startActivity(i);
                finish();
            }
        });

        if(splashscreen.logout==true)
        {
            email1=splashscreen.emailsignin;
            password1=splashscreen.passwordsignin;
            new JSONP1().execute();
        }

       else  if(splashscreen.fblogout==true || splashscreen.twlogout==true)
        {
            //loginButton.performClick();
            Intent i=new Intent(Signin.this,MainActivity.class);
            startActivity(i);
            finish();
        }

b1.setOnClickListener(new View.OnClickListener() {

                          @Override
                          public void onClick(View v) {
                              if(c1==0) {
                                  i1.setVisibility(ImageView.VISIBLE);
                                  i2.setVisibility(ImageView.VISIBLE);
                                  e1.setVisibility(EditText.VISIBLE);
                                  e2.setVisibility(EditText.VISIBLE);
                                  e3.setVisibility(EditText.GONE);
                                  e4.setVisibility(EditText.GONE);
                                  e5.setVisibility(EditText.GONE);
                                  e6.setVisibility(EditText.GONE);
                                  i3.setVisibility(ImageView.GONE);
                                  i4.setVisibility(ImageView.GONE);
                                  i5.setVisibility(ImageView.GONE);
                                  i6.setVisibility(ImageView.GONE);
                                  b2.setVisibility(Button.GONE);
                                  t2.setVisibility(TextView.GONE);
                                  loginButton1.setVisibility(TwitterLoginButton.GONE);
                                  loginButton.setVisibility(LoginButton.GONE);
                                  c1++;
                              }
                              else if(c1==1)
                              {
                                  c1--;
                                  if(c2==0) {
                                      email1= e1.getText().toString();
                                      password1=e2.getText().toString();

                                      if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty()) {

                                          Toast.makeText(Signin.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                                      }
                                      else if(!email1.contains("@") || (!email1.contains(".")))
                                      {
                                          Toast.makeText(Signin.this, "Check your email id", Toast.LENGTH_SHORT).show();
                                      }

                                      else if(password1.length()<8)
                                      {
                                          Toast.makeText(Signin.this, "Password length cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                                      }
                                      else {
                                          new JSONP1().execute();
                                          c2++;
                                      }
                                  }
                              }
                          }
                      });

        b2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(c1==1) {
                    i1.setVisibility(ImageView.GONE);
                    i2.setVisibility(ImageView.GONE);
                    e1.setVisibility(EditText.GONE);
                    e2.setVisibility(EditText.GONE);
                    e3.setVisibility(EditText.VISIBLE);
                    e4.setVisibility(EditText.VISIBLE);
                    e5.setVisibility(EditText.VISIBLE);
                    e6.setVisibility(EditText.VISIBLE);
                    i3.setVisibility(ImageView.VISIBLE);
                    i4.setVisibility(ImageView.VISIBLE);
                    i5.setVisibility(ImageView.VISIBLE);
                    i6.setVisibility(ImageView.VISIBLE);
                    c1--;

                }
                else {

                    name = e3.getText().toString();
                    email = e4.getText().toString();
                    phone = e5.getText().toString();
                    password = e6.getText().toString();

                    if ( e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty() || e5.getText().toString().isEmpty() ||  e6.getText().toString().isEmpty()) {

                        Toast.makeText(Signin.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                    }
                    else if(!email.contains("@") || (!email.contains(".")))
                    {
                        Toast.makeText(Signin.this, "Check your email id", Toast.LENGTH_SHORT).show();
                    }
                    else if (phone.length()<10)
                    {
                        Toast.makeText(Signin.this, "Incorrect mobile no.", Toast.LENGTH_SHORT).show();
                    }
                    else if(password.length()<8)
                    {
                        Toast.makeText(Signin.this, "Password length cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AlertDialog.Builder build = new AlertDialog.Builder(Signin.this);
                        build.setTitle("Upload profile image using -");

                        build.setPositiveButton("camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                                try {
                                    intent.putExtra("return-data", true);
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                        });


                        build.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        build.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, PICK_FROM_FILE);
                            }
                        });
                        build.show();
                    }
                }
            }
        });

        loginButton1.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                session = result.data;

                final String twname=session.getUserName();
                final long twid=session.getUserId();

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                Twitter.getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {

                            @Override
                            public void failure(TwitterException e) {
                                Toast.makeText(Signin.this, "Unable to retrieve user data", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void success(Result<User> userResult) {


                                User user = userResult.data;
                                String twpic = user.profileImageUrl;
                                //String name=user.name;
                                //String email=user.email;
                                Toast.makeText(Signin.this, twpic+name+String.valueOf(user.followersCount)+user.createdAt, Toast.LENGTH_SHORT).show();
                                if(splashscreen.twlogout==false) {
                                    splashscreen.twlogout=true;
                                    TwDatabaseHelper dh2 = new TwDatabaseHelper(Signin.this);
                                    boolean b = dh2.deletedata(1);
                                    boolean b1 = dh2.insertdata(splashscreen.twlogout.toString(), String.valueOf(twid),twname,twpic);

                                }
                            }
                        });

              /*  TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the email address
                        String email = result.data;
                        Toast.makeText(Signin.this, email, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                    }
                });*/

                Intent bj=new Intent(Signin.this,MainActivity.class);
                startActivity(bj);
                finish();

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(Signin.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               //  String s="User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken();


                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {

                                } else {
                                   // String email = me.optString("email");
                                    String fbid = me.optString("id");
                                    String fbname = me.optString("name");
                                    String fbpic="https://graph.facebook.com/"+fbid +"/picture?type=normal";
                                    //String gender = me.optString("gender");
                                    Toast.makeText(Signin.this,fbid+fbname, Toast.LENGTH_SHORT).show();

                                    if(splashscreen.fblogout==false) {
                                        splashscreen.fblogout=true;
                                        FbDatabaseHelper dh1 = new FbDatabaseHelper(Signin.this);
                                        boolean b = dh1.deletedata(1);
                                        boolean b1 = dh1.insertdata(splashscreen.fblogout.toString(),fbid,fbname,fbpic);

                                    }
                                    Intent bj1=new Intent(Signin.this,MainActivity.class);
                                    startActivity(bj1);
                                    finish();
                                }

                            }
                        }).executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Signin.this, "Login attempt failed", Toast.LENGTH_SHORT).show();
            }

        });
    }


    class JSONP extends AsyncTask<String,Void,String> {
        String jsonurl, jsonstring,regidlogin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl ="http://sixthsenseit.com/toshow/udr/signup.php";
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder  stringBuilder;
            String data = "";
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata = URLEncoder.encode("name") + "=" + URLEncoder.encode(name) + "&" + URLEncoder.encode("phone") + "=" + URLEncoder.encode(phone)
                        + "&" + URLEncoder.encode("email") + "=" + URLEncoder.encode(email) + "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password)
               +"&" +URLEncoder.encode("pic") + "=" + URLEncoder.encode(params[0]);

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

                data=stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("inserted"))
            {

                Toast.makeText(Signin.this, "Successfuly registered.Please Sign in", Toast.LENGTH_SHORT).show();
                b1.performClick();

            }
            else
            {
                Toast.makeText(Signin.this,"Already registered.Please sign in", Toast.LENGTH_SHORT).show();
                b1.performClick();
            }
        }
    }


    class JSONP1 extends AsyncTask<Void,Void,String> {
        String jsonurl, jsonstring,regidlogin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl ="http://sixthsenseit.com/toshow/udr/signin.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            String data = "";
            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                String postdata = URLEncoder.encode("email") + "=" + URLEncoder.encode(email1) + "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password1);

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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("logged"))
            {
                if(splashscreen.logout==false) {
                    splashscreen.logout=true;
                    DatabaseHelper dh = new DatabaseHelper(Signin.this);
                    boolean b = dh.deletedata(1);
                    boolean b1 = dh.insertdata(e1.getText().toString(), e2.getText().toString(), splashscreen.logout.toString());

                }
                Toast.makeText(Signin.this,"Signing in", Toast.LENGTH_SHORT).show();
                Intent bj=new Intent(Signin.this,MainActivity.class);
                bj.putExtra("email",email1);
                startActivity(bj);
                finish();
            }
            else
            {
                Toast.makeText(Signin.this,s, Toast.LENGTH_SHORT).show();
                c2--;
            }
        }
    }
}
