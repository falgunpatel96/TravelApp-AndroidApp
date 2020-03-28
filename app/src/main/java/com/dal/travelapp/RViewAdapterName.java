package com.dal.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RViewAdapterName extends RecyclerView.Adapter<RViewAdapterName.ViewHolder> {

    Context context;
    final int LAUNCH_SECOND_ACTIVITY = 100;
    int adult_count = 1, child_count, infant_count;

    public RViewAdapterName(Context context, int adult_count, int child_count, int infant_count) {
        this.context = context;
        this.adult_count = adult_count;
        this.child_count = child_count;
        this.infant_count = infant_count;
    }

    @NonNull
    @Override
    //inflate the layout for the view of every item in the Recycler View
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.traveller_name, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //Binding the data on views
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String age_Group="Adult"+(position+1);
        if(position+1 <= adult_count)
        {
            age_Group = "Adult "+(position+1)+":";
        }
        else if(position+1 <= adult_count+child_count)
        {
            age_Group = "Child "+(position+1)+":";
        }
        else
        {
            age_Group = "Infant "+(position+1)+":";
        }
        holder.ageGroup.setText(age_Group);
    }


    @Override
    //number of recyclerview required
    public int getItemCount()
    {
        System.out.println("cnt: "+(adult_count+child_count+infant_count));
        return adult_count + child_count + infant_count;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ageGroup;
        EditText first_name, last_name;

        public ViewHolder(View itemView) {
            super(itemView);

            ageGroup = (TextView) itemView.findViewById(R.id.ageGroup);
            first_name = (EditText) itemView.findViewById(R.id.first_name);
            last_name = (EditText) itemView.findViewById(R.id.last_name);


        }

    }
}
