package com.example.githubclock;

import android.app.*;import android.os.*;import android.graphics.Color;import android.view.*;import android.widget.*;import java.net.*;import java.io.*;import java.text.*;import java.util.*;import java.util.concurrent.*;

public class MainActivity extends Activity {
  TextView clock,status,detail; long baseMillis=0, baseElapsed=0; final Handler h=new Handler(Looper.getMainLooper());
  Runnable tick=()->{ if(baseMillis>0){ long now=baseMillis+(SystemClock.elapsedRealtime()-baseElapsed); clock.setText(fmt(now,"HH:mm:ss","Asia/Jakarta")); detail.setText("UTC  "+fmt(now,"HH:mm:ss","UTC")+"\nWIB  "+fmt(now,"HH:mm:ss","Asia/Jakarta")); } h.postDelayed(tick,1000); };
  public void onCreate(Bundle b){super.onCreate(b); LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(40,70,40,40);l.setBackgroundColor(Color.rgb(30,30,30));
    clock=t("--:--:--",64,Color.WHITE); status=t("Menunggu waktu GitHub...",18,Color.LTGRAY); detail=t("",22,Color.WHITE);
    Button sync=new Button(this);sync.setText("SYNC GITHUB SERVER TIME");sync.setOnClickListener(v->sync()); l.addView(clock);l.addView(detail);l.addView(status);l.addView(sync);setContentView(l);h.post(tick);sync(); }
  TextView t(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(0,10,0,10);return v;}
  String fmt(long ms,String p,String tz){SimpleDateFormat f=new SimpleDateFormat(p,Locale.US);f.setTimeZone(TimeZone.getTimeZone(tz));return f.format(new Date(ms));}
  void sync(){status.setText("Mengambil Date header dari github.com..."); new Thread(()->{try{long t0=System.currentTimeMillis(); HttpURLConnection c=(HttpURLConnection)new URL("https://github.com/").openConnection();c.setRequestMethod("HEAD");c.setConnectTimeout(8000);c.setReadTimeout(8000);c.setRequestProperty("User-Agent","GitHubClockTime/1.0");c.connect(); long server=c.getHeaderFieldDate("Date",-1); long latency=System.currentTimeMillis()-t0;c.disconnect(); if(server<0)throw new IOException("Header Date tidak tersedia"); baseMillis=server;baseElapsed=SystemClock.elapsedRealtime(); runOnUiThread(()->status.setText("GitHub server: SYNC OK • latency "+latency+" ms • "+fmt(server,"dd MMM yyyy HH:mm:ss","UTC")+" UTC"));}catch(Exception e){runOnUiThread(()->status.setText("SYNC gagal: "+e.getClass().getSimpleName()+" — coba lagi"));}}).start();}
  public void onDestroy(){h.removeCallbacks(tick);super.onDestroy();}
    }
