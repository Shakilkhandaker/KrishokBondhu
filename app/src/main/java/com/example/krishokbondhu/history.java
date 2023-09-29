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

public class history extends AppCompatActivity {
    List<HistoryDetails> historyList;
    RecyclerView recyclerView;
    User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyList = new ArrayList<>();
        load_History_list();
    }
    private void load_History_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_HISTORY+user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject n = array.getJSONObject(i);
                                historyList.add(new HistoryDetails(
                                        n.getString("date_history"),
                                        n.getString("info_history"),
                                        n.getString("payment_value"),
                                        n.getString("time_value"),
                                        n.getString("status")
                                ));
                            }
                            HistoryAdapter adapter = new HistoryAdapter(history.this, historyList);
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