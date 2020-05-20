package com.dal.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ViewHolder> {

    ArrayList<Booking> bookingOption;
    Context context;
    onClickListener listener;
    //final int LAUNCH_SECOND_ACTIVITY= 100;

    public RViewAdapter(Context context, ArrayList<Booking> bookingOption, onClickListener listener) {
        this.context = context;
        this.bookingOption = bookingOption;
        this.listener = listener;
    }

    @NonNull
    @Override
    //inflate the layout for the view of every item in the Recycler View
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //Binding the data on views
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //flight_name,flight_code,depart_plc,depart_time,destination_plc,destination_time,pls_day,totl_hour,no_stops,price;

        if (bookingOption.size() > 0) {
            final Booking bookingoptions = bookingOption.get(position);

//            holder.compny_logo.setImageResource(R.drawable.indigo_logo);
//            Toast.makeText(context,bookingoptions.getFlight_logo(),Toast.LENGTH_LONG).show();
            Glide.with(context).load(bookingoptions.getFlight_logo()).into(holder.compny_logo);
            holder.flight_name.setText(bookingoptions.getFlight_name());
            holder.flight_code.setText(bookingoptions.getFlight_code());
            holder.depart_plc.setText(bookingoptions.getDepart_plc());
            holder.depart_time.setText(bookingoptions.getDepart_time());
            holder.destination_plc.setText(bookingoptions.getDestination_plc());
            holder.destination_time.setText(bookingoptions.getDestination_time());
            holder.pls_day.setText(bookingoptions.getPls_day());
            holder.totl_hour.setText(bookingoptions.getTotl_hour());
            holder.no_stops.setText(bookingoptions.getNo_stops());
            holder.price.setText(bookingoptions.getPrice());

            holder.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClickListener(holder.getAdapterPosition(), bookingOption.get(holder.getAdapterPosition()));
                    }
                }
            });
//            holder.whole.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(context, Payment.class);
////                    context.startActivity(intent);
//
//                    Boolean logged_in = Save.readStatus(context, new Boolean("false"));
//                    Toast.makeText(context, "" + logged_in, Toast.LENGTH_LONG).show();
//                    if (!logged_in) {
//
//                        Intent intent = new Intent(context, Login.class);
//                        ((Activity)context).startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
//
//                        Toast.makeText(context, "User not Logged In!", Toast.LENGTH_LONG).show();
//                        logged_in = Boolean.valueOf(Save.readStatus(context, new Boolean("false")));
//                        Log.d("bol", logged_in + "");
//                    } else {
//                        Intent intent = new Intent(context, Details.class);
//                        intent.putExtra("adult_no",adult_count);
//                        intent.putExtra("child_no",child_count);
//                        intent.putExtra("infant_no",infant_count);
//                        ((Activity)context).startActivity(intent);
//                    }
//                    //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
//
//                }
//            });

            holder.whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        listener.onItemClickListener(holder.getAdapterPosition(), bookingOption.get(holder.getAdapterPosition()));
                    }
                }
            });

        }

    }

//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if(requestCode == LAUNCH_SECOND_ACTIVITY)
//        {
//            Boolean logged_in = Save.readStatus(context, new Boolean("false"));
//
//            if (resultCode == Activity.RESULT_OK && logged_in)
//            {
//                Intent intent = new Intent(context, Details.class);
//                ((Activity)context).startActivity(intent);
//            }
//            else if (resultCode == Activity.RESULT_CANCELED || !logged_in)
//            {
//                Intent intent = new Intent(context, Login.class);
//                ((Activity)context).startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
//                Toast.makeText(context,"User not Logged In!",Toast.LENGTH_LONG).show();
//                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
//                //Log.d("bol",logged_in+"");
//            }
//        }
//    }


    @Override
    //number of recyclerview required
    public int getItemCount() {
        return bookingOption.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView compny_logo;
        LinearLayout whole;
        TextView flight_name, flight_code, depart_plc, depart_time, destination_plc, destination_time, pls_day, totl_hour, no_stops, price;
        Button book;

        public ViewHolder(View itemView) {
            super(itemView);

            compny_logo = (ImageView) itemView.findViewById(R.id.compny_logo);
            flight_name = (TextView) itemView.findViewById(R.id.flight_name);
            flight_code = (TextView) itemView.findViewById(R.id.flight_code);
            depart_plc = (TextView) itemView.findViewById(R.id.depart_plc);
            depart_time = (TextView) itemView.findViewById(R.id.depart_time);
            destination_plc = (TextView) itemView.findViewById(R.id.destination_plc);
            destination_time = (TextView) itemView.findViewById(R.id.destination_time);
            pls_day = (TextView) itemView.findViewById(R.id.pls_day);
            totl_hour = (TextView) itemView.findViewById(R.id.destination_time);
            no_stops = (TextView) itemView.findViewById(R.id.no_stops);
            price = (TextView) itemView.findViewById(R.id.price);
            book = (Button) itemView.findViewById(R.id.book);
            whole = (LinearLayout) itemView.findViewById(R.id.whole);
        }

    }

    public void doRefresh(ArrayList<Booking> bookingoptions) {
        this.bookingOption = bookingoptions;
        notifyDataSetChanged();
    }

    public interface onClickListener {
        void onItemClickListener(int position, Booking bookingOptions);
    }


}
