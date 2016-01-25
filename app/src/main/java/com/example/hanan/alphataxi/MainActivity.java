package com.example.hanan.alphataxi;


import android.annotation.TargetApi;
import android.app.ActionBar;
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
import android.widget.TextClock;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button_login,talk,exit;
    boolean la,dailog;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    public static int backpress=0;
//        TextClock mTextClock;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
//        mTextClock=(TextClock) findViewById(R.id.textClock2);
//        mTextClock.setFormat24Hour("MMM dd, yyyy k:mm");
       la= preferences.getBoolean("Status",false);
        dailog= preferences.getBoolean("dailog",false);

        if (la){

            // Save the state
            startActivity(new Intent(this,Dashboard.class));


        }
        else {

            if (!dailog) {
                editor.putBoolean("dailog", true);
                editor.apply();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("Warning");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Please do not operate this application while vehicle is in motion.!\n" +
                                "Obey safety laws. Keep your eyes on the road !")

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

                        if(alertDialog.isShowing()){alertDialog.dismiss();}
                    }
                }.start();
            }
        }
        talk=(Button)findViewById(R.id.call_btn);
        talk.setOnClickListener(this);
        button_login =(Button)findViewById(R.id.button);
        button_login.setOnClickListener(this);
        exit=(Button)findViewById(R.id.button8);
        exit.setOnClickListener(this);
    }



    public void onClick(View view)
    {

        switch(view.getId())
        {
            case R.id.button:

                view.startAnimation(buttonClick);
                startActivity(new Intent(this,Login.class));


                break;
            case R.id.call_btn:
                view.startAnimation(buttonClick);

                break;
            case R.id.button8:
                view.startAnimation(buttonClick);
                moveTaskToBack(true);
                break;
        }

    }

    public void onBackPressed(){
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();


        if (backpress>1) {
//            System.exit(1);
            moveTaskToBack(true);

        }


    }

}
