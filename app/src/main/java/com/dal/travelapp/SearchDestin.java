package com.dal.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchDestin extends AppCompatActivity implements RViewAdapterSearch.onClickListener {

    EditText search_desti;
    Button searchDestiBtn;
    RecyclerView rvLocations;
    RViewAdapterSearch rViewAdapterSearch;
    ArrayList<Location> alLocations;
    private RequestQueue queue;
    //ArrayAdapter<String> placesAdapter,destinationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destin);

        alLocations = new ArrayList<Location>();

        searchDestiBtn = (Button) findViewById(R.id.searchDestiBtn);
//        searchFlightBtn = (Button)findViewById(R.id.searchFlightBtn);
        search_desti = (EditText) findViewById(R.id.search_desti);
        queue = Volley.newRequestQueue(getApplicationContext());
        rvLocations = (RecyclerView) findViewById(R.id.rv_destination);
        rvLocations.setLayoutManager(new LinearLayoutManager(rvLocations.getContext()));
        if (rViewAdapterSearch == null) {
            rViewAdapterSearch = new RViewAdapterSearch(alLocations, getApplicationContext(), SearchDestin.this);
        }

        rvLocations.setAdapter(rViewAdapterSearch);
        rViewAdapterSearch.notifyDataSetChanged();
//        show_places = (ListView)findViewById(R.id.show_places);

        //destinationAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,new String[1]);

//        search_desti.setThreshold(1);
//        search_desti.setAdapter(destinationAdapter);

//        search_desti.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //call api with text enter by user in textView
//                getDestin(search_desti.getText().toString());
//            }
//        });

        searchDestiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] places = getPlacesAtDestin();
//                placesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_autocomplete, places);
//                show_places.setAdapter(placesAdapter);
                getLocations(search_desti.getText().toString());
//                Intent i =new Intent(SearchDestin.this,MainActivity.class);
//                startActivity(i);
            }
        });

//        searchFlightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(SearchDestin.this,MainActivity.class);
//                i.putExtra("destination",search_desti.getText().toString());
//                startActivity(i);
//            }
//        });
    }

//    private void getDestin(String searchKey) {
//        //call api with text enter by user in textView
//        //JSON object return thai aeni size no string araay banavano
//        //for example json object is of size 5
//        String[] dataFromJson = {"Hawai","Halifax","Goa","Paris","Prague"};
//        String[] destinations = new String[5];
//        for (int i=0;i<5;i++)
//        {
//            //ahiya json mathi nakhai devanu
//            destinations[i] = dataFromJson[i];
//        }
//
//        destinationAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_autocomplete,destinations);
//        search_desti.setAdapter(destinationAdapter);
//
//    }

//    private String[] getPlacesAtDestin() {
//        //JSON object return thai aeni size no string araay banavano
//        //for example json object is of size 5
//        String[] placesfromJson = {"Point Pleasant Park","WaterFront","Halifax Citadel National Historixc Site","Halifax Seaport Farmers’ Market","Maritime Museum of the Atlantic"};
//        String[] places = new String[5];
//        for (int i=0;i<5;i++)
//        {
//            //ahiya json mathi nakhai devanu
//            places[i] = placesfromJson[i];
//        }
//        return places;
//
//    }

    private void getLocations(String province) {

        province = province.replace(" ","%20");
//            province = URLEncoder.encode(province, "UTF-8");


//        Toast.makeText(getApplicationContext(),province,Toast.LENGTH_LONG).show();
        String url = "http://54.159.208.79:5002/api/browse?province=" + province;


        JSONObject provincereq = new JSONObject();
        JSONArray param = new JSONArray();
        try {
            provincereq.put("province",province);
            param.put(provincereq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            alLocations.clear();
//                        Toast.makeText(getActivity(),"response: "+response.length(),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);
                                Location locationItem = new Location();

                                locationItem.setId(jo.getInt("_id"));
                                locationItem.setAttraction(jo.getString("name"));
                                locationItem.setCity(jo.getString("city"));
                                locationItem.setImageURL(jo.getString("image"));
//                                locationItem.setDesc(jo.getString("description"));
//                                locationItem.setImageURL(jo.getString("imageURL"));
                                alLocations.add(locationItem);
                            }
                            rViewAdapterSearch.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error Retriving Data", Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(request);
    }


    @Override
    public void onItemClickListener(int position, Location destination) {
        // check if user is logged in
        Intent i = new Intent(SearchDestin.this,MainActivity.class);
        i.putExtra("destination",destination.getCity());
        startActivity(i);
    }
}
