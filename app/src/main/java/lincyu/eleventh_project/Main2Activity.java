package lincyu.eleventh_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    Drawable run123;
    boolean standkey=false;
    boolean runkey=true;
    ImageView iv_dice;
    long firsttime,lasttime;
    private SensorManager mSensorManager;   //體感(Sensor)使用管理
    private Sensor mSensor;                 //體感(Sensor)類別
    private float mLastX;                    //x軸體感(Sensor)偏移
    private float mLastY;                    //y軸體感(Sensor)偏移
    private float mLastZ;                    //z軸體感(Sensor)偏移
    private double mSpeed;                 //甩動力道數度
    private long mLastUpdateTime;           //觸發時間
    int a=0;
    TextView tv_dice;
    EditText et_maxnumber,et_minnumber;
    Button bt_start;

    //甩動力道數度設定值 (數值越大需甩動越大力，數值越小輕輕甩動即會觸發)
    private static final int SPEED_SHRESHOLD = 3000;

    //觸發間隔時間
    private static final int UPTATE_INTERVAL_TIME =70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        et_maxnumber=(EditText)findViewById(R.id.et_maxnumber);
        et_minnumber=(EditText)findViewById(R.id.et_minnumber);
        tv_dice=(TextView)findViewById(R.id.tv_dice);
        bt_start=(Button)findViewById(R.id.bt_start);
        bt_start.setOnClickListener(bt_start_CL);

        //取得體感(Sensor)服務使用權限
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        //取得手機Sensor狀態設定
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //註冊體感(Sensor)甩動觸發Listener
        mSensorManager.registerListener(SensorListener, mSensor, SensorManager.SENSOR_DELAY_GAME);

    }
    private View.OnClickListener bt_start_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            try {
                int minnumber = Integer.parseInt(et_minnumber.getText().toString());
                int maxnumber = Integer.parseInt(et_maxnumber.getText().toString());
                if(minnumber<maxnumber){

                    int x= maxnumber-minnumber+1;
                    int number = (int) (Math.random() * x + minnumber);
                    String numb = String.valueOf(number);
                    tv_dice.setText(numb);
                }
                else {
                    tv_dice.setText("最大值要大於最小值哦");
                }
//

            }catch (Exception e){
                tv_dice.setText("請輸入最大最小值!!!!");
            }


        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.rule:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("用法");
                ad.setMessage("搖動或是按按鈕隨機選字");

                DialogInterface.OnClickListener listener =
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface di, int i) {

                            }
                        };
                ad.setPositiveButton("確定", listener);
                ad.show();
                break;
            case R.id.action_quiet:
                finish();
                break;

        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private SensorEventListener SensorListener = new SensorEventListener()
    {
        public void onSensorChanged(SensorEvent mSensorEvent)
        {
            //當前觸發時間
            long mCurrentUpdateTime = System.currentTimeMillis();

            //觸發間隔時間 = 當前觸發時間 - 上次觸發時間
            long mTimeInterval = mCurrentUpdateTime - mLastUpdateTime;

            //若觸發間隔時間< 70 則return;
            if (mTimeInterval < UPTATE_INTERVAL_TIME) return;

            mLastUpdateTime = mCurrentUpdateTime;

            //取得xyz體感(Sensor)偏移
            float x = mSensorEvent.values[0];
            float y = mSensorEvent.values[1];
            float z = mSensorEvent.values[2];

            //甩動偏移速度 = xyz體感(Sensor)偏移 - 上次xyz體感(Sensor)偏移
            float mDeltaX = x - mLastX;
            float mDeltaY = y - mLastY;
            float mDeltaZ = z - mLastZ;

            mLastX = x;
            mLastY = y;
            mLastZ = z;

            //體感(Sensor)甩動力道速度公式
            mSpeed = Math.sqrt(mDeltaX * mDeltaX + mDeltaY * mDeltaY + mDeltaZ * mDeltaZ)/ mTimeInterval * 10000;

            //若體感(Sensor)甩動速度大於等於甩動設定值則進入 (達到甩動力道及速度)
            if (mSpeed >= SPEED_SHRESHOLD)
            {
                //達到搖一搖甩動後要做的事情

                //UI更新
                Main2Activity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if(runkey) {
                            runkey=false;
                            try {
                                int minnumber = Integer.parseInt(et_minnumber.getText().toString());
                                int maxnumber = Integer.parseInt(et_maxnumber.getText().toString());
                                if(minnumber<maxnumber){

                                    int x= maxnumber-minnumber;
                                    int number = (int) (Math.random() * x + minnumber);
                                    String numb = String.valueOf(number);
                                    tv_dice.setText(numb);
                                }
                                else {
                                    tv_dice.setText("最大值要大於最小值哦");
                                }
//

                            }catch (Exception e){
                                tv_dice.setText("請輸入最大最小值!!!!");
                            }
                            runkey = true;

                        }


                    }


                });



            }
        }

        public void onAccuracyChanged(Sensor sensor , int accuracy)
        {
        }
    };
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //在程式關閉時移除體感(Sensor)觸發
        mSensorManager.unregisterListener(SensorListener);
    }

//    private class InputNumberThread extends Thread {
//
//        @Override
//        public void run(){
//            while(true){
//                String x=et_number.toString();
//            }
//        }
//    }

}
