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
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main4Activity extends AppCompatActivity {
    Button btn_msg, btn_number, btn_big, btn_small, btn_one, btn_two, btn_three, btn_four, btn_same;
    TextView tv_state, tv_mymoney, tv_urmoney;
    EditText et_money, et_number, et_msg;

    String ip = "", user = "";
    int key = 1, grade = 0, price = 0, dice1 = 0, dice2 = 0, dice3 = 0, allsame = 0, kind = 0, mymoney = 1000, urmoney = 1000, win = 0, start = 0, compare = 0, over = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        btn_msg = (Button) findViewById(R.id.btn_msg);
        btn_number = (Button) findViewById(R.id.btn_number);
        btn_big = (Button) findViewById(R.id.btn_big);
        btn_small = (Button) findViewById(R.id.btn_small);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_same = (Button) findViewById(R.id.btn_same);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_mymoney = (TextView) findViewById(R.id.tv_mymoney);
        tv_urmoney = (TextView) findViewById(R.id.tv_urmoney);
        et_money = (EditText) findViewById(R.id.et_money);
        et_number = (EditText) findViewById(R.id.et_number);
        et_msg = (EditText) findViewById(R.id.et_msg);

        btn_msg.setOnClickListener(btn_msg_CL);
        btn_number.setOnClickListener(btn_number_CL);
        btn_big.setOnClickListener(btn_big_CL);
        btn_small.setOnClickListener(btn_small_CL);
        btn_same.setOnClickListener(btn_same_CL);
        btn_one.setOnClickListener(btn_one_CL);
        btn_two.setOnClickListener(btn_two_CL);
        btn_three.setOnClickListener(btn_three_CL);
        btn_four.setOnClickListener(btn_four_CL);

        Intent intent = getIntent();
        ip = intent.getStringExtra("IP");
        Thread UDP_ServerThread = new Thread(new UDP_ServerThread());
        UDP_ServerThread.start();


//            Thread user1_SocketServerThread = new Thread(new user1_SocketServerThread());
//            user1_SocketServerThread.start();
        tv_mymoney.setText("我方金額:" + mymoney);
        tv_urmoney.setText("對方金額:" + urmoney);


    }

    private View.OnClickListener btn_msg_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(start>0) {
                UDP_Client UDP_Client = new UDP_Client();
                UDP_Client.start();
            }
        }
    };
    private View.OnClickListener btn_number_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {
                    try {
                        kind = 1;
                        price = 15;
                        if (grade == Integer.parseInt(et_number.getText().toString())) {
                            mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                            tv_state.setText("已下注");
                            TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                            TCP_SocketClientThread.start();
                            compare = 1;
                            win = 1;
                            key = 1;
                        } else {
                            mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                            tv_state.setText("已下注");
                            TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                            TCP_SocketClientThread.start();
                            compare = 1;
                            key = 1;
                        }
                        // compare();
                    } catch (Exception e) {
                        tv_state.setText("請輸入要猜的點數");
                    }

                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_small_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {

                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {

                    price = 2;
                    kind = 3;
                    if (allsame != 1 && grade <= 10) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    //    compare();
                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_same_CL = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            try {
                if (start == 0) {
                    start();
                } else if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {


                    kind = 2;
                    price = 30;
                    if (allsame == 1) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    //  compare();

                } else if (over == 1) {
                    if (urmoney >= 100000 && mymoney >= 100000) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("恭喜都破十萬，平手!\n請再按一次豹子");
                    } else if (urmoney >= 100000) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("妳對手已到十萬，你輸了~\n請再按一次豹子");
                    } else if (mymoney >= 100000) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("妳已經拿到了十萬，你贏了!!\n請再按一次豹子");
                    } else if (urmoney == 0 && mymoney == 0) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("兩位都破產了~\n請再按一次豹子");

                    } else if (urmoney == 0) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("妳對手已經破產了，你贏了~\n請再按一次豹子");
                    } else if (mymoney == 0) {
                        mymoney = 1000;
                        urmoney = 1000;
                        tv_state.setText("妳已經破產了，你輸了~\n請再按一次豹子");
                    } else {
                        tv_mymoney.setText("我方金額:" + mymoney);
                        tv_urmoney.setText("對方金額:" + urmoney);
                        key = 1;
                        dice1 = 0;
                        dice2 = 0;
                        dice3 = 0;
                        allsame = 0;
                        grade = 0;
                        over = 0;
                        price = 0;
                        kind = 0;
                        compare = 0;
                        start();
                    }


                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_big_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {
                    price = 2;
                    kind = 4;
                    if (allsame != 1 && grade >= 11) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }

                    // compare();

                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    public View.OnClickListener btn_one_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {


                    price = 8;
                    kind = 5;
                    if (allsame != 1 && grade >= 4 && grade <= 7) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    // compare();

                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_two_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {


                    price = 8;
                    kind = 6;
                    if (allsame != 1 && grade >= 8 && grade <= 10) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    // compare();

                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_three_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {

                    price = 8;
                    kind = 7;
                    if (allsame != 1 && grade >= 11 && grade <= 13) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    //  compare();

                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
            }
        }
    };
    private View.OnClickListener btn_four_CL = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {

                if (key == 0 && Integer.parseInt(et_money.getText().toString()) > mymoney) {
                    tv_state.setText("請輸入範圍內金額!");
                } else if (key == 0) {


                    price = 8;
                    kind = 8;
                    if (allsame != 1 && grade >= 14 && grade <= 17) {
                        mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        win = 1;
                        key = 1;
                        compare = 1;
                    } else {
                        mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
                        tv_state.setText("已下注");
                        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
                        TCP_SocketClientThread.start();
                        key = 1;
                        compare = 1;
                    }
                    // compare();


                }
            } catch (Exception e) {
                tv_state.setText("請輸入金額");
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
                ad.setMessage("按豹子是開始以及結算\n當金額到十萬或對手歸零時獲勝\n通訊要開始比賽才能用");

                DialogInterface.OnClickListener listener =
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface di, int i) {

                            }
                        };
                ad.setPositiveButton("確定", listener);
                ad.show();
                break;
            case R.id.action_quiet:
                over = 0;
                key = 1;
                grade = 0;
                price = 0;
                dice1 = 0;
                dice2 = 0;
                dice3 = 0;
                allsame = 0;
                kind = 0;
                win = 0;
                compare = 0;
                urmoney=1000;
                mymoney=1000;
                finish();
                break;

        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    public void start() {
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);
        dice3 = (int) (Math.random() * 6 + 1);
        int x = (dice1 * 100) + (dice2 * 10) + (dice3);
        if (dice1 == dice2 && dice1 == dice3) {
            allsame = 1;
        }
        grade = dice1 + dice2 + dice3;

        TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(x);
        TCP_SocketClientThread.start();
        tv_state.setText("等待對方準備");
        start++;
    }
