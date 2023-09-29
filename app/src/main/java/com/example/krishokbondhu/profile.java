package com.example.krishokbondhu;
import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class profile extends AppCompatActivity {
    private static final int CODE_POST_REQUEST = 1025;
    TextView t1,t2,t3,t4;
    EditText e1,e2;
    private static Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, loginpage.class));
        }
        t1 = findViewById(R.id.l2);// phone number
        t2 = findViewById(R.id.l1);// name show
        t3 = findViewById(R.id.update); // update button
        t4 = findViewById(R.id.update_done); // update complete
        e1 = findViewById(R.id.l4);// password
        e2 = findViewById(R.id.l2_1); // name edit field

        User user = SharedPrefManager.getInstance(this).getUser();
        t1.setText(String.valueOf((user.getPhone())));
        e1.setText(String.valueOf((user.getPassword())));
        t2.setText(String.valueOf((user.getPassword())));
        e2.setText(String.valueOf((user.getPassword())));

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.VISIBLE);
                e1.setEnabled(true);
                e2.setEnabled(true);
                //e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                // update profile
                // next_update();
                String s = t3.getText().toString();
//                if(s.equals("পরিবর্তন করুন")){
//                    t3.setText("পরিবর্তন সম্পন্ন হয়েছে");
//                }
                //Toast.makeText(getApplicationContext(),("Successfully Updated"), Toast.LENGTH_SHORT).show();

            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = e1.getText().toString();
                String name = e2.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    e1.setError("Please enter password");
                    e1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    e2.setError("Please enter name");
                    e2.requestFocus();
                    return;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(user.getId()));
                params.put("name", name);
                params.put("password", password);
                PerformNetworkRequest request = new PerformNetworkRequest(URLs.URL_PROFILE, params, CODE_POST_REQUEST);
                request.execute();
                t4.setVisibility(View.GONE);
                t3.setVisibility(View.VISIBLE);
                String p1 = e1.getText().toString();
                String n1 = e2.getText().toString();
                e1.setEnabled(false);// password
                e2.setEnabled(false);// name
                Toast.makeText(getApplicationContext(),("Successfully Updated"), Toast.LENGTH_SHORT).show();
                SharedPrefManager s = new SharedPrefManager();
                s.update(n1,p1);
                User user = SharedPrefManager.getInstance(mCtx).getUser();
                t1.setText(String.valueOf((user.getPhone())));
                e2.setText(String.valueOf((user.getName())));
                t2.setText(String.valueOf((user.getName())));
                e1.setText(String.valueOf((user.getPassword())));
            }
        });
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
                    //refreshHeroList(object.getJSONArray("heroes"));
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


            return null;
        }
    }
    public void next_update(){
        t3.setText("আপডেট করুন");
        String p1 = e1.getText().toString();
        String n1 = e2.getText().toString();
        e1.setEnabled(false);// password
        e2.setEnabled(false);// name
        Toast.makeText(getApplicationContext(),("Successfully Updated"), Toast.LENGTH_SHORT).show();
        SharedPrefManager s = new SharedPrefManager();
        s.update(n1,p1);
        User user = SharedPrefManager.getInstance(mCtx).getUser();
        t1.setText(String.valueOf((user.getPhone())));
        e2.setText(String.valueOf((user.getName())));
        t2.setText(String.valueOf((user.getPassword())));
        e1.setText(String.valueOf((user.getPassword())));
    }
}