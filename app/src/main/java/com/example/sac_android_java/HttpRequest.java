package com.example.sac_android_java;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class HttpRequest extends AppCompatActivity {

    TextView httpRequestText;
    Button httpRequestButton;
    Button getHttpRequestButtonClear;

    @SuppressLint("SetTextI18n")   // Added when tried to do set text
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);

        //To Permit to run response in the main code - LEarn Aysnchronous task
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final HttpRequest obj=new HttpRequest();
        httpRequestButton = (Button) findViewById(R.id.httpRequestButton);
        httpRequestText = (TextView) findViewById(R.id.httpRequestText);
        getHttpRequestButtonClear = (Button) findViewById(R.id.httpRequestButtonClear);


        //webView1.loadUrl("http://www.google.com");

        httpRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUrl = "http://www.google.com";
                HashMap<String, String> answer = new HashMap<>();
                answer = obj.requestWebPage(strUrl);
                if (Objects.equals(answer.get("success"), "1")){
                    httpRequestText.setText(answer.get("webpage"));
                }
                else{
                    httpRequestText.setText(answer.get("error"));
                }

            }
        });

        getHttpRequestButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequestText.setText("");
            }
        });
    }

    public HashMap<String, String> requestWebPage(String strUrl){
        HashMap<String, String> output = new HashMap<>();
        try{
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            output.put("success","1");
            output.put("webpage",response.toString());
        } catch(IOException e){
            output.put("success","0");
            output.put("error",e.toString());
        } catch (Exception e){
            output.put("success","0");
            output.put("error",e.toString());
        }
        return output;
    }
}
