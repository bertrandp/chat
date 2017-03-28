package io.bertrand.chat;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.bertrand.chat.asynctask.LoginTask;
import io.bertrand.chat.service.ChatService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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


        //new LoginTask(this).execute(login.getText().toString(), pwd.getText().toString());

//        ChatService chatService = ChatService.retrofit.create(ChatService.class);
//        Call<String> call = chatService.connect(login.getText().toString(), pwd.getText().toString());
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i(TAG, "code : " + response.code() + ", message : " + response.message());
//                if(response.isSuccessful()) {
//                    Intent intent = new Intent(getApplicationContext(), ListMessageActivity.class);
//                    Toast.makeText(getApplicationContext(), "Hi " + login.getText() , Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong credentials" , Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failure" , Toast.LENGTH_SHORT).show();
//            }
//        });

        Intent intent = new Intent(getApplicationContext(), ListMessageActivity.class);
        Toast.makeText(getApplicationContext(), "Hi " + login.getText() , Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
}
