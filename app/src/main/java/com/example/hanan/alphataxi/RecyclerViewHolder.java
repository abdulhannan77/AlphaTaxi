package com.example.hanan.alphataxi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hanan on 1/13/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView userName;


    public RecyclerViewHolder(View itemView){
        super(itemView);
        //implementing onClickListener
        itemView.setOnClickListener(this);
        userName = (TextView)itemView.findViewById(R.id.user_name);

    }

    @Override
    public void onClick(View view) {
        //Every time you click on the row toast is displayed
        Toast.makeText(view.getContext(), "You got a call from " + userName.getText() + "\n", Toast.LENGTH_LONG).show();
    }
}