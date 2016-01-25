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
public class Book extends Activity implements View.OnClickListener{

Button accept,back,logout,reject;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);
        accept=(Button)findViewById(R.id.accept_btn);
        accept.setOnClickListener(this);
        back=(Button)findViewById(R.id.back_btn);
        back.setOnClickListener(this);
        logout=(Button)findViewById(R.id.logout_btn);
        logout.setOnClickListener(this);
        reject=(Button)findViewById(R.id.reject_btn);
        reject.setOnClickListener(this);
    }

        @Override
    public void onClick(View v) {
            switch (v.getId())
            {
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
                                    startActivity(new Intent(Book.this,MainActivity.class));
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
                case R.id.accept_btn:
                    v.startAnimation(buttonClick);
                    startActivity(new
                            Intent(Book.this, PickUp.class));
                    break;
                case R.id.back_btn:
                    v.startAnimation(buttonClick);
                    startActivity(new Intent(Book.this, Dashboard.class));
                    break;
                case R.id.reject_btn:
                    v.startAnimation(buttonClick);
                    startActivity(new Intent(Book.this, Dashboard.class));
                    break;


            }

    }
}