//    public void compare(){
//        try {
//            if(Integer.parseInt(et_money.getText().toString())>mymoney){
//                tv_state.setText("請輸入妳擁有的金額數");
//            }
//           else if (allsame == 1) {
//                if (kind == 2) {
//                    mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                    win = 1;
//                    tv_state.setText("已下注");
//                    TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                    TCP_SocketClientThread.start();
//                    compare = 1;
//                    key = 1;
//                } else {
//                    mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
//                    tv_state.setText("已下注");
//                    TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                    TCP_SocketClientThread.start();
//                    compare = 1;
//                    key = 1;
//                }
//            } else if (kind == 1) {
//                if (grade == Integer.parseInt(et_number.getText().toString())) {
//                    mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                    tv_state.setText("已下注");
//                    TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                    TCP_SocketClientThread.start();
//                    compare = 1;
//                    win = 1;
//                    key = 1;
//                } else {
//                    mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
//                    tv_state.setText("已下注");
//                    TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                    TCP_SocketClientThread.start();
//                    compare = 1;
//                    key = 1;
//                }
//            } else if (kind == 3 && grade <= 10) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                win = 1;
//                key = 1;
//            } else if (kind == 4 && grade >= 11) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                win = 1;
//                key = 1;
//            } else if (kind == 5 && grade >= 4 && grade <= 7) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                key = 1;
//                win = 1;
//            } else if (kind == 6 && grade >= 8 && grade <= 10) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                key = 1;
//                win = 1;
//            } else if (kind == 7 && grade >= 11 && grade <= 13) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                win = 1;
//                key = 1;
//            } else if (kind == 8 && grade >= 14 && grade <= 17) {
//                mymoney = mymoney + Integer.parseInt(et_money.getText().toString()) * price;
//                win = 1;
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                win = 1;
//                key = 1;
//            } else {
//                mymoney = mymoney - Integer.parseInt(et_money.getText().toString());
//                tv_state.setText("已下注");
//                TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread(mymoney);
//                TCP_SocketClientThread.start();
//                compare = 1;
//                key = 1;
//                win = 1;
//
//            }
//        }catch (Exception e){
//            tv_state.setText("請輸入數字");
//        }
//
//    }


