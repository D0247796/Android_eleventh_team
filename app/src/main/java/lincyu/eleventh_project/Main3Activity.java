package lincyu.eleventh_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    Button btn_big,btn_small,btn_shake,btn_restart;
    EditText et_people;
    TextView tv_state;
    int state=0,people=0,bigsmall=0,number=1,grade=0,grade_number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn_big = (Button)findViewById(R.id.btn_big);
        btn_small=(Button)findViewById(R.id.btn_small);
        btn_shake=(Button)findViewById(R.id.btn_shake);
        btn_restart=(Button)findViewById(R.id.btn_restart);
        et_people=(EditText)findViewById(R.id.et_people);
        tv_state=(TextView)findViewById(R.id.tv_state);

        btn_big.setOnClickListener(btn_big_CL);
        btn_small.setOnClickListener(btn_small_CL);
        btn_shake.setOnClickListener(btn_shake_CL);
        btn_restart.setOnClickListener(btn_restart_CL);
    }
    private View.OnClickListener btn_big_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            if(state==0) {
                tv_state.setText("比大，請輸入人數並按搖動");
                state = 1;
                bigsmall = 1;

            }
        }
    };
    private View.OnClickListener btn_small_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            if (state == 0) {
                tv_state.setText("比小，請輸入人數並按搖動");
                state = 1;
                bigsmall = 2;
                grade=1000;
            }
        }
    };
    private View.OnClickListener btn_shake_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            if(state==1) {
                try {

                    people = Integer.parseInt(et_people.getText().toString());
                    if (people>1) {

                        tv_state.setText("第" + number + "位，開始吧 ");
                        state = 2;
                    }else {
                        tv_state.setText("請大於1");
                    }



                } catch (Exception e) {
                    tv_state.setText(tv_state.getText() + "!!");
                }
            }else if (state==2 && number<people){
                int x = (int) (Math.random() * 101 );
                if(x>grade&&bigsmall==1){
                    grade=x;
                    grade_number=number;
                }
                if(x<grade&&bigsmall==2){
                    grade=x;
                    grade_number=number;
                }
                tv_state.setText("第" + number + "位，"+x+"分，下一位開始吧");
                number++;

            }
            else if(state==2 && number==people){
                int x = (int) (Math.random() * 100 );
                if(x>grade&&bigsmall==1){
                    grade=x;
                    grade_number=number;
                }
                if(x<grade&&bigsmall==2){
                    grade=x;
                    grade_number=number;
                }
                tv_state.setText("第" + number + "位，"+x+"分，最後一位完了，再按一次搖動看成果");
                number++;
            }
           else if(state==2 && number>people){
                if(bigsmall==1) {
                    tv_state.setText("第" + grade_number + "位:" + grade + "分最大");
                }
                if(bigsmall==2) {
                    tv_state.setText("第" + grade_number + "位:" + grade + "分最小");
                }
            }
        }
    };
    private View.OnClickListener btn_restart_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            state=0;
            people=0;
            bigsmall=0;
            number=1;
            grade=0;
            grade_number=0;
            tv_state.setText("比大，請輸入人數並按搖動");

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
                ad.setMessage("先輸入人數便開始按按鈕，按一次換一個人");

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
}
