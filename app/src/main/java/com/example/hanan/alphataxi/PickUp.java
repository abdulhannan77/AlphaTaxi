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

/**
 * Created by hanan on 12/13/2015.
 */
public class PickUp extends Activity implements View.OnClickListener{


    Button back,logout,loaded,map1,map2,noshow,callout;


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup);
        back=(Button)findViewById(R.id.back_btn);
        back.setOnClickListener(this);
        logout=(Button)findViewById(R.id.logout_btn);
        logout.setOnClickListener(this);
        loaded=(Button)findViewById(R.id.loaded_btn);
        loaded.setOnClickListener(this);
        map1=(Button)findViewById(R.id.map1);
        map1.setOnClickListener(this);
        map2=(Button)findViewById(R.id.map2);
        map2.setOnClickListener(this);
        noshow=(Button)findViewById(R.id.noshow_btn);
        callout=(Button)findViewById(R.id.callout_btn);
        noshow.setOnClickListener(this);
        callout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.logout_btn:
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
                                startActivity(new Intent(PickUp.this,MainActivity.class));
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
            case R.id.back_btn:
                v.startAnimation(buttonClick);
                startActivity(new Intent(PickUp.this, Book.class));
                break;

            case R.id.loaded_btn:
                v.startAnimation(buttonClick);
                Intent intent = new Intent(PickUp.this, MapMain.class);
                intent.putExtra("destLatitude",31.441847 );
                intent.putExtra("destLongitude", 74.276056);
                startActivity(intent);
                break;
            case R.id.map1:
                v.startAnimation(buttonClick);
                Intent intent1 = new Intent(PickUp.this, Only_Map.class);
                intent1.putExtra("destLatitude",31.441847 );
                intent1.putExtra("destLongitude",74.276056 );
                startActivity(intent1);
                break;
            case R.id.map2:
                v.startAnimation(buttonClick);
                Intent intent2 = new Intent(PickUp.this, Only_Map.class);
                intent2.putExtra("destLatitude",31.441847 );
                intent2.putExtra("destLongitude",74.276056 );
                startActivity(intent2);
                break;
            case R.id.callout_btn:
                v.startAnimation(buttonClick);

                break;
            case R.id.noshow_btn:
                v.startAnimation(buttonClick);

                break;
        }
    }
}
