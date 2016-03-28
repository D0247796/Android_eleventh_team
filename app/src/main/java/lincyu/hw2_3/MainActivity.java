package lincyu.hw2_3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    TextView textViewRes;
    Button submit;
    EditText editTextNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNum =(EditText)findViewById(R.id.textNumber);
        submit=(Button)findViewById(R.id.button);
        textViewRes=(TextView)findViewById(R.id.textResult);
        submit.setOnClickListener(submitOnClickListener);





    }
    private View.OnClickListener submitOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Toast t = Toast.makeText(MainActivity.this,"哈囉,"+editTextNum.getText(), Toast.LENGTH_SHORT);

            t.show();

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
                ad.setTitle("哈囉 這是關於本程式");
                ad.setMessage("作者:林佳慶_D0247796");

                DialogInterface.OnClickListener listener =
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface di, int i) {

                            }
                        };
                ad.setPositiveButton("確定", listener);
                ad.show();
                break;
            case R.id.action_reset:
                editTextNum.setText("");
                break;

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