//    //user1Server端
//    public class user1_SocketServerThread extends Thread {
//
//        int cleint_msg;
//        @Override
//        public void run() {
//            ServerSocket TCP_server_serversocket =null;
//            Socket TCP_server_socket =null;
//            DataInputStream TCP_server_din = null;
//            DataOutputStream TCP_server_dout =null;
//
//            try{
//                TCP_server_serversocket= new ServerSocket(7700);
//            }
//            catch(Exception e){
//                e.printStackTrace();;
//            }
//            while(true){
//                try{
//                    TCP_server_socket=TCP_server_serversocket.accept();
////                    client_ip=s.getInetAddress().toString();
//                    TCP_server_din = new DataInputStream(TCP_server_socket.getInputStream());
//                    TCP_server_dout = new DataOutputStream(TCP_server_socket.getOutputStream());
//                    cleint_msg = TCP_server_din.readInt(); //這是裡傳來的訊息
//                    tv_state.setText(cleint_msg);
//                    urmoney=cleint_msg;
//                    step++;
//                }
//                catch(Exception e){
//                    e.getStackTrace();
//                }
//                finally{
//                    try{
//                        if(TCP_server_dout !=null){
//                            TCP_server_dout.close();
//                        }
//                        if(TCP_server_din !=null){
//                            TCP_server_din.close();
//                        }
//                        if(TCP_server_socket != null){
//                            TCP_server_socket.close();
//                        }
//                    }
//                    catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//
//    }

    //Client端
    public class TCP_SocketClientThread extends Thread {
        int server_msg = 0;
        int msg;

        public TCP_SocketClientThread(int msg) {
            this.msg = msg;

        }

        @Override
        public void run() {
            Socket TCP_client_socket = null;
            DataOutputStream TCP_client_dout = null;
            DataInputStream TCP_client_din = null;

            try {
                TCP_client_socket = new Socket(ip, 7770);

                //UI更新
                TCP_client_dout = new DataOutputStream(TCP_client_socket.getOutputStream());
                TCP_client_din = new DataInputStream(TCP_client_socket.getInputStream());
                TCP_client_dout.writeInt(msg);
                server_msg = TCP_client_din.readInt(); //這是裡傳來的訊息
                if (server_msg == 80800) {
                    //UI更新
                    Main4Activity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            tv_state.setText("下注囉~~");
                            key = 0;
                        }
                    });

                }
                if (compare == 1) {
                    urmoney = server_msg;
                    server_msg = 0;
                    if (win == 1) {
                        //UI更新
                        Main4Activity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                tv_state.setText("骰子為" + dice1 + "、" + dice2 + "、" + dice3 + "恭喜猜對了\n按豹子進行下一局");
                            }
                        });

                    } else {
                        Main4Activity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                tv_state.setText("骰子為" + dice1 + "、" + dice2 + "、" + dice3 + "在加油吧\n按豹子進行下一局");
                            }
                        });

                    }
                    Main4Activity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            tv_mymoney.setText("我方金額:" + mymoney);
                            tv_urmoney.setText("對方金額:" + urmoney);
                        }
                    });
                    over = 1;
                    win = 0;
                }


                TCP_client_socket.close();


            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                try {
                    if (TCP_client_dout != null) {
                        TCP_client_dout.close();
                    }
                    if (TCP_client_din != null) {
                        TCP_client_din.close();
                    }
                    if (TCP_client_socket != null) {
                        TCP_client_socket.close();
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

        }

    }

    //UDP
    public class UDP_ServerThread extends Thread {
        public void run() {
            int port = 7070;
            byte[] buf = new byte[1000];
            byte[] buf2 = new byte[1000];

            try {
                // Construct a datagram socket and bind it to the specified port
                DatagramSocket socket = new DatagramSocket(port);

                System.out.println("我在等待喔: " + port);

                // Construct a DatagramPacket for receiving packets
                DatagramPacket recPacket = new DatagramPacket(buf, buf.length);
                while (true) {
                    // Receive a datagram packet from this socket
                    socket.receive(recPacket);
                    // Process message
                    InetAddress senderAddr = recPacket.getAddress();
                    int senderPort = recPacket.getPort();
                    final String msg = new String("對方說: " + new String(buf, 0, buf.length) );
                    System.out.println("我在收到喔: " + port);

                    System.out.println(msg);
                    //UI更新
                    Main4Activity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(Main4Activity.this, msg, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

//                    // Prepare reply message
//                    String reply = "Server reply";
//                    // Encode this String into a sequence of bytes and store to buf.
//                    buf = reply.getBytes();
//                    // Construct a DatagramPacket for reply message
//                    DatagramPacket replyPacket = new DatagramPacket(buf2, buf2.length, senderAddr, 8080);
//                    // Send message
//                    socket.send(replyPacket);
                }

            } catch (Exception e) {

            }
        }
    }

    public class UDP_Client extends Thread {
        int udp_port = 7080;
        byte[] buf = new byte[1000];
        byte[] buf2 = new byte[1000];

        @Override
        public void run() {
            try {

                // Construct a datagram socket and bind it to any available port
                DatagramSocket socket = new DatagramSocket();

                String msg = et_msg.getText().toString();
                // Encode this String into a sequence of bytes and store to buf.
                buf = msg.getBytes();
                // Construct a datagram packet for sending packets of length length to
                // the specified port number on the specified host.

                InetAddress UDP_ip = InetAddress.getByName(ip);

                DatagramPacket packet = new DatagramPacket(buf, buf.length, UDP_ip, udp_port);
                // Send message
                socket.send(packet);

//                // Construct a DatagramPacket for receiving packet
//                DatagramPacket recPacket = new DatagramPacket(buf2, buf2.length);
//                // Receive a datagram packet from this socket
//                socket.receive(recPacket);
//                // Process message
//                InetAddress senderAddr = recPacket.getAddress();
//                int senderPort = recPacket.getPort();
//                String reply = new String("Receive message '" + new String(buf2, 0, buf2.length) +
//                        "' from address : " + senderAddr +
//                        ", port : " + senderPort);
//                System.out.println(reply);

                socket.close();
            } catch (Exception e) {

            }
        }

    }
}