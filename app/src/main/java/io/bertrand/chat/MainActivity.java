package io.bertrand.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import io.bertrand.chat.service.ChatService;
import io.bertrand.chat.service.ServiceGenerator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.internal.http.BridgeInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "pref";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TOKEN = "token";
    private EditText login;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new BridgeInterceptor())
                ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                . // other setters
    . // setNetworkFetcher is already called for you
    .build();


        Fresco.initialize(this, config);

        login = (EditText) findViewById(R.id.loginInput);
        pwd = (EditText) findViewById(R.id.pwdInput);

        Button button = (Button) findViewById(R.id.btnLogin);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String lgn = "bertrand";    // login.getText().toString().trim()
        String passwd = "bertrand"; // pwd.getText().toString().trim()

        final String authToken = Credentials.basic(lgn, passwd);

        ChatService chatService = ServiceGenerator.createService(ChatService.class, authToken);

        Call<Void> call = chatService.connect();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "success, response : " + response.toString());
                    Intent intent = new Intent(getApplicationContext(), ListMessageActivity.class);
                    Toast.makeText(getApplicationContext(), "Hi " + login.getText(), Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor settings = getSharedPreferences(PREFS_NAME, 0).edit();
                    settings.putString(LOGIN, login.getText().toString().trim());
                    settings.putString(TOKEN, authToken);
                    settings.apply();

                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "failure, t : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
