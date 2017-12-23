package com.example.bobby.udr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

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

public class MessagesFragment extends Fragment {
DatabaseHelper dh;
    TwDatabaseHelper dh2;
    FbDatabaseHelper dh1;

    public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        dh=new DatabaseHelper(getContext());
        dh1=new FbDatabaseHelper(getContext());
        dh2=new TwDatabaseHelper(getContext());

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if(accessToken != null) {
        if(splashscreen.fblogout==true)
        {
           splashscreen.fblogout=false;
            dh1.deletedata(1);
            LoginManager.getInstance().logOut();
       }

        }

        if (twitterSession != null)
        {
            if(splashscreen.twlogout==true) {
                splashscreen.twlogout=false;
                dh2.deletedata(1);
                CookieSyncManager.createInstance(getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
                Twitter.getSessionManager().clearActiveSession();
                Twitter.logOut();

            }
        }

            if( splashscreen.logout==true)
            {
                splashscreen.logout=false;
                dh.deletedata(1);
            }
        Intent i=new Intent(getContext(),Signin.class);
        startActivity(i);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

