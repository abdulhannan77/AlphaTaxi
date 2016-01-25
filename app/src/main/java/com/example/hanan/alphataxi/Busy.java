package com.example.hanan.alphataxi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by hanan on 10/14/2015.
 */


public class Busy extends Activity implements View.OnClickListener{
    Button eat,other,smoking,rest,gas,other2,airport,back,logout,talk;
    boolean eatf,otherf,smokingf,restf,gasf,airportf,other2f=false;
    boolean all=true;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busy_activity);
        eat=(Button) findViewById(R.id.eat);
        other=(Button) findViewById(R.id.other1);
        smoking=(Button) findViewById(R.id.smoking);
        rest=(Button) findViewById(R.id.rest);
        gas=(Button) findViewById(R.id.gas);
        airport=(Button) findViewById(R.id.airport);
        other2=(Button) findViewById(R.id.other2);
        back=(Button) findViewById(R.id.back_btn);
        talk=(Button)findViewById(R.id.call_btn);
        logout=(Button) findViewById(R.id.logout_btn);
        eat.setOnClickListener(this);
        other.setOnClickListener(this);
        talk.setOnClickListener(this);
        smoking.setOnClickListener(this);
        back.setOnClickListener(this);
        smoking.setOnClickListener(this);
        other.setOnClickListener(this);
        logout.setOnClickListener(this);
        rest.setOnClickListener(this);
        gas.setOnClickListener(this);
        airport.setOnClickListener(this);
        other2.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.eat:

                if(all){

                    eat.setBackgroundResource(R.mipmap.imbck);
                    eatf=true;
                    all=false;
                }
                else
                {
                    if(eatf)
                    {
                        eat.setBackgroundResource(R.mipmap.snacks);
                        eatf=false;
                        all=true;
                    }
                }

                break;
            case R.id.rest:

                if(all){

                    rest.setBackgroundResource(R.mipmap.imbck);
                    restf=true;
                    all=false;
                }
                else
                {
                    if(restf)
                    {
                        rest.setBackgroundResource(R.mipmap.rest_room);
                        restf=false;
                        all=true;
                    }
                }
                break;

            case R.id.gas:

                if(all){

                    gas.setBackgroundResource(R.mipmap.imbck);
                    gasf=true;
                    all=false;
                }
                else
                {
                    if(gasf)
                    {
                        gas.setBackgroundResource(R.mipmap.gas);
                        gasf=false;
                        all=true;
                    }
                }
                break;

            case R.id.airport:

                if(all){

                    airport.setBackgroundResource(R.mipmap.imbck);
                    airportf=true;
                    all=false;
                }
                else
                {
                    if(airportf)
                    {
                        airport.setBackgroundResource(R.mipmap.airport);
                        airportf=false;
                        all=true;
                    }
                }
                break;

            case R.id.other2:

                if(all){

                    other2.setBackgroundResource(R.mipmap.imbck);
                    other2f=true;
                    all=false;
                }
                else
                {
                    if(other2f)
                    {
                        other2.setBackgroundResource(R.mipmap.other);
                        other2f=false;
                        all=true;
                    }
                }
                break;

            case R.id.other1:
                if(all){
                    other.setBackgroundResource(R.mipmap.imbck);
                    otherf=true;
                    all=false;
                }
                else
                {
                    if(otherf)
                    {
                       other.setBackgroundResource(R.mipmap.other);
                        otherf=false;
                        all=true;
                    }
                }
                break;

            case  R.id.smoking:
                if(all){
                   smoking.setBackgroundResource(R.mipmap.imbck);
                    smokingf=true;
                    all=false;
                }
                else
                {
                    if(smokingf)
                    {
                        smoking.setBackgroundResource(R.mipmap.smoking);
                       smokingf=false;
                        all=true;
                    }
                }
                break;

            case R.id.call_btn:
                view.startAnimation(buttonClick);
                break;
            case R.id.logout_btn:
                if(!all)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    // set title
                    alertDialogBuilder.setTitle("Safety ALert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Unpress The Button  !")

                            .setCancelable(false)
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();

                                }
                            })
                    ;

                    // create alert dialog
                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    new CountDownTimer(5000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub

                        }
                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub

                            alertDialog.dismiss();
                        }
                    }.start();

                    }
                else {
                    view.startAnimation(buttonClick);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    // set title
                    alertDialogBuilder.setTitle("ALert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Are You Sure You Want To Logout !")

                            .setCancelable(true)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("Status", false);
                                    editor.apply();

                                    startActivity(new Intent(Busy.this, MainActivity.class));


                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

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
                                .getColor(android.R.color.holo_red_light));
                    }
                    Button negative_button =  alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    if (negative_button != null) {

                        positive_button.setTextColor(this.getResources()
                                .getColor(android.R.color.holo_green_dark));
                    }



                }

                break;
            case R.id.back_btn:
                if(!all)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    // set title
                    alertDialogBuilder.setTitle("Safety ALert");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Unpress The Button  !")

                            .setCancelable(false)
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();

                                }
                            })
                    ;

                    // create alert dialog
                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    new CountDownTimer(5000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub

                        }
                        @Override
                        public void onFinish() {
                            // TODO Auto-generated method stub

                            alertDialog.dismiss();
                        }
                    }.start();
                }
                else {


                    view.startAnimation(buttonClick);
                    startActivity(new Intent(this, Dashboard.class));
                    break;
                }
        }


    }
    public void onBackPressed(){

        if (!all) {
//
AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle(" ALert");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Unpress The Button  !")

                    .setCancelable(false)
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            dialog.cancel();

                        }
                    })
            ;

            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            new CountDownTimer(5000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub

                }
                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                    alertDialog.dismiss();
                }
            }.start();

        }
else
        {
            startActivity(new Intent(this, Dashboard.class));
        }

    }
}
