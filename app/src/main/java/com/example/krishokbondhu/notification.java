package com.example.krishokbondhu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {
    List<NotificationDetails> notificationList;
    RecyclerView recyclerView;
    User user = SharedPrefManager.getInstance(this).getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationList = new ArrayList<>();
        load_Notification_list();
    }
    private void load_Notification_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_NOTIFICATION+user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject n = array.getJSONObject(i);
                                notificationList.add(new NotificationDetails(
                                        n.getString("date"),
                                        n.getString("info")
                                ));
                            }
                            NotificationAdapter adapter = new NotificationAdapter(notification.this, notificationList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}