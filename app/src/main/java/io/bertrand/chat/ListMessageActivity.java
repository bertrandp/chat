package io.bertrand.chat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import io.bertrand.chat.model.Message;
import io.bertrand.chat.service.ChatService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static io.bertrand.chat.MainActivity.LOGIN;
import static io.bertrand.chat.MainActivity.PASSWORD;
import static io.bertrand.chat.MainActivity.PREFS_NAME;

public class ListMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ListMessageActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ImageButton btnSend;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        refreshMessage();

        editText = (EditText) findViewById(R.id.editmsg);
        btnSend = (ImageButton) findViewById(R.id.send);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        btnSend.setOnClickListener(this);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMessage();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                sendMessage();
                break;
        }
    }

    private void refreshMessage() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        ChatService chatService = ChatService.retrofit.create(ChatService.class);
        Call<List<Message>> call = chatService.messageList(settings.getString(LOGIN, "bertrand"), settings.getString(PASSWORD, "bertrand"));
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "success, response : " + response.toString());

                    MessageAdapter messageAdapter = new MessageAdapter(response.body(), getApplicationContext());
                    recyclerView.setAdapter(messageAdapter);

                    swipeRefresh.setRefreshing(false);
                } else {
                    Log.i(TAG, "error, response : " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.i(TAG, "failure");
            }
        });
    }

    private void sendMessage() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        ChatService chatService = ChatService.retrofit.create(ChatService.class);
        Call<Void> call = chatService.sendMessage(settings.getString(LOGIN, "bertrand"), settings.getString(PASSWORD, "bertrand"), new Message(settings.getString(LOGIN, "bertrand"), editText.getText().toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ListMessageActivity.this, "Message send", Toast.LENGTH_SHORT).show();
                editText.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ListMessageActivity.this, "Failed to send the message", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
