package com.epitech.intra.intranetepitech;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


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
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            ImageRequest request = new ImageRequest("https://cdn.local.epitech.eu/userprofil/profilview/dedick_r.jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            photo.setImageBitmap(bitmap);
                            System.out.println("C'est Ok");
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error){
                            //  photo.setImageResource(R.drawable.image_load_error);
                            System.out.println("fail");
                        }
                    });
            queue.add(request);

            return rootView;
        }
    }
}
