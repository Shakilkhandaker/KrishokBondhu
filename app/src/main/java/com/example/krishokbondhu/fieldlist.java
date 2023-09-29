package com.example.krishokbondhu;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fieldlist extends AppCompatActivity {
    private static final int CODE_POST_REQUEST = 1025;
    private static final int CODE_GET_REQUEST = 1024;
    List<Field> productList;
    RelativeLayout r;
    RecyclerView recyclerView;
    ImageView addfield,updatefield;
    EditText edit1,edit2,edit3;
    TextView ink;
    String specific_field_id ="";
    NotificationManagerCompat notificationManagerCompat;
    Notification n;

    User user = SharedPrefManager.getInstance(this).getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fieldlist);
        recyclerView    = (RecyclerView) findViewById(R.id.recyclerView);
        addfield        = findViewById(R.id.new_field);
        updatefield     = findViewById(R.id.update_field);
        edit1           = findViewById(R.id.field_m);
        edit2           = findViewById(R.id.field_v);
        edit3           = findViewById(R.id.field_p);
        ink             = findViewById(R.id.v_add_box);
        r               = findViewById(R.id.middle_edit);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList     = new ArrayList<>();
        loadfieldlist();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyID","My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"MyID").
                setSmallIcon(R.mipmap.ic_launcher_round).setContentText("আপনি সফলভাবে একটি নতুন জমি যোগ করেছেন").setContentTitle("সফল");
        n = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);

        ink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.setVisibility(View.VISIBLE);
                ink.setVisibility(View.GONE);
            }
        });
        addfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mark = edit1.getText().toString().trim();
                String village = edit2.getText().toString().trim();
                String pouroshova = edit3.getText().toString().trim();

                if (TextUtils.isEmpty(mark)) {
                    edit1.setError("Please enter mark");
                    edit1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(village)) {
                    edit2.setError("Please enter village");
                    edit2.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pouroshova)) {
                    edit3.setError("Please enter pouroshova");
                    edit3.requestFocus();
                    return;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(user.getId()));
                params.put("mark",String.valueOf(mark));
                params.put("village", String.valueOf(village));
                params.put("p", String.valueOf(pouroshova));

                PerformNetworkRequest request = new PerformNetworkRequest(URLs.URL_FIELD_ADD, params, CODE_POST_REQUEST);
                request.execute();

                edit1.setText("");
                edit2.setText("");
                edit3.setText("");

                notificationManagerCompat.notify(1,n);

                ink.setVisibility(View.VISIBLE);
                r.setVisibility(View.GONE);
                refreshlist();

            }
        });

        updatefield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mark = edit1.getText().toString().trim();
                String village = edit2.getText().toString().trim();
                String pouroshova = edit3.getText().toString().trim();

                if (TextUtils.isEmpty(mark)) {
                    edit1.setError("Please enter mark");
                    edit1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(village)) {
                    edit2.setError("Please enter village");
                    edit2.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pouroshova)) {
                    edit3.setError("Please enter pouroshova");
                    edit3.requestFocus();
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("id",specific_field_id);
                params.put("mark",String.valueOf(mark));
                params.put("village", String.valueOf(village));
                params.put("p", String.valueOf(pouroshova));
                PerformNetworkRequest request = new PerformNetworkRequest(URLs.URL_FIELD_UPDATE, params, CODE_POST_REQUEST);
                request.execute();
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                ink.setVisibility(View.VISIBLE);
                r.setVisibility(View.GONE);
                updatefield.setVisibility(View.GONE);
                addfield.setVisibility(View.VISIBLE);
                refreshlist();
            }
        });

    }

    private void deleteField(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(URLs.URL_FIELD_DELETE+id, null, CODE_GET_REQUEST);
        request.execute();
        refreshlist();
    }
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);
            return null;
        }
    }

    private void loadfieldlist() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_FIELD_LIST+user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new Field(
                                        product.getInt("field_id"),
                                        product.getString("field_mark"),
                                        product.getString("field_village"),
                                        product.optString("field_pouroshova")
                                ));
                            }
                            FieldAdapter adapter = new FieldAdapter(fieldlist.this, productList);
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
    void refreshlist(){
        productList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_FIELD_LIST+user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new Field(
                                        product.getInt("field_id"),
                                        product.getString("field_mark"),
                                        product.getString("field_village"),
                                        product.optString("field_pouroshova")
                                ));
                            }
                            FieldAdapter adapter = new FieldAdapter(fieldlist.this, productList);
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
    class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.FieldViewHolder> {
        private Context mCtx;
        private List<Field> productList;
        public FieldAdapter(Context mCtx, List<Field> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }
        @NonNull
        @Override
        public FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.fieldlist, null);
            return new FieldViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
            Field product = productList.get(position);
            holder.serial.setText(String.valueOf(product.getField_id()));
            holder.textViewShortDesc.setText(product.getField_mark());
            holder.textViewRating.setText(String.valueOf(product.getField_village()));
            holder.textViewPrice.setText(String.valueOf(product.getField_pouroshova()));
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit1.setText(String.valueOf(product.getField_mark()));
                    edit2.setText(String.valueOf(product.getField_village()));
                    edit3.setText(String.valueOf(product.getField_pouroshova()));
                    r.setVisibility(View.VISIBLE);
                    ink.setVisibility(View.GONE);
                    addfield.setVisibility(View.GONE);
                    updatefield.setVisibility(View.VISIBLE);
                    specific_field_id = "";
                    specific_field_id = String.valueOf(product.getField_id());
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fieldlist.this);
                    builder.setTitle("Delete " + product.getField_mark() +" - "+product.getField_village() +" - "+product.getField_pouroshova())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteField(product.getField_id());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();

                }
            });
        }
        @Override
        public int getItemCount() {
            return productList.size();
        }
        public class FieldViewHolder extends RecyclerView.ViewHolder {
            TextView textViewShortDesc, textViewRating, textViewPrice,serial;
            ImageView update, delete;
            public FieldViewHolder(View itemView) {
                super(itemView);
                serial = itemView.findViewById(R.id.filed_serial);
                textViewShortDesc = itemView.findViewById(R.id.filed_no);
                textViewRating = itemView.findViewById(R.id.village_name);
                textViewPrice = itemView.findViewById(R.id.area_name);
                update = itemView.findViewById(R.id.im1);
                delete = itemView.findViewById(R.id.im2);

            }
        }
    }
}