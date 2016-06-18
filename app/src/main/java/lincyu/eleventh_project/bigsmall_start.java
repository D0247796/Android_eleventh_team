package lincyu.eleventh_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bigsmall_start extends AppCompatActivity {
    Button btn_single,btn_connect,btn_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigsmall_start);
        btn_connect=(Button)findViewById(R.id.btn_connect);
        btn_group=(Button)findViewById(R.id.btn_group);
        btn_single=(Button)findViewById(R.id.btn_single);

        btn_connect.setOnClickListener(btn_connect_CL);
        btn_single.setOnClickListener(btn_single_CL);
        btn_group.setOnClickListener(btn_group_CL);
    }
    private View.OnClickListener btn_connect_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(bigsmall_start.this, compare_connect.class);
            startActivity(intent);


        }
    };
    private View.OnClickListener btn_group_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(bigsmall_start.this, Main3Activity.class);
            startActivity(intent);


        }
    };
    private View.OnClickListener btn_single_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            Intent intent = new Intent();
            intent.setClass(bigsmall_start.this, compare_single.class);
            startActivity(intent);


        }
    };
}
