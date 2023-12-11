package com.example.volleyqueuedec2023;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txtView;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = findViewById(R.id.textView);
        img = findViewById(R.id.imageView);

        String url1 = "https://upload.wikimedia.org/wikipedia/en/9/99/Ulster_University_Logo.png";
        String url2 = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

        callVolley(url1, url2);
    }

    private void callVolley(String url1, String url2) {
        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest imageRequest = new ImageRequest(url1, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        },0,0,null,null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray hero = obj.getJSONArray("heroes");
                            for (int i = 0; i < hero.length(); i++) {
                                JSONObject heroObject = hero.getJSONObject(i);
                                String name = heroObject.getString("name");
                                String imageurl = heroObject.getString("imageurl");
                                txtView.append("Name: " + name + "\n" + " ImageUrl: " + imageurl + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(imageRequest);
        queue.add(stringRequest);
    }
}