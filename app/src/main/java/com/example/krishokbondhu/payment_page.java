package com.example.krishokbondhu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class payment_page extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    TextView t1,t2,t3;
    String base_value;
    public String main_value;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        t1 = findViewById(R.id.product_name_payment);
        t2 = findViewById(R.id.product_time_payment);
        t3 = findViewById(R.id.product_value_payment);
        String b;
        button = findViewById(R.id.btn_buy);
        String product_name,product_time,product_price;
        String product_name2,product_time2="এককালীন ক্রয়কৃত",product_price2;
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            b = bundle.getString("check");
            product_name = bundle.getString("p_p_name");
            product_time = bundle.getString("p_p_time");
            product_price = bundle.getString("p_p_total_price");
            main_value    = bundle.getString("very_important");
            if(b.equals("true")){
                t1.setText(product_name);
                t2.setText(product_time);
                t3.setText(product_price);
            }
        }

        Bundle b2 = getIntent().getExtras();
        if (b2 != null) {
            b = bundle.getString("check");
            product_name2 = bundle.getString("p_p_name_2");
            product_price2 = bundle.getString("p_p_total_price_2");
            main_value = product_price2;
            if(b.equals("false")){
                t1.setText(product_name2);
                t2.setText("এককালীন ক্রয়কৃত");
                t3.setText(product_price2);
            }
        }
        String main_value = t3.getText().toString().trim();
        base_value = main_value;
        final Spinner spinner = (Spinner) findViewById(R.id.spinner); //quantity
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);// payment method
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("১ ব্যাগ");
        categories.add("২ ব্যাগ");
        categories.add("৩ ব্যাগ");
        categories.add("৪ ব্যাগ");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner2.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("বিকাশ");
        categories2.add("নগদ");
        categories2.add("উপায়");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_payment();
            }
        });


    }
    private void add_payment() {
         User user = SharedPrefManager.getInstance(this).getUser();
         String info    = "আপনি "+t1.getText().toString().trim()+" কিনেছেন";
         String pay_value = t3.getText().toString().trim(); // ekhon tk deche
         String time_remaining;
         if(t2.getText().toString().trim().equals("এককালীন ক্রয়কৃত")){
            time_remaining = "নগদ";
         }else{
            time_remaining = t2.getText().toString().toString().trim();
         }
         String farmer_serial = String.valueOf(user.getId());
         String sts ;
         String remain_val="0";
         if(pay_value.equals(main_value)){
            sts = "এককালীন ক্রয়কৃত";
            remain_val="0";
         }else{
            sts = "বকেয়া রয়েছে";
            //remain_val = String.valueOf(Math.abs(Integer.parseInt(main_value)-Integer.parseInt(pay_value)));
         }
         String transaction_id;
                                                                                                                                transaction_id="22091930HUvGaQJVFIIRij";
         //Toast.makeText(getApplicationContext()," "+info+"\n"+pay_value+"\n"+farmer_serial+"\n"+sts+"\n"+"\n"+transaction_id, Toast.LENGTH_LONG).show();
        class input_payment extends AsyncTask<Void, Void, String> {
            //private ProgressBar progressBar;
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("info", info);
                params.put("pay_value", pay_value);
                params.put("time_remaining",time_remaining);
                params.put("farmer_serial",farmer_serial);
                params.put("sts",sts);
                params.put("transaction_id",transaction_id);
                return requestHandler.sendPostRequest(URLs.URL_PAYMENT, params);
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
                        JSONObject paymentJson = obj.getJSONObject("payment");
                        Payment payment = new Payment(
                                paymentJson.getString("info_history"),
                                paymentJson.getString("payment_value"),
                                paymentJson.getString("time_value"),
                                paymentJson.getString("farmer_no"),
                                paymentJson.getString("status"),
                                paymentJson.getString("transaction_id")
                        );

                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        input_payment ip = new input_payment();
        ip.execute();
        finish();
        startActivity(new Intent(getApplicationContext(),complete_payment.class));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            // Showing selected spinner item
            if(position==0){
                t3.setText(String.valueOf((position+1)*(Integer.parseInt(base_value))));
            }
            if(position==1){
                t3.setText(String.valueOf((position+1)*Integer.parseInt(base_value)));
            }

            if(position==2){
                t3.setText(String.valueOf((position+1)*Integer.parseInt(base_value)));
            }

            if(position==3){
                t3.setText(String.valueOf((position+1)*Integer.parseInt(base_value)));
            }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

    }

}