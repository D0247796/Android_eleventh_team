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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class gamble_start extends AppCompatActivity {
    Button btn_connect;
    EditText et_ip;
    TextView tv_ip,tv_state;
    int connect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamble_start);

        btn_connect=(Button)findViewById(R.id.btn_connect);
        et_ip=(EditText)findViewById(R.id.et_ip);
        tv_ip=(TextView)findViewById(R.id.tv_ip);
        tv_state=(TextView)findViewById(R.id.tv_state);

        btn_connect.setOnClickListener(btn_connect_CL);

        tv_ip.setText(getIpAddress());

        Thread TCP_SocketServerThread = new Thread(new TCP_SocketServerThread());
        TCP_SocketServerThread.start();

    }
    private View.OnClickListener btn_connect_CL = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            TCP_SocketClientThread TCP_SocketClientThread = new TCP_SocketClientThread();
            TCP_SocketClientThread.start();
            //沒加的話只能一次
            Thread changeThread =new Thread(new changeThread());
            changeThread.start();
            tv_state.setText("連結中");

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
                ad.setMessage("請輸入ＩＰ（在底下）");

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
    //拿取 IP
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
    //Server端
    private class TCP_SocketServerThread extends Thread {

        String cleint_msg;
        @Override
        public void run() {
            ServerSocket TCP_server_serversocket =null;
            Socket TCP_server_socket =null;
            DataInputStream TCP_server_din = null;
            DataOutputStream TCP_server_dout =null;

            try{
                TCP_server_serversocket= new ServerSocket(7777);
            }
            catch(Exception e){
                e.printStackTrace();;
            }
            while(true){
                try{
                    TCP_server_socket=TCP_server_serversocket.accept();
//                    client_ip=s.getInetAddress().toString();
                    TCP_server_din = new DataInputStream(TCP_server_socket.getInputStream());
                    TCP_server_dout = new DataOutputStream(TCP_server_socket.getOutputStream());

                    cleint_msg = TCP_server_din.readUTF(); //這是裡傳來的訊息
                    if(cleint_msg.equals("User_Client")){
                        InetAddress client_ip =null;
                        client_ip = TCP_server_socket.getInetAddress();
                        String ip=client_ip.toString();

                        Intent intent = new Intent();
                        intent.setClass(gamble_start.this, User2Activity.class);
                        intent.putExtra("IP", ip);
                        startActivity(intent);

                    }

                    //UI更新
                    gamble_start.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });

                    TCP_server_dout.writeUTF("OK");
                }
                catch(Exception e){
                    e.getStackTrace();
                }
                finally{
                    try{
                        if(TCP_server_dout !=null){
                            TCP_server_dout.close();
                        }
                        if(TCP_server_din !=null){
                            TCP_server_din.close();
                        }
                        if(TCP_server_socket != null){
                            TCP_server_socket.close();
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }


    }
    //Client端
    private class TCP_SocketClientThread extends Thread {
        String server_msg,server_msg2=null;
        @Override
        public void run() {
            Socket TCP_client_socket = null;
            String ip="";
            ip=et_ip.getText().toString();
            DataOutputStream TCP_client_dout = null;
            DataInputStream TCP_client_din =null;

            try{
                TCP_client_socket = new Socket(ip,7777);
                //UI更新
                TCP_client_dout = new DataOutputStream(TCP_client_socket.getOutputStream());
                TCP_client_din = new DataInputStream(TCP_client_socket.getInputStream());
                TCP_client_dout.writeUTF("User_Client");
                server_msg = TCP_client_din.readUTF(); //這是裡傳來的訊息
                server_msg2=server_msg;
                if(server_msg.equals("OK")){
                    connect=1;
                    server_msg2=null;
                }
                //UI更新
                gamble_start.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        tv_state.setText(server_msg);

                    }
                });
                TCP_client_socket.close();



            }
            catch (Exception e){
                e.getStackTrace();
            }
            finally {
                try{
                    if(TCP_client_dout !=null){
                        TCP_client_dout.close();
                    }
                    if(TCP_client_din !=null){
                        TCP_client_din.close();
                    }
                    if(TCP_client_socket != null){
                        TCP_client_socket.close();
                    }
                }catch (Exception e){
                    e.getStackTrace();
                }
            }

        }

    }
    //確認接收 轉換介面 傳遞IP
    private class changeThread extends Thread {

        @Override
        public void run() {
            while(true) {
                if (connect == 1) {
                    String ip = et_ip.getText().toString();
                    Intent intent = new Intent();
                    intent.setClass(gamble_start.this, Main4Activity.class);
                    intent.putExtra("IP", ip);
                    startActivity(intent);
                    connect = 0;
                    break;



                }
            }
        }
    }

}
