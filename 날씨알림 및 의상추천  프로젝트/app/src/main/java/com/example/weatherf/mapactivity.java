package com.example.weatherf;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class mapactivity extends AppCompatActivity implements RecognitionListener {
    private SpeechRecognizer speech;

    ArrayList<String> text;
    private Intent recognizerIntent;
    private final int RESULT_SPEECH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ko-KR"); //언어지정입니다.
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);   //검색을 말한 결과를 보여주는 갯수
                startActivityForResult(recognizerIntent, RESULT_SPEECH);
            }
        });


        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        // Initialize the AWSMobileClient if not initialized
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("TAG", "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());



                            downloadWithTransferUtility();

            }

            @Override
            public void onError(Exception e) {
                Log.e("TAG", "Initialization error.", e);
            }
        });

        System.out.println("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");

    }

    private void downloadWithTransferUtility() {
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
                        "public/z.txt",
                        file);


        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    TextView textView = (TextView) findViewById(R.id.sea) ;//내일

                    TextView textView2 = (TextView) findViewById(R.id.seoul) ;//2일후
                    TextView textView3 = (TextView) findViewById(R.id.gws) ;//2일후
                    TextView textView4 = (TextView) findViewById(R.id.gw) ;//2일후

                    TextView textView5 = (TextView) findViewById(R.id.cn) ;//3일후

                    TextView textView6 = (TextView) findViewById(R.id.cb) ;//4일후

                    TextView textView7 = (TextView) findViewById(R.id.kb) ;//오늘

                    TextView textView8 = (TextView) findViewById(R.id.dd) ;//위치

                    TextView textView9 = (TextView) findViewById(R.id.jn) ;//내일

                    TextView textView10 = (TextView) findViewById(R.id.jb) ;//3일후

                    TextView textView11 = (TextView) findViewById(R.id.kn) ;//4일후
                    TextView textView12 = (TextView) findViewById(R.id.jj) ;//2일후


                   try {

                        BufferedReader buf = new BufferedReader(new FileReader(file));
                        String line = null;
                        ArrayList<String> tv = new ArrayList<>();
                        while((line=buf.readLine())!=null){

                            tv.add(line);

                        }
                       System.out.println("ffffffffffffffffffffffffffffffffffffffffff"+tv.toString());
                       buf.close();
                       Log.d("TAG" , tv.get(0));
                       String loc1=tv.get(0);
                       String loc2=tv.get(1);
                       String loc3=tv.get(2);
                       String loc4=tv.get(3);
                       String loc5=tv.get(4);
                       String loc7=tv.get(5);
                       String loc8=tv.get(6);
                       String loc9=tv.get(7);
                       String loc10=tv.get(8);
                       String loc11=tv.get(9);
                       String loc12=tv.get(10);
                       String loc13=tv.get(11);

                       String[] today_arr = loc1.split(",");
                       String[] next_day_arr = loc2.split(",");
                       String[] nnext_day_arr = loc3.split(",");
                       String[] nnnext_day_arr = loc4.split(",");
                       String[] nnnnext_day_arr = loc5.split(",");
                       String[] arr1 = loc7.split(",");
                       String[] arr2 = loc8.split(",");
                       String[] arr3 = loc9.split(",");
                       String[] arr4 = loc10.split(",");
                       String[] arr5 = loc11.split(",");
                       String[] arr6 = loc12.split(",");
                       String[] arr7 = loc13.split(",");
                       textView.setText("서해 5도"+today_arr[1]);
                       textView2.setText("서울/경기"+next_day_arr[1]);
                       textView3.setText("강원영서"+nnext_day_arr[1]);
                       textView4.setText("강원영동"+nnnext_day_arr[1]);
                       textView5.setText("충청남도"+nnnnext_day_arr[1]);
                       textView6.setText("충청북도"+arr1[1]);
                       textView7.setText("경상북도"+arr2[1]);
                       textView8.setText("울릉/독도"+arr3[1]);
                       textView9.setText("전라남도"+arr4[1]);
                       textView10.setText("전라북도"+arr5[1]);
                       textView11.setText("경상남도"+arr6[1]);
                       textView12.setText("제주"+arr7[1]);



                       ImageView ivImage = findViewById(R.id.sea1);
                       Glide.with(mapactivity.this).load(today_arr[2]).override(30,30).centerCrop().into(ivImage);
                       Log.d("TAG", today_arr[2]);

                       ImageView ivImage2 = findViewById(R.id.seoulview);
                       Glide.with(mapactivity.this).load(next_day_arr[2]).override(10,10).centerCrop().into(ivImage2);
                       Log.d("TAG", next_day_arr[2]);
                       ImageView ivImage3 = findViewById(R.id.gwsv);
                       Glide.with(mapactivity.this).load(nnext_day_arr[2]).override(10,10).into(ivImage3);

                       ImageView ivImage4 = findViewById(R.id.gwv);
                       Glide.with(mapactivity.this).load(nnnext_day_arr[2]).override(30,30).into(ivImage4);

                       ImageView ivImage5 = findViewById(R.id.cnview);
                       Glide.with(mapactivity.this).load(nnnnext_day_arr[2]).into(ivImage5);
                       ImageView ivImage6 = findViewById(R.id.cbview);
                       Glide.with(mapactivity.this).load(arr1[2]).into(ivImage6);
                       ImageView ivImage7 = findViewById(R.id.kbview);
                       Glide.with(mapactivity.this).load(arr2[2]).into(ivImage7);
                       ImageView ivImage8 = findViewById(R.id.ddv);
                       Glide.with(mapactivity.this).load(arr3[2]).into(ivImage8);
                       ImageView ivImage9 = findViewById(R.id.jnview);
                       Glide.with(mapactivity.this).load(arr4[2]).into(ivImage9);
                       ImageView ivImage10 = findViewById(R.id.jbbiew);
                       Glide.with(mapactivity.this).load(arr5[2]).into(ivImage10);
                       ImageView ivImage11 = findViewById(R.id.knv);
                       Glide.with(mapactivity.this).load(arr6[2]).into(ivImage11);
                       ImageView ivImage12 = findViewById(R.id.jjv);
                       Glide.with(mapactivity.this).load(arr7[2]).into(ivImage12);



                       View.OnClickListener listener = new View.OnClickListener()
                       {
                           @Override
                           public void onClick(View v) {



                           }


                       };


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



    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for(int i = 0; i < matches.size() ; i++){
            Log.e("GoogleActivity", "onResults text : " + matches.get(i));
        }

    }

    @Override
    public void onError(int errorCode) {

        String message;

        switch (errorCode) {

            case SpeechRecognizer.ERROR_AUDIO:
                message = "오디오 에러";
                break;

            case SpeechRecognizer.ERROR_CLIENT:
                message = "클라이언트 에러";
                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "퍼미션없음";
                break;

            case SpeechRecognizer.ERROR_NETWORK:
                message = "네트워크 에러";
                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "네트웍 타임아웃";
                break;

            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "찾을수 없음";;
                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "바쁘대";
                break;

            case SpeechRecognizer.ERROR_SERVER:
                message = "서버이상";;
                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "말하는 시간초과";
                break;

            default:
                message = "알수없음";
                break;
        }

        Log.e("GoogleActivity", "SPEECH ERROR : " + message);
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH : {
                if (resultCode == RESULT_OK && null != data) {
                    text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Intent intent = new Intent(getApplicationContext(), GoogleActivity.class);

                    intent.putExtra("region",text); /*송신*/
                    startActivity(intent);



                }

                break;
            }
        }
    }
}