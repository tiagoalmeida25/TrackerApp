package com.tiago.trackerappv3;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String username = "";

    BackgroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "https://tiagoalmeida5.com/tracker_app/login.php";
        String register_url = "https://tiagoalmeida5.com/tracker_app/register.php";
        String save_url = "https://tiagoalmeida5.com/tracker_app/save.php";
        String get_categories_url = "https://tiagoalmeida5.com/tracker_app/get_categories.php";
        String get_types_url = "https://tiagoalmeida5.com/tracker_app/get_types.php";
        String save_with_time_url = "https://tiagoalmeida5.com/tracker_app/save_with_time.php";

        if(type.equals("login")){
            try {
                username = params[1];
                String password = params[2];
                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){
            try {
                username = params[1];
                String password = params[2];
                URL url = new URL(register_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        + URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("save")){
            try {
                String category = params[1];
                String stype = params[2];
                String value = params[3];
                username = params[4];

                URL url = new URL(save_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                        "&"+ URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(category,"UTF-8")+
                        "&"+ URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(stype,"UTF-8")+
                        "&"+ URLEncoder.encode("value","UTF-8")+"="+URLEncoder.encode(value,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                Log.d("Result", result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("save with time")){
            try {
                String category = params[1];
                String stype = params[2];
                String value = params[3];
                String date = params[4];
                username = params[5];

                Log.d("Save with time", date);

                URL url = new URL(save_with_time_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+
                        "&"+ URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(category,"UTF-8")+
                        "&"+ URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(stype,"UTF-8")+
                        "&"+ URLEncoder.encode("value","UTF-8")+"="+URLEncoder.encode(value,"UTF-8")+
                        "&"+ URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                Log.d("Save with time result", result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("get categories")){
            try {
                username = params[1];

                URL url = new URL(get_categories_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("get types")){
            try {
                String category = params[1];

                URL url = new URL(get_types_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(category,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String result = "";
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        if (result == null){
            Log.d("Result Null","");
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Error occurred");
            alertDialog.show();
        }
        else if(result.equals("Registration Successful"))
        {
            Intent i = new Intent("com.tiago.broadcast.REGISTER");
            i.putExtra("result",result);
            Log.d("Result Register",result);
            context.sendBroadcast(i);
        }
        else if(result.contains("Login"))
        {
            Intent i = new Intent("com.tiago.broadcast.LOGIN");
            i.putExtra("result",result);
            Log.d("Result Login",result);
            context.sendBroadcast(i);
        }
        else if(result.equals("Saved!"))
        {
            alertDialog.setTitle("Save Status");
            Log.d("Result Saved",result);
            alertDialog.setMessage(result);
            alertDialog.show();
//            send flag
        }
        else if(result.contains("»»")){
            // Get categories display
//            result = result.replace("»»","\n");
            Intent intent = new Intent("com.tiago.broadcast.DISPLAY_CATEGORIES");
            intent.putExtra("categories", result);
            Log.d("Display categories",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("~~")){
            // Get types display
//            result = result.replace("~~","\n");
            Intent intent = new Intent("com.tiago.broadcast.DISPLAY_TYPES");
            intent.putExtra("types", result);
            Log.d("Display types",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("##")){
            // Get categories
            Intent intent = new Intent("com.tiago.broadcast.GET_CATEGORIES");
            intent.putExtra("categories", result);
            Log.d("Result Categories",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("No categories")){
            Intent intent = new Intent("com.tiago.broadcast.NO_CATEGORIES");
            intent.putExtra("categories", result);
            Log.d("Result No Categories",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("No Types")){
            Intent intent = new Intent("com.tiago.broadcast.NO_TYPES");
            intent.putExtra("types", result);
            Log.d("Result No Types",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("--")){
            // Get types
            Intent intent = new Intent("com.tiago.broadcast.GET_TYPES");
            intent.putExtra("types", result);
            Log.d("Result types",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("ºº")){
            // Get values
//            result = result.replace("..","\n");
            Intent intent = new Intent("com.tiago.broadcast.DISPLAY_VALUES");
            intent.putExtra("values", result);
            Log.d("Display Values",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("Deleted")){
            Intent intent = new Intent("com.tiago.broadcast.DELETE_VALUES");
            Log.d("Deleted value",result);
            context.sendBroadcast(intent);
        }
        else{
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Result: " + result);
            Log.d("Result",result);
            alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

