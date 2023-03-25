package com.tiago.trackerappv3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name, password;
    BroadcastReceiverRegisterService broadcastReceiver = new BroadcastReceiverRegisterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        onRegister();

        name = (EditText)findViewById(R.id.etRegisterName);
        password = (EditText)findViewById(R.id.etRegisterPassword);
    }

    public void OnReg(View view){
        String str_name = name.getText().toString();
        String str_password = password.getText().toString();
        String type = "register";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_name, str_password);
    }

    private class BroadcastReceiverRegisterService extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action != null){
                if(action.equals("com.tiago.broadcast.REGISTER")){
                    String result = intent.getStringExtra("result");

                    if(result.contains("Registration Successful")){
                        Intent i = new Intent();
                        i.putExtra("result",result);
                        Register.this.setResult(RESULT_OK,i);

                        finish();
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Register Error");
                        alertDialog.setMessage(result);
                        alertDialog.show();
                    }
                }
            }
        }
    }

    private void onRegister() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tiago.broadcast.REGISTER");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}