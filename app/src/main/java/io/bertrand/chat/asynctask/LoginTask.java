package io.bertrand.chat.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bertrand on 28/03/2017.
 */

public class LoginTask extends AsyncTask<String, Void, Void> {

    private static final String TAG = LoginTask.class.getSimpleName();
    private ProgressDialog progressDialog;
    private Context context;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connection is coming");
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(String... params) {

        int response = 0;

        try {
            URL url = new URL("http://training.loicortola.com/chat-rest/1.0/connect/nico/nico");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            response = urlConnection.getResponseCode();
            Log.i(TAG, "" + response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
