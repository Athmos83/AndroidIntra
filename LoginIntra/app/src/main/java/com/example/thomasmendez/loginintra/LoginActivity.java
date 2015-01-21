package com.example.thomasmendez.loginintra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{

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
              getConnection();
          }

       });

    }

    public void getConnection(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://epitech-api.herokuapp.com/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //System.out.println("Response is: "+ response.substring(0,500));
                        System.out.println("Response = "+response);
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
                params.put("login", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}



