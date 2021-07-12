package com.example.weatherf;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    LinearLayout alarm;
    LinearLayout search;
    LinearLayout weather;
    LinearLayout recommend;
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private static int ONE_MINUTE = 5626;
    public static int a=10;
    public static ArrayList<String> arr_str = new ArrayList<>();
    public static Context mContext;
    private static int REQUEST_ACCESS_FINE_LOCATION = 1000;
    public String number=null;
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        this.InitializeView();
        this.SetListener();
        mContext =this;
        //Alarm(0);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); //위치권한 탐색 허용 관련 내용
            }
            return;
        }
        str = GPS();
           /* if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                return;
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                return;
            }*/

        //GPS();



        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        // Initialize the AWSMobileClient if not initialized
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("TAG", "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());



                downloadWithTransferUtility(str);

            }

            @Override
            public void onError(Exception e) {
                Log.e("TAG", "Initialization error.", e);
            }
        });

        System.out.println("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
    }
    public void InitializeView()
    {
        alarm = (LinearLayout) findViewById(R.id.alarm1);

        search = (LinearLayout) findViewById(R.id.search1);
        recommend = (LinearLayout) findViewById(R.id.recommend1);
    }
    public void SetListener(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getBaseContext() , mapactivity.class);
                startActivity(intent);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( getBaseContext() , Notification_Service.class);
                startActivity(intent);

            }
        });
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),recom.class);
                intent.putExtra("number",number);
                startActivity(intent);
            }
        });


    }
    private void downloadWithTransferUtility(String s){
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();
        File file = new File(getApplicationContext().getFilesDir(), "download.txt");

        String string=null;
        System.out.println(s);
        if(s==null)
            string= "public/" + "고양" +"/data2.txt";
        else
            string= "public/" + str +"/data2.txt";
        Log.d("TAG" , s+"ssssssssssssssssssssssssss");


        TransferObserver downloadObserver =
                transferUtility.download(
                        string,
                        file);

        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    TextView textView = (TextView) findViewById(R.id.textView) ;//내일

                    TextView textView2 = (TextView) findViewById(R.id.textView2) ;//2일후

                    TextView textView3 = (TextView) findViewById(R.id.textView3) ;//3일후

                    TextView textView5 = (TextView) findViewById(R.id.textView5) ;//4일후

                    TextView textView6 = (TextView) findViewById(R.id.textView6) ;//오늘

                    TextView textView7 = (TextView) findViewById(R.id.textView7) ;//위치

                    TextView textView8 = (TextView) findViewById(R.id.textView8) ;//내일

                    TextView textView9 = (TextView) findViewById(R.id.textView9) ;//2일후

                    TextView textView10 = (TextView) findViewById(R.id.textView10) ;//3일후

                    TextView textView12 = (TextView) findViewById(R.id.textView12) ;//4일후
                    try {
                        BufferedReader buf = new BufferedReader(new FileReader(file));
                        String line = null;
                        ArrayList<String> tv = new ArrayList<>();

                        while((line=buf.readLine())!=null){

                            tv.add(line);

                        }

                        System.out.println("ffffffffffffffffffffffffffffffffffffffffff"+tv.toString());
                        buf.close();
                        String location=tv.get(0);
                        String today=tv.get(1);
                        String next_day=tv.get(2);
                        String nnext_day=tv.get(3);
                        String nnnext_day=tv.get(4);
                        String nnnnext_day=tv.get(5);
                        number=tv.get(7);
                        System.out.println("넘버");
                        System.out.println(number);
                        String[] today_arr = today.split(",");
                        String[] next_day_arr = next_day.split(",");
                        String[] nnext_day_arr = nnext_day.split(",");
                        String[] nnnext_day_arr = nnnext_day.split(",");
                        String[] nnnnext_day_arr = nnnnext_day.split(",");

                        ImageView ivImage = findViewById(R.id.imageView);
                        Glide.with(WeatherActivity.this).load(today_arr[4]).into(ivImage);

                        ImageView ivImage2 = findViewById(R.id.imageView2);
                        Glide.with(WeatherActivity.this).load(next_day_arr[4]).into(ivImage2);

                        ImageView ivImage3 = findViewById(R.id.imageView3);
                        Glide.with(WeatherActivity.this).load(nnext_day_arr[4]).into(ivImage3);

                        ImageView ivImage4 = findViewById(R.id.imageView4);
                        Glide.with(WeatherActivity.this).load(nnnext_day_arr[4]).into(ivImage4);

                        ImageView ivImage5 = findViewById(R.id.imageView5);
                        Glide.with(WeatherActivity.this).load(nnnnext_day_arr[4]).into(ivImage5);


                        textView.setText(today_arr[0]);
                        textView2.setText(nnext_day_arr[0]) ;

                        textView3.setText(nnnext_day_arr[0]) ;

                        textView5.setText(nnnnext_day_arr[0]) ;

                        textView6.setText(today_arr[1] + "/" + today_arr[2] + "\n강수량" + today_arr[5].replace('-','0')) ;

                        textView7.setText(location + "\n오늘") ;

                        textView8.setText(next_day_arr[1] + "/" + next_day_arr[2]+ "\n강수량" + next_day_arr[5].replace('-','0')) ;

                        textView9.setText(nnext_day_arr[1] + "/" + nnext_day_arr[2]+"\n강수량" + nnext_day_arr[5].replace('-','0')) ;

                        textView10.setText(nnnext_day_arr[1] + "/" +nnnext_day_arr[2]+"\n강수량" + nnnext_day_arr[5].replace('-','0')) ;

                        textView12.setText(nnnnext_day_arr[1] + "/" +nnnnext_day_arr[2]+"\n강수량" + nnnnext_day_arr[5].replace('-','0')) ;
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

                System.out.println("Your Activity ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {

                System.out.println("Your Activity :ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ");

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
    public String GPS(){
        mContext =this;
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            System.out.println("수신중..");
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);

        }catch(SecurityException ex){
        }
        Location lastKnownLocation =lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocation !=null){
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();
            lng= Math.round(lng*10000)/10000.0;
            lat= Math.round(lat*10000)/10000.0;
            System.out.println("위도 : " + lng + " 경도 :" + lat);

            final Geocoder geocoder = new Geocoder(mContext);
            List<Address> list=null;
            try {
                list = geocoder.getFromLocation(
                        lat, // 위도
                        lng , // 경도
                        2); // 얻어올 값의 개수
            } catch (IOException e) {

                System.out.println("지오코딩 오류");
                e.printStackTrace();

            }
            System.out.println(list.get(0));
            System.out.println(list.get(0).getAdminArea());
            System.out.println(list.get(0).getLocality());
            //System.out.println(list.get(0));
            if (list != null) {
                if(list.size()==0)
                    System.out.println("해당주소가 없습니다.");
                else{
                    //System.out.println(list.get(0));
                    // System.out.println("여기:"+list.get(0).getAdminArea());
                    if(!list.get(0).getAdminArea().equals("경기도")) {
                        String string=list.get(0).getAdminArea().substring(0,2);
                        return(string);
                    }else{
                        String string=list.get(0).getLocality().substring(0,2);
                        return(string);
                    }
                }
            }
        }
        return null;
    }private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            System.out.println("위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };


}