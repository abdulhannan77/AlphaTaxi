package com.example.hanan.alphataxi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Login extends Activity implements View.OnClickListener {


    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    FrameLayout progressBarHolder;
    Button login,back_btn,talk;
    EditText username,password;
    String username1,password1,token;
    String message = "";
    boolean login_1=false;

    String app_url = "http://alphadispatch.azurewebsites.net/Token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_login);

        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(this);
        back_btn=(Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        username= (EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        talk=(Button)findViewById(R.id.button5);
        talk.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                v.startAnimation(buttonClick);
                username1=username.getText().toString();
                password1=password.getText().toString();
                new Signin().execute();
                break;
            case R.id.back_btn:
                v.startAnimation(buttonClick);
                startActivity(new  Intent(Login.this,MainActivity.class));
                break;
            case R.id.button5:
                v.startAnimation(buttonClick);
                break;
        }

    }

    public class Signin extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String,String> map = new HashMap<>();
            map.put("username",username1);
            map.put("password",password1);
            map.put("grant_type","password");

            performPostCall(app_url, map);


            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            login.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            login.setEnabled(true);
//            Toast toast = Toast.makeText(Signin.this , "Successfully Logged in", Toast.LENGTH_LONG);
//            Toast.makeText(Login.this , message, Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);

            // set title
            alertDialogBuilder.setTitle("Error");

            // set dialog message
            alertDialogBuilder
                    .setMessage( message)

                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
            Log.e("response", "post");
            if(login_1) {
                SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Status", true);
                editor.apply();
                startActivity(new Intent(Login.this, Dashboard.class));
            }
        }
    }//Async Task Login Class

    public String  performPostCall(String requestURL, HashMap<String, String> postDataParams) {



        String response = "";
        try {
            //url = new URL(requestURL);
            URL url = new URL("http://alphadispatch.azurewebsites.net/Token");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         //   conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                final JSONObject json = new JSONObject(response.toString());
             //   String s=json.optString("error");
//                if(s.equals("")||s.equals(null)){
////                    JSONObject jsonObject = json.getJSONObject("data");
//                    message = json.getString("error");
//                    Log.e("response",message);
//
//
//                }
//                else{
                    token = json.getString("access_token");
                    Log.e("response",message);
                    message="login in successfully !";
                login_1=true;
                  //  Toast toast = Toast.makeText(Login.this , "Successfully Logged in", Toast.LENGTH_LONG);


//                }
            }
            else {
                Log.e("response","");
                message="Login Credentials Are Not Correct !";
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

        }

        return result.toString();
    }


}