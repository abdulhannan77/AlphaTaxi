package com.example.hanan.alphataxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.example.hanan.alphataxi.R;

/**
 * Created by hanan on 10/7/2015.
 */
public class Dashboard extends Activity implements View.OnClickListener {

    Button back_btn,log_out,busy_btn,bid_btn,zone_btn,call,walking,map,cummunication;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_dashboard);

        log_out=(Button)findViewById(R.id.button2);
       // back_btn=(Button)findViewById(R.id.button4);
        log_out.setOnClickListener(this);
      //  back_btn.setOnClickListener(this);
        busy_btn=(Button)findViewById(R.id.busy_btn);
        busy_btn.setOnClickListener(this);
        bid_btn=(Button)findViewById(R.id.bid_btn);
        bid_btn.setOnClickListener(this);
        zone_btn=(Button)findViewById(R.id.zone_btn);
        zone_btn.setOnClickListener(this);
        call=(Button)findViewById(R.id.call_btn);
        call.setOnClickListener(this);
        walking=(Button)findViewById(R.id.button7);
        walking.setOnClickListener(this);
        map=(Button)findViewById(R.id.map);
        map.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.button4:
//                startActivity(new Intent(this,MainActivity.class));
//                break;
            case R.id.button2:
                v.startAnimation(buttonClick);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("ALert");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are You Sure You Want To Logout !")

                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();

                            }
                        })
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("Status", false);
                                editor.apply();
                                startActivity(new Intent(Dashboard.this,MainActivity.class));
                            }
                        })
                ;

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                Button positive_button =  alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (positive_button != null) {

                    positive_button.setTextColor(this.getResources()
                            .getColor(android.R.color.holo_red_dark));
                }
                Button negative_button =  alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if(negative_button != null) {

                    positive_button.setTextColor(this.getResources()
                            .getColor(android.R.color.holo_green_dark));
                }

                break;
            case R.id.busy_btn:
                v.startAnimation(buttonClick);

                startActivity(new Intent(this,Busy.class));
                break;
            case R.id.zone_btn:
                v.startAnimation(buttonClick);
                break;
            case R.id.bid_btn:
                v.startAnimation(buttonClick);
                startActivity(new Intent(Dashboard.this, Book.class));
                break;
            case R.id.call_btn:
                v.startAnimation(buttonClick);
                break;
            case R.id.button7:
//                v.startAnimation(buttonClick);
//                Intent rec = new Intent(Dashboard.this, RecyclerViewHorizontal.class);
//                rec.putExtra("price",100);
//                rec.putExtra("time", 10*0.1);
//                    startActivity(rec);
                startActivity(new Intent(Dashboard.this, SearchDestination.class));
                break;
            case R.id.map:
                v.startAnimation(buttonClick);
                startActivity(new Intent(Dashboard.this, Map_NoDestination.class));
                break;


        }
    }

    public void onBackPressed(){



            moveTaskToBack(true);

        }

    }