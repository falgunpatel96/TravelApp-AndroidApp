package com.dal.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapterTicket extends RecyclerView.Adapter<RViewAdapterTicket.ViewHolder> {

    ArrayList<Ticket> tickets;
    Context context;
    onClickListener listener;
    //final int LAUNCH_SECOND_ACTIVITY= 100;

    public RViewAdapterTicket(Context context, ArrayList<Ticket> tickets, onClickListener listener) {
        this.context = context;
        this.tickets = tickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    //inflate the layout for the view of every item in the Recycler View
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ticket, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //Binding the data on views
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //flight_name,flight_code,depart_plc,depart_time,destination_plc,destination_time,pls_day,totl_hour,no_stops,price;

        if (tickets.size() > 0) {
            final Ticket singleTicket = tickets.get(position);

            holder.order_no.setText(singleTicket.getOrder_no());
            holder.dateTime.setText(singleTicket.getDate_Time());
            holder.amount.setText(singleTicket.getAmount());
            Glide.with(context)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.airplane24))
                    .load(singleTicket.getFlight_logo())
                    .into(holder.compny_logo);
//            holder.compny_logo.setImageResource(R.drawable.indigo_logo);
            holder.flight_name.setText(singleTicket.getFlight_name());
            holder.flight_code.setText(singleTicket.getFlight_code());

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClickListener(holder.getAdapterPosition(), tickets.get(holder.getAdapterPosition()));
                    }
                }
            });

            holder.whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        listener.onLinerLayoutClick(holder.getAdapterPosition(), tickets.get(holder.getAdapterPosition()));
                    }
                }
            });
        }

    }


    @Override
    //number of recyclerview required
    public int getItemCount() {
        return tickets.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView compny_logo;
        TextView order_no, dateTime, amount, flight_name, flight_code, depart_plc, depart_time, destination_plc, destination_time, pls_day, totl_hour, no_stops, price;
        Button download;
        LinearLayout whole;

        public ViewHolder(View itemView) {
            super(itemView);

            order_no = (TextView) itemView.findViewById(R.id.order_no);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            amount = (TextView) itemView.findViewById(R.id.amount);
            compny_logo = (ImageView) itemView.findViewById(R.id.compny_logo);
            flight_name = (TextView) itemView.findViewById(R.id.flight_name);
            flight_code = (TextView) itemView.findViewById(R.id.flight_code);
            download = (Button) itemView.findViewById(R.id.download);
            whole = (LinearLayout) itemView.findViewById(R.id.whole);
        }

    }

    public void doRefresh(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    public interface onClickListener {
        void onItemClickListener(int position, Ticket singleTicket);
        void onLinerLayoutClick(int position, Ticket singleTicket);
    }
}
