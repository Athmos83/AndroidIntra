package com.epitech.intra.intranetepitech;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {
    String _login = "dedick_r";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button)findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                doRequest("https://epitech-api.herokuapp.com/login");
            }

        });

    }

    public void doRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://epitech-api.herokuapp.com/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String token = jObj.getString("token");
                            User.setToken(token);
                            User.setLogin(_login);
                            System.out.println(User.getToken());
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                TextView email = (TextView) findViewById(R.id.email);
                TextView password = (TextView) findViewById(R.id.password);
                // System.out.println("Test : " +email.getText().toString() + " => "+password.getText().toString() );
                Map<String, String> params = new HashMap<String, String>();
                _login = email.getText().toString();
//                params.put("login", _login);
                //              params.put("password", password.getText().toString());
                _login = "dedick_r";
                params.put("login", "dedick_r");
                params.put("password", "vHIRX&(~");
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}