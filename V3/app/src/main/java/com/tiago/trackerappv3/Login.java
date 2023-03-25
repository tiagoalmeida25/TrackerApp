package com.tiago.trackerappv3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
    String username = "";
    String password = "";
    BroadcastReceiverLoginService broadcastReceiver = new BroadcastReceiverLoginService();

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    String result_reg = data.getStringExtra("result");

                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("Registration Successful");
                    alertDialog.setMessage(result_reg);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsernameEt = (EditText)findViewById(R.id.etUsername);
        PasswordEt = (EditText)findViewById(R.id.etPassword);

        onRegister();
    }

    private class BroadcastReceiverLoginService extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action != null){
                if(action.equals("com.tiago.broadcast.LOGIN")){
                    String result = intent.getStringExtra("result");

                    if(result.contains("Welcome")){
                        PreferenceUtils.saveUsername(username,Login.this);
                        PreferenceUtils.savePassword(password, Login.this);

                        Log.d("User in login",username);
                        Intent i = new Intent();
                        i.putExtra("result",result);
                        i.putExtra("username",username);
                        Login.this.setResult(RESULT_OK,i);

                        finish();
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Login Error");
                        alertDialog.setMessage(result);
                        alertDialog.show();
                    }
                }
            }
        }
    }

    private void onRegister() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tiago.broadcast.LOGIN");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    public void OnLogin (View View) {
        username = rtrim(UsernameEt.getText().toString());
        password = PasswordEt.getText().toString();
        String type = "login";

        UsernameEt.setText("");
        PasswordEt.setText("");


        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

    public void OpenReg(View view){
//        startActivity(new Intent(this, Register.class));
        activityResultLauncher.launch(new Intent(this, Register.class));
    }

    private String rtrim(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ' ') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}