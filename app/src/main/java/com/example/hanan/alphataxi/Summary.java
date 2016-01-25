package com.example.hanan.alphataxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

/**
 * Created by hanan on 12/15/2015.
 */
public class Summary extends Activity implements View.OnClickListener  {


    Button back,logout,credit,account,edit,cash;


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        back=(Button)findViewById(R.id.back_btn);
        back.setOnClickListener(this);
        logout=(Button)findViewById(R.id.logout_btn);
        logout.setOnClickListener(this);
        credit=(Button)findViewById(R.id.credit);
        account=(Button)findViewById(R.id.account_btn);
        edit=(Button)findViewById(R.id.button10);
        cash=(Button)findViewById(R.id.cash_btn);
        credit.setOnClickListener(this);
        edit.setOnClickListener(this);
        credit.setOnClickListener(this);
        account.setOnClickListener(this);
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
                                    startActivity(new Intent(Summary.this,MainActivity.class));
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
                    startActivity(new Intent(Summary.this, Dashboard.class));
                    break;
                case R.id.credit:
                    v.startAnimation(buttonClick);

                    break;
                case R.id.account_btn:
                    v.startAnimation(buttonClick);

                    break;
                case R.id.cash_btn:
                    v.startAnimation(buttonClick);

                    break;
                case R.id.button10:
                    v.startAnimation(buttonClick);

                    break;

            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Summary.this, Dashboard.class));
    }
}
