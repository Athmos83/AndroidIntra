package com.epitech.intra.intranetepitech;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class PlanningActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planning, container, false);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String start = format1.format(c.getTime());
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            String end = format1.format(c.getTime());
            System.out.println("Date début => " + start + " Date fin => " + end);
            getCalendar(start, end);
            return rootView;
        }

        public void getCalendar(String start, String end){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "https://epitech-api.herokuapp.com/planning?token="+User.getToken()+"&start="+start+"&end="+end;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<BeanPlanning> planning = new ArrayList<BeanPlanning>();
                                JSONArray module = new JSONArray(response);
                                for (int i=0; i<module.length(); i++) {
                                    BeanPlanning plan = new BeanPlanning();
                                    JSONObject actor = module.getJSONObject(i);
                                    plan.set_acti_title(actor.getString("acti_title"));
                                    plan.set_moduleRegister(actor.getString("module_registered"));
                                    plan.set_start(actor.getString("start"));
                                    plan.set_moduleAvailable(actor.getString("module_available"));
                                    plan.set_end(actor.getString("end"));
                                    plan.set_titleModule(actor.getString("titlemodule"));
                                    plan.set_codeModule(actor.getString("codemodule"));
                                    plan.set_semester(actor.getString("semester"));
                                    plan.set_allowToken(actor.getString("allow_token"));
                                    plan.set_time(actor.getString("nb_hours"));
                                    plan.set_codeActi(actor.getString("codeacti"));
                                    plan.set_codeEvent(actor.getString("codeevent"));
                                    plan.set_codeInstance(actor.getString("codeinstance"));
                                    plan.set_registerStudent(actor.getString("register_student"));
                                    plan.set_date(changeFormatDate(actor.getString("start")));
                                    if (plan.get_moduleRegister().equals("true") && (plan.get_moduleAvailable().equals("true")))
                                        planning.add(plan);
                                }
                                setAdapter(planning);
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

        public void setAdapter(List<BeanPlanning> list){
            ListView listview = (ListView)getView().findViewById(R.id.planning);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listview = (ListView)getView().findViewById(R.id.planning);
                    BeanPlanning test = (BeanPlanning)listview.getItemAtPosition(position);
                    System.out.println(test.get_titleModule());

                    Bundle data = new Bundle();
                    //data.putString("module", test);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment mFrag = new ModuleDetail.PlaceholderFragment();
                    mFrag.setArguments(data);
                    fragmentTransaction.replace(R.id.container , new ModuleDetail.PlaceholderFragment());
                    User.setBeanPlanning(test);
                    fragmentTransaction.commit();
                }
            });

             ArrayAdapter<BeanPlanning> adapter = new PlanningAdapter(getActivity().getApplicationContext(), R.layout.display_module, list);
            listview.setAdapter(adapter);
        }

        public String changeFormatDate(String date){
            String currentData="";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            SimpleDateFormat dateDay = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dateHour = new SimpleDateFormat("hh:mm::ss");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(date);
                String daten = convertedDate.toString();
                currentData = dateDay.format(convertedDate);
               return currentData;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return currentData;
        }

        public void displayList(List<BeanPlanning> list){
            Iterator itr = list.iterator();
            while(itr.hasNext()) {
                Object element = itr.next();
                System.out.println(((BeanPlanning) element).get_acti_title() + " ");
                String date = ((BeanPlanning)element).get_start();

                //System.out.println(convertedDate);
               // System.out.println(Calendar.getInstance().getTime());
            }
        }


    }
}