package com.example.krishokbondhu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

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
import android.widget.Filter;
import android.widget.Toast;

public class product extends AppCompatActivity {
    List<ProductDetails> productList;
    RecyclerView recyclerView;


    //User user = SharedPrefManager.getInstance(this).getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();

        loadProdutclist();
    }
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductDetailsViewHolder>{
        private Context mCtx;
        private List<ProductDetails> productList;
        public ProductAdapter(Context mCtx, List<ProductDetails> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }
        @NonNull
        @Override
        public ProductDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.productlist, null);
            return new ProductDetailsViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ProductDetailsViewHolder holder, int position) {
            ProductDetails p1 = productList.get(position);
            holder.product_name.setText(p1.getProduct_name());
            holder.product_quantity.setText(String.valueOf(p1.getProduct_quantity()));
            holder.btn1.setText("এখনই কিনুন \n" +String.valueOf(p1.getProduct_totalprice())+" টাকা ");
            holder.btn2.setText(String.valueOf(p1.getTime_period())+" সময়কাল \n" +
                    String.valueOf(p1.getProduct_price())+" টাকা শুরু করুন");
            holder.btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("p_p_name", p1.getProduct_name());
                    bundle.putString("p_p_time", p1.getTime_period());
                    bundle.putString("p_p_total_price", p1.getProduct_price());
                    bundle.putString("very_important", p1.getProduct_totalprice());
                    bundle.putString("check", "true");
                    Intent intent = new Intent(product.this, payment_page.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b2 = new Bundle();
                    b2.putString("check", "false");
                    b2.putString("p_p_name_2", p1.getProduct_name());
                    b2.putString("p_p_total_price_2", p1.getProduct_totalprice());
                    Intent intent = new Intent(product.this, payment_page.class);
                    intent.putExtras(b2);
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {return productList.size();}
        public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {
            TextView product_name, product_quantity;
            Button btn1, btn2;
            public ProductDetailsViewHolder(View itemView) {
                super(itemView);
                product_name = itemView.findViewById(R.id.product_name);
                product_quantity = itemView.findViewById(R.id.product_quantity);
                btn1 = itemView.findViewById(R.id.btn1);
                btn2 = itemView.findViewById(R.id.btn2);

            }
        }
    }
    private void loadProdutclist() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_PRODUCT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new ProductDetails(
                                        product.getString("product_id"),
                                        product.getString("product_name"),
                                        product.optString("product_price"),
                                        product.optString("product_quantity"),
                                        product.optString("product_totalprice"),
                                        product.getString("time_period")
                                        ));
                            }
                            ProductAdapter adapter = new ProductAdapter(product.this, productList);
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
