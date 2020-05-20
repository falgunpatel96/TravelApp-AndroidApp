package com.dal.travelapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MyTickets extends AppCompatActivity implements RViewAdapterTicket.onClickListener {

    RecyclerView recyclerView;
    RViewAdapterTicket rviewAdapterTicket;
    ArrayList<Ticket> tickets;
    final int LAUNCH_SECOND_ACTIVITY = 1;
    final int DOWNLOAD_TICKET = 2;
    private Ticket selected_Ticket;
    ArrayList<Traveller> travellers;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rview_booking_option);

        tickets = new ArrayList<Ticket>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        queue = Volley.newRequestQueue(getApplicationContext());

        getBookings();
    }

    public void getBookings() {

//        for (int i = 0; i < 5; i++) {
//            Ticket singleTicket = new Ticket();
//
//            singleTicket.setOrder_no("324632t47326");
//            singleTicket.setDate_Time("1 dec 2019");
//            singleTicket.setAmount("Rs. 45000");
//            singleTicket.setFlight_name("IndiGo");
//            singleTicket.setFlight_code("6E-171");
//            singleTicket.setDepart_plc("New Delhi");
//            singleTicket.setDestination_plc("Mumbai");
//            singleTicket.setDepart_time("04:55");
//            singleTicket.setDestination_time("07:05");
//            singleTicket.setPls_day("");
//            singleTicket.setTotl_hour("2h 10m");
//            singleTicket.setNo_stops("Non Stop");
//            singleTicket.setPrice("Rs. 4940");
//
//            ArrayList<Traveller> travellerInfo = new ArrayList<Traveller>();
//            travellerInfo.clear();
//            for (int j = 0; j < 5; j++) {
//                Traveller singleTraveller = new Traveller();
//                singleTraveller.setFirst_name("firstname" + j);
//                singleTraveller.setLast_name("lastname" + j);
//                travellerInfo.add(singleTraveller);
//            }
//            singleTicket.setTravellersInfo(travellerInfo);
//
//            tickets.add(singleTicket);
//        }
//        Log.e("bookings", "bookings Size:" + tickets.size());
//
//        if (rviewAdapterTicket == null) {
//            rviewAdapterTicket = new RViewAdapterTicket(MyTickets.this, tickets, MyTickets.this);
//            recyclerView.setAdapter(rviewAdapterTicket);
//        }
//        if (tickets != null && !tickets.isEmpty()) {
//            rviewAdapterTicket.doRefresh(tickets);
//        }


//            String url = "54.236.253.176:5001/api/flight?source=Toronto&destination=Calgary";
        String url = "http://54.159.158.177:5003/get/" + Save.readEmail(getApplicationContext());

//        String url = "54.236.253.176:5001/api/flight";
        url = url.replace(" ", "%20");
        Log.e("API", url);
//            Booking singleBooking = new Booking();

//            //private String flight_name, flight_code, depart_plc, destination_plc, depart_time, destination_time, pls_day, totl_hour, no_stops, price;
//            singleBooking.setFlight_name("IndiGo");
//            singleBooking.setFlight_code("6E-171");
//            singleBooking.setDepart_plc("New Delhi");
//            singleBooking.setDestination_plc("Mumbai");
//            singleBooking.setDepart_time("04:55");
//            singleBooking.setDestination_time("07:05");
//            singleBooking.setPls_day("");
//            singleBooking.setTotl_hour("2h 10m");
//            singleBooking.setNo_stops("Non Stop");
//            singleBooking.setPrice("Rs. 4940");
//
//            bookings.add(singleBooking);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tickets.clear();
//                            Toast.makeText(getApplicationContext(), "response: " + response.length(), Toast.LENGTH_LONG).show();

                            JSONObject user = response.getJSONObject("user");
                            JSONArray bookngs = user.getJSONArray("bookings");

                            for (int i = 0; i < bookngs.length(); i++) {
                                JSONObject jo = bookngs.getJSONObject(i);

                                Ticket singleTicket = new Ticket();

                                singleTicket.setOrder_no(jo.getString("booking_no"));
                                singleTicket.setDate_Time(jo.getString("date_time"));
                                singleTicket.setAmount(jo.getString("amount"));

                                JSONObject flight_details = jo.getJSONObject("flight_details");

                                singleTicket.setFlight_name(flight_details.getString("flight_name"));
//                                singleTicket.setFlight_code(flight_details.getString("flightcode"));
                                singleTicket.setFlight_logo(flight_details.getString("flight_logo"));

                                singleTicket.setDepart_date(flight_details.getString("departure_date"));
                                singleTicket.setDepart_plc(flight_details.getString("departure_plc"));
                                singleTicket.setDepart_time(flight_details.getString("departure_time"));

                                singleTicket.setDestination_date(flight_details.getString("destination_date"));
                                singleTicket.setDestination_plc(flight_details.getString("destination_plc"));
                                singleTicket.setDestination_time(flight_details.getString("destination_time"));


                                singleTicket.setNo_stops(flight_details.getString("no_stops"));
                                singleTicket.setPls_day(flight_details.getString("pls_day"));
                                //singleBooking.setPrice(jo.getString("price"));
                                singleTicket.setPrice("CAD 500");
                                singleTicket.setTotl_hour(flight_details.getString("total_hour"));
//                                locationItem.setDesc(jo.getString("description"));
//                                singleTicket.setDepart_plc("New Delhi");
//                                singleTicket.setDestination_plc("Mumbai");
//                                singleTicket.setDepart_time("04:55");
//                                singleTicket.setDestination_time("07:05");
//                                singleTicket.setPls_day("");
//                                singleTicket.setTotl_hour("2h 10m");
//                                singleTicket.setNo_stops("Non Stop");
//                                singleTicket.setPrice("Rs. 4940");

                                ArrayList<Traveller> travellerInfo = new ArrayList<Traveller>();
                                travellerInfo.clear();

                                JSONArray travells = jo.getJSONArray("traveller_name");
                                travellerInfo.clear();
                                for (int j = 0; j < travells.length(); j++) {
                                    JSONObject jot = travells.getJSONObject(i);
                                    Traveller singleTraveller = new Traveller();
                                    singleTraveller.setFirst_name(jot.getString("fname"));
                                    singleTraveller.setLast_name(jot.getString("lname"));
                                    travellerInfo.add(singleTraveller);
                                }
                                singleTicket.setTravellersInfo(travellerInfo);

                                tickets.add(singleTicket);

                            }
                            setAdapter();
//                            rviewAdapterTicket.notifyDataSetChanged();
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
        Log.e("tickets", "ticket Size:" + tickets.size());

        /*if (rviewAdapterTicket == null) {
            rviewAdapterTicket = new RViewAdapterTicket(MyTickets.this, tickets, MyTickets.this);
            recyclerView.setAdapter(rviewAdapterTicket);
        }*/
        if (tickets != null && !tickets.isEmpty()) {
            rviewAdapterTicket.doRefresh(tickets);
        }
    }

    private void setAdapter()
    {
        rviewAdapterTicket = new RViewAdapterTicket(MyTickets.this, tickets, MyTickets.this);
        recyclerView.setAdapter(rviewAdapterTicket);
    }

    @Override
    public void onItemClickListener(int position, Ticket singleTicket) {
        selected_Ticket = singleTicket;
        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

        //Toast.makeText(this, "" + logged_in, Toast.LENGTH_LONG).show();
        if (!logged_in) {

            Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MyTickets.this, Login.class);
            startActivityForResult(intent, DOWNLOAD_TICKET);
            //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
            //Log.d("bol", logged_in + "");
        } else {
            Toast.makeText(MyTickets.this, "Ticket Downloaded!", Toast.LENGTH_LONG).show();
            downloadTicket(Save.readEmail(MyTickets.this), singleTicket.getOrder_no());
            //code for downloading ticket
        }
    }

    private void downloadTicket(final String email, final String order_no) {
//        String mUrl= "http://3.85.62.49:5001/getPdf";
        String mUrl = "http://10.0.2.2:5000/getPdf";

        JSONObject object = new JSONObject();
        try {
//                object.put("email_id",email);
//                object.put("booking_no",order_no);
            object.put("email_id", "fan@gmail.com");
            object.put("booking_no", "323030343032313430333333");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, mUrl, object,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response != null) {

                                FileOutputStream outputStream;
                                String name = "ticket" + order_no + ".pdf";
                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                                outputStream.write(response);
                                outputStream.close();
                                Toast.makeText(MyTickets.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);

//        new DownloadFileFromURL().execute(mUrl);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100%           progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
//                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
//            dismissDialog(progress_bar_type);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.pdf";
            // setting downloaded into image view

        }

    }


    @Override
    public void onLinerLayoutClick(int position, Ticket singleTicket) {
        selected_Ticket = singleTicket;
        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

        if (!logged_in) {

            Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MyTickets.this, Login.class);
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
        } else {
            Intent intent = new Intent(MyTickets.this, SingleWholeTicket.class);
            intent.putExtra("ticket_selected", singleTicket);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK && logged_in) {
                Intent intent = new Intent(MyTickets.this, SingleWholeTicket.class);
                intent.putExtra("ticket_selected", selected_Ticket);
                startActivity(intent);

            } else if (resultCode == Activity.RESULT_CANCELED || !logged_in) {
                Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyTickets.this, Login.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        } else if (requestCode == DOWNLOAD_TICKET) {
            if (resultCode == Activity.RESULT_OK && logged_in) {
                Toast.makeText(MyTickets.this, "Ticket Downloaded!", Toast.LENGTH_LONG).show();
                //code for downloading ticket
            } else if (resultCode == Activity.RESULT_CANCELED || !logged_in) {
                Toast.makeText(this, "User not Logged In!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MyTickets.this, Login.class);
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        }
    }
}
