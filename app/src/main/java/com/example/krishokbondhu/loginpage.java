package com.example.krishokbondhu;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class loginpage extends AppCompatActivity {
    TextView reg,lph,lpsw;
    Button btn_lgin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        reg = findViewById(R.id.reg_is);
        lph = findViewById(R.id.ltext3);
        lpsw = findViewById(R.id.ltext5);
        btn_lgin = findViewById(R.id.btn_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, homepage.class));
            return;
        }
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_reg = new Intent(loginpage.this,registrationpage.class);
                startActivity(intent_reg);
            }
        });
        btn_lgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    private void userLogin() {
        final String phone = lph.getText().toString();
        final String password = lpsw.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            lph.setError("Please enter your phone");
            lph.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            lpsw.setError("Please enter your password");
            lpsw.requestFocus();
            return;
        }
        class UserLogin extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject userJson = obj.getJSONObject("user");
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("phone"),
                                userJson.getString("password"),
                                userJson.getString("name")
                        );
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        finish();
                        startActivity(new Intent(getApplicationContext(), homepage.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}