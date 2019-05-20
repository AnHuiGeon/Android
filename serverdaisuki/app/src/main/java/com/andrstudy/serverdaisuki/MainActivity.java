package com.andrstudy.serverdaisuki;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        try {
            URL url = new URL("http://ip:port/context");
            new Task(this).execute(url);    //doInBackground로 보낸다. 그것도 배열로다가!
        }catch (MalformedURLException e) {

        }
    }
    class Task extends AsyncTask<URL, Integer, String> {    // 첫 번째, 두 번째는 취향탄다~~~ 하지만 세 번째는 거~~~~~~의 string으로 온다.
        private WeakReference<MainActivity> activityReference;  // 하단 mainactivity를 finish했을 때 jdk가 가비지컬렉션을 하려면 WeakReference를 해야한다.
        public Task(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override   // 배열임 => 왜? Integer... values => Integer[] values
        protected void onProgressUpdate(Integer... values) {    //doInBackground가 중간중간 publishing 할 때 마다 불려진다.
            super.onProgressUpdate(values);
            if(values.length > 0)
                Log.i("http", String.valueOf(values[0])):
        }

        @Override
        protected void onPreExecute() { //doInBackground가 불리기 전에 불려진다.
            super.onPreExecute();       // 접속 중, 로딩 중, 파일 다운로드 중 등등...
        }

        @Override   // 배열임 => 왜? URL... urls => URL[] urls
        protected String doInBackground(URL... urls) {  // URL[] urls => URL... urls 로 하면 응답 쪽에서 process(
            return null;
            int i = 0;
            String result = new String();
            if(param == nulll || params.length < 1) return null;
            try {
                publishProgress((i ++));
                HttpURLConnection connection = (HttpURLConnection)params[0].openConnection();
                connection.setRequestMent("GET");
                publishingProgress(i++);
                InputStream is = connection.getGroupStream();
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF=8'"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(length+ "\n");
                    publishProgress(i++);
                }
                result = builder.toString(); Log.i (("http", "result" + result));
                publishingProgress(i++);
            }catch (IOException me) {

            }
        }

        @Override   // 결과로 받는 타입
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            MainActivity activity = activityReference.get();    // mainactivit는 뭔가 집같은 존재로 느껴진다...만약 작업(doInBackground)가 일을 마치고 집에 돌아갔는데 집이 철거되어 있는 상태;;...
            if(activity == null || activity.isFinishing()) return;  // doInBackground에서 작업을 하고 왔을 때 mainactivity가 꺼졌는지 꺼지지 않았는지 검사한다.
        }
    }
}
