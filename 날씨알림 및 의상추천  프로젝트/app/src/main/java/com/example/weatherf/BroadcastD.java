package com.example.weatherf;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    private static final String TAG = WeatherActivity.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        String str;
        str=GPS(context);
        System.out.println("str :"+str);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        testAlarm(context,pendingIntent,intent);
        AWSMobileClient.getInstance().initialize(context, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(TAG, "AWSMobileClient initialized. User State is " + userStateDetails.getUserState());
                downloadWithTransferUtility(context,str,pendingIntent);
            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Initialization error.", e);
            }
        });

        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        //System.out.println("이함수가 실행이 되었습니다111.");

        //((MainActivity)MainActivity.mContext).Alarm_next();
    }
    public void testAlarm(Context context, PendingIntent pendingIntent, Intent intent){
        //Intent intent = new Intent("AlarmService");
        PendingIntent sender = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10*1000; //10초 후 알람 이벤트 발생
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1,calendar.get(Calendar.HOUR_OF_DAY) ,
                calendar.get(Calendar.MINUTE), 00);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //API 19 이상 API 23미만
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent) ;
            } else {
                //API 19미만
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else {
            //API 23 이상
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public String GPS(Context context){
        System.out.println("GPS실행");
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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

            final Geocoder geocoder = new Geocoder(context);
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
            if (list != null) {
                if(list.size()==0)
                    System.out.println("해당주소가 없습니다.");
                else{
                    System.out.println("리스트 : " +list.get(0));
                    if(!list.get(0).getAdminArea().equals("경기도")) {
                        String string=list.get(0).getAdminArea().substring(0,2);
                        return(string);
                    }else{
                        String string=list.get(0).getLocality().substring(0,2);
                        return(string);
                    }
                }
            }

        }else{
            System.out.println("라스트지역이 없습니다");
        }



        System.out.println("ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ");
        return null;
    }
    private void downloadWithTransferUtility(Context context,String str,PendingIntent pendingIntent){
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();
        File file = new File(context.getFilesDir(), "download.txt");
        String string=null;
        if(str==null)
            string= "public/" + "서울" +"/data.txt";
        else
            string= "public/" + str +"/data.txt";
        System.out.println("지나감 : " + str);
        TransferObserver downloadObserver =
                transferUtility.download(
                        string,
                        file);

        System.out.println("지나감22");
        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    ArrayList<String> arr_str=new ArrayList<>();
                    try {
                        BufferedReader buf = new BufferedReader(new FileReader(file));
                        String line = null;
                        ArrayList<String> tv = new ArrayList<>();

                        while((line=buf.readLine())!=null){
                            arr_str.add(line);
                            arr_str.add("\n");
                            tv.add(line);
                        }

                        System.out.println("ffffffffffffffffffffffffffffffffffffffffff"+tv.toString());
                        buf.close();
                        String location=tv.get(0);
                        String today=tv.get(1);

                        String[] today_arr = today.split(",");
                        System.out.println("array_list22 : " + today);
                        System.out.println("array_list2233 : " + today_arr[0]);
                        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notificationmanager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                        }
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default");
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("지역: " +location)
                                .setContentText("오늘의 날씨: 기온 " + today_arr[1] +"/"+today_arr[2]+", "+today_arr[3] +", 강수량 "+today_arr[5].replace('-','0'))
                                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

                        notificationmanager.notify(1, builder.build());


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
    private final LocationListener mLocationListener = new LocationListener() {
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