package com.epitech.intra.intranetepitech;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        Map<String, String> listGrade;
        private Context mContext;
        private LayoutInflater mInflater;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_grade, container, false);
            //displayGrade(rootView);
            ListView list = (ListView)rootView.findViewById(R.id.grade);
//            fillListGrade(&listGrade);
            doGradeRequest(rootView);
//            MyAdapter adapter = new MyAdapter(listGrade);
  //          list.setAdapter(adapter);
            return rootView;
        }

        public void setAdapter(Map<String,String> listGrade){
            ListView list = (ListView)getView().findViewById(R.id.grade);
            MyAdapter adapter = new MyAdapter(listGrade);
            list.setAdapter(adapter);
        }

        public void doGradeRequest(View rootView){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "https://epitech-api.herokuapp.com/modules?token="+User.getToken();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                listGrade = new HashMap<String,String>();
                                JSONObject jObj = new JSONObject(response);
                                JSONArray module = jObj.getJSONArray("modules");
                                for (int i=0; i<module.length(); i++) {
                                    JSONObject actor = module.getJSONObject(i);
                                    String name = actor.getString("title");
                                    String grade = actor.getString("grade");
                                    listGrade.put(name,grade);
                                    System.out.println(name);
                                }
                                setAdapter(listGrade);
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
    }
}
