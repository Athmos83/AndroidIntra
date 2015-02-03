package com.epitech.intra.intranetepitech;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfilActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
            final ImageView photo = (ImageView) rootView.findViewById(R.id.photo);
            final TextView log = (TextView) rootView.findViewById(R.id.log);
            final TextView credit = (TextView) rootView.findViewById(R.id.credit);
            getPictureUser(photo);
            getInfoUser(log, credit);
            return rootView;
        }

        public void setLogTime(String log){

        }

        public void getInfoUser(final TextView logView, final TextView credit){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epitech-api.herokuapp.com/infos",
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONObject log = jObj.getJSONObject("current");
                                logView.setText("Le temps de log est de :"+log.getString("active_log"));
                                credit.setText("Tu as "+log.getString("achieved")+" crédits. Il en faut "+log.getString("credits_obj")+" crédits. Tu peux en obtenir encore "+log.getString("inprogress"));
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Info user don't work!");
                }
            }){
                @Override
                protected Map<String,String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", User.getToken());
                    return params;
                }
            };
            queue.add(stringRequest);
        }

        public void getPathPictureUser(){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "https://epitech-api.herokuapp.com/photo?token="+User.getToken()+"&login="+User.getLogin();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String PicturePath = jObj.getString("url");
                                System.out.println(PicturePath);
                                User.setpathPicture(PicturePath);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });
            queue.add(stringRequest);
        }


        public void getPictureUser(final ImageView photo){

            if (User.getPhoto() == null) {
                getPathPictureUser();
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                ImageRequest request = new ImageRequest(User.getPathPicture(),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                photo.setImageBitmap(bitmap);
                                User.setPhoto(bitmap);
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                //  photo.setImageResource(R.drawable.image_load_error);
                                System.out.println("fail");
                            }
                        });
                queue.add(request);
            } else
                photo.setImageBitmap(User.getPhoto());
        }
    }
}
