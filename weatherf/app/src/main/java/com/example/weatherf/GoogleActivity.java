package com.example.weatherf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleActivity extends Activity {

    private SpeechRecognizer speech;
    TextView vw;
    ArrayList<String> text;

    private final int RESULT_SPEECH = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_google);
        Intent intent = getIntent();

        text = intent.getExtras().getStringArrayList("region");
        String name = text.get(0);
        Log.d("TAG" , "tttttttttttttttttttttttttttttttttttttttttttt" + name);
        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        // Initialize the AWSMobileClient if not initialized
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("TAG", "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());
                downloadWithTransferUtility(name);

            }

            @Override
            public void onError(Exception e) {
                Log.e("TAG", "Initialization error.", e);
            }
        });


    }
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        //액티비티(팝업) 닫기
        finish();
    }



    private void downloadWithTransferUtility(String s) {
        System.out.println("ssssssssssssssssssssssssss");
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();
        File file = new File(getApplicationContext().getFilesDir(), "download3");
        TransferObserver downloadObserver =
                transferUtility.download(
                        "public/"+s+"/data.txt",
                        file);


        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                TextView region =(TextView)findViewById(R.id.region);
                TextView one = findViewById(R.id.a);
                TextView two = findViewById(R.id.b);
                TextView three = findViewById(R.id.c);
                TextView four = findViewById(R.id.d);
                TextView five = findViewById(R.id.e);
                ImageView six = findViewById(R.id.f);
                ImageView seven = findViewById(R.id.g);
                ImageView eight = findViewById(R.id.h);
                ImageView nine = findViewById(R.id.i);
                ImageView ten = findViewById(R.id.j);

                if (TransferState.COMPLETED == state) {

                    Log.d("TAG" , "ffffffffffffffffffffffffffffffffffffffffff"+file );







                    try {

                        BufferedReader buf = new BufferedReader(new FileReader(file));
                        String line = null;
                        ArrayList<String> tv = new ArrayList<>();
                        while((line=buf.readLine())!=null){

                            tv.add(line);

                        }

                        Log.d("TAG" , "ffffffffffffffffffffffffffffffffffffffffff"+tv.toString() );
                        buf.close();
                        String location=tv.get(0);
                        String today=tv.get(1);
                        String next_day=tv.get(2);
                        String nnext_day=tv.get(3);
                        String nnnext_day=tv.get(4);
                        String nnnnext_day=tv.get(5);
                        String[] today_arr = today.split(",");
                        String[] next_day_arr = next_day.split(",");
                        String[] nnext_day_arr = nnext_day.split(",");
                        String[] nnnext_day_arr = nnnext_day.split(",");
                        String[] nnnnext_day_arr = nnnnext_day.split(",");
                        region.setText(location);
                        one.setText(today_arr[0]+today_arr[1]+today_arr[2]+today_arr[3]);
                        two.setText(next_day_arr[0]+next_day_arr[1]+next_day_arr[2]+next_day_arr[3]);
                        three.setText(nnext_day_arr[0]+nnext_day_arr[1]+nnext_day_arr[2]+nnext_day_arr[3]);
                        four.setText(nnnext_day_arr[0]+nnnext_day_arr[1]+nnnext_day_arr[2]+nnnext_day_arr[3]);
                        five.setText(nnnnext_day_arr[0]+nnnnext_day_arr[1]+nnnnext_day_arr[2]+nnnnext_day_arr[3]);
                        Glide.with(GoogleActivity.this).load(today_arr[4]).into(six);
                        Glide.with(GoogleActivity.this).load(next_day_arr[4]).into(seven);
                        Glide.with(GoogleActivity.this).load(nnext_day_arr[4]).into(eight);
                        Glide.with(GoogleActivity.this).load(nnnext_day_arr[4]).into(nine);
                        Glide.with(GoogleActivity.this).load(nnnnext_day_arr[4]).into(ten);









                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("Your Activity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {

                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == downloadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("Your Activity", "Bytes Transferred: " + downloadObserver.getBytesTransferred());
        Log.d("Your Activity", "Bytes Total: " + downloadObserver.getBytesTotal());
    }
}

