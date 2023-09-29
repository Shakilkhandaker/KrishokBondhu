package com.example.krishokbondhu;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class registrationpage extends AppCompatActivity {
    TextView login_page,ph,psw;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);

        login_page = findViewById(R.id.log_in);
        ph = findViewById(R.id.text3);
        psw = findViewById(R.id.text5);
        reg = findViewById(R.id.btn_reg);
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, homepage.class));
//            return;
//        }
        login_page.setOnClickListener(view -> {
            Intent intent_log = new Intent(registrationpage.this,loginpage.class);
            startActivity(intent_log);
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        final String phone    = ph.getText().toString().trim();
        final String password = psw.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ph.setError("Please enter Phone");
            ph.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ph.setError("Please enter your password");
            ph.requestFocus();
            return;
        }
        class RegisterUser extends AsyncTask<Void, Void, String> {
            //private ProgressBar progressBar;
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
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
                                userJson.getString("")
                        );
                        //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        //finish();
                        startActivity(new Intent(getApplicationContext(), loginpage.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}