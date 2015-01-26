package com.example.thomasmendez.loginintra;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomasmendez on 26/01/15.
 */
public class Accueil extends Activity {
    final String TOKEN = "token";
    String token = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.accueil);
        token = intent.getStringExtra(TOKEN);
        System.out.println("salut les amis voici le token " + token);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://epitech-api.herokuapp.com/infos";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject infos = new JSONObject(response);
                            String login = infos.getJSONObject("infos").getString("login");
                            TextView loginView = (TextView)findViewById(R.id.login);
                            loginView.setText(login);
                            String photos = infos.getJSONObject("infos").getString("picture");
                            System.out.println("https:\\/\\/cdn.local.epitech.eu\\/userprofil\\/" + photos);
                            ImageView imageProfil = (ImageView)findViewById(R.id.photo);
                            imageProfil.setImageBitmap(getBitmapFromURL("https:\\/\\/cdn.local.epitech.eu\\/userprofil\\/" + photos));
                        } catch (JSONException e) {
                            System.out.println("Fuck you");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work 2!");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String tok = token;
                try {
                    JSONObject json = new JSONObject(tok);
                    params.put("token", json.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
