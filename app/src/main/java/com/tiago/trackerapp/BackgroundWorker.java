package com.tiago.trackerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
        String get_categories_for_display_url = "https://tiagoalmeida5.com/tracker_app/get_categories_for_display.php";
        String get_types_for_display_url = "https://tiagoalmeida5.com/tracker_app/get_types_for_display.php";
        String get_values_url = "https://tiagoalmeida5.com/tracker_app/get_values.php";
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
                        "&"+ URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+
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
        else if(type.equals("display categories")){
            try {
                username = params[1];

                URL url = new URL(get_categories_for_display_url);

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
        else if(type.equals("display types")){
            try {
                String category = params[1];

                URL url = new URL(get_types_for_display_url);

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
        else if(type.equals("display values")){
            try {
                String s_type = params[1];

                URL url = new URL(get_values_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(s_type,"UTF-8");

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
        if (result == null){
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Error occurred");
            alertDialog.show();
        }
        else if(result.equals("Registration Successful"))
        {
            Intent i = new Intent("com.tiago.broadcast.REGISTER");
            i.putExtra("result",result);
            context.sendBroadcast(i);
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//            Intent i = new Intent(context, Login.class);
//            context.startActivity(i);
        }
        else if(result.contains("Login"))
        {
            Intent i = new Intent("com.tiago.broadcast.LOGIN");
            i.putExtra("result",result);
            context.sendBroadcast(i);
//            Intent i = new Intent(context,DataBase.class);
//            i.putExtra("username", username);
//            context.startActivity(i);

//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage(result);
//            alertDialog.show();
        }
        else if(result.equals("Saved!"))
        {
            alertDialog.setTitle("Save Status");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else if(result.contains("»»")){
            result = result.replace("»»","\n");
            alertDialog.setTitle("Categories");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else if(result.contains("~~")){
            result = result.replace("~~","\n");
            alertDialog.setTitle("Types");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else if(result.contains("##")){
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
//            result = result.replace("--","\n");
//            alertDialog.setTitle("Types");
//            alertDialog.setMessage(result);
            Intent intent = new Intent("com.tiago.broadcast.GET_TYPES");
            intent.putExtra("types", result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("##")){
            Intent intent = new Intent("com.tiago.broadcast.NO_TYPES");
            intent.putExtra("types", result);
            Log.d("Result Categories",result);
            context.sendBroadcast(intent);
        }
        else if(result.contains("..")){
            result = result.replace("..","\n");
            alertDialog.setTitle("Values");
            alertDialog.setMessage(result);
            alertDialog.show();
//            Intent intent = new Intent();
//            intent.setAction("com.tiago.broadcast.GET_TYPES");
//            intent.putExtra("types", result);
//            context.sendBroadcast(intent);
        }
        else{
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Result: " + result);
            Log.d("Result",result);
            alertDialog.show();
        }
        Log.d("After Result",result);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


