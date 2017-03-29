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
import io.bertrand.chat.service.ChatService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "pref";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText login;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.loginInput);
        pwd = (EditText) findViewById(R.id.pwdInput);

        Button button = (Button) findViewById(R.id.btnLogin);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        ChatService chatService = ChatService.retrofit.create(ChatService.class);
        Call<Void> call = chatService.connect(login.getText().toString(), pwd.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, "code : " + response.code() + ", message : " + response.message());
                if (response.isSuccessful()) {
                    Log.i(TAG, "success, response : " + response.toString());
                    Intent intent = new Intent(getApplicationContext(), ListMessageActivity.class);
                    Toast.makeText(getApplicationContext(), "Hi " + login.getText(), Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor settings = getSharedPreferences(PREFS_NAME, 0).edit();
                    settings.putString(LOGIN, login.getText().toString());
                    settings.putString(PASSWORD, pwd.getText().toString());
                    settings.apply();

                    startActivity(intent);
                } else {
                    Log.i(TAG, "error, response : " + response.toString());
                    Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
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
