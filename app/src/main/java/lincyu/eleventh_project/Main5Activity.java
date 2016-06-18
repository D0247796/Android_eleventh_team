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

public class Main5Activity extends AppCompatActivity {
    EditText et_number;
    TextView tv_state;
    Button btn_restart,btn_guess;
    int number=0,state=0,top=100,down=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        et_number = (EditText)findViewById(R.id.et_number);
        tv_state=(TextView)findViewById(R.id.tv_state);
        btn_guess=(Button)findViewById(R.id.btn_guess);
        btn_restart=(Button)findViewById(R.id.btn_restart);

        btn_guess.setOnClickListener(btn_guess_CL);
        btn_restart.setOnClickListener(btn_restart_CL);
    }
    private View.OnClickListener btn_guess_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            guess();
        }
    };
    private View.OnClickListener btn_restart_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            restart();
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
                ad.setMessage("先輸入指定數字,就能開始猜了");

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

    public void guess(){
        if(state==0){
            try {

                number = Integer.parseInt(et_number.getText().toString());

                if (number < down || number > top) {
                    tv_state.setText("請輸入1~100");
                } else {
                    tv_state.setText("來吧，1~100開始猜囉");
                    et_number.setText("");
                    state++;
                }
            }catch (Exception e){
                tv_state.setText("請輸入1~100!!");
            }
        }
        else if(state==1){
            try {

                int guess_number = Integer.parseInt(et_number.getText().toString());
                if (guess_number >= top || guess_number <= down) {
                    tv_state.setText("請猜範圍內的數字");
                } else if (guess_number == number) {
                    tv_state.setText("猜中了!!!請按重設重新開始");
                } else if (guess_number != number) {
                    if (guess_number > number) {
                        tv_state.setText(down + "到" + guess_number);
                        top = guess_number;
                        et_number.setText("");
                    }
                    if (guess_number < number) {
                        tv_state.setText(guess_number + "到" + top);
                        down = guess_number;
                        et_number.setText("");
                    }

                }
            }catch (Exception e){
                tv_state.setText(tv_state.getText()+"\n請填入數字");
            }


        }


    }
    private void restart(){
        et_number.setText("");
        tv_state.setText("請填入給人猜的數字(1~100)");
       number=0;
        state=0;
        top=100;
        down=1;
    }
}
