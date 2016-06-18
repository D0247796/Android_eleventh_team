package lincyu.eleventh_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button bt_shake,bt_guess,bt_compare,bt_gamble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_shake = (Button)findViewById(R.id.bt_shake);
        bt_compare =(Button)findViewById(R.id.bt_compare);
        bt_gamble = (Button)findViewById(R.id.bt_gamble);
        bt_guess = (Button)findViewById(R.id.bt_guess);

        bt_shake.setOnClickListener(bt_shake_CL);
        bt_compare.setOnClickListener(bt_compare_CL);
        bt_gamble.setOnClickListener(bt_gamble_CL);
        bt_guess.setOnClickListener(bt_guess_CL);

    }
    private View.OnClickListener bt_shake_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main2Activity.class);
            startActivity(intent);

        }
    };
    private View.OnClickListener bt_compare_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main3Activity.class);
            startActivity(intent);

        }
    };
    private View.OnClickListener bt_gamble_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, gamble_start.class);
            startActivity(intent);


        }
    };
    private View.OnClickListener bt_guess_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Main5Activity.class);
            startActivity(intent);


        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_about:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("關於作者");
                ad.setMessage("組別:11\n作者:\n林佳慶_D0247796");

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
