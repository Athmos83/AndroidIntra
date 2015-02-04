package com.epitech.intra.intranetepitech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
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
    protected ProgressDialog myProgressDialog;
    final Handler uiThreadCallback = new Handler();
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
                LoginRequest();
            }
        });
    }

    public void LoginRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epitech-api.herokuapp.com/login",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response = "+response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String token = jObj.getString("token");
                            User.setToken(token);
                            TextView email = (TextView) findViewById(R.id.email);
                            User.setLogin(email.getText().toString());
                            //System.out.println(User.getToken());
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } catch (JSONException e){
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}