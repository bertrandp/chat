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
import io.bertrand.chat.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.bertrand.chat.MainActivity.LOGIN;
import static io.bertrand.chat.MainActivity.PASSWORD;
import static io.bertrand.chat.MainActivity.PREFS_NAME;
import static io.bertrand.chat.MainActivity.TOKEN;

public class ListMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ListMessageActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;
    private ImageButton btnSend;
    private SwipeRefreshLayout swipeRefresh;

    private SharedPreferences settings;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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

        settings = getSharedPreferences(PREFS_NAME, 0);
        chatService = ServiceGenerator.createService(ChatService.class, settings.getString(TOKEN,""));

        refreshMessage();
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

        Map<String, String> data = new HashMap<>();
        data.put("limit", String.valueOf(30));
        //data.put("page", String.valueOf(2));

        Call<List<Message>> call = chatService.messageList(data);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "success, nb msg : " + response.body().size());

                    MessageAdapter messageAdapter = new MessageAdapter(response.body(), getApplicationContext());
                    recyclerView.setAdapter(messageAdapter);

                    swipeRefresh.setRefreshing(false);
                } else {
                    Log.i(TAG, "error, response : " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "failure");
            }
        });
    }

    private void sendMessage() {

        Call<Void> call = chatService.sendMessage(new Message(settings.getString(LOGIN, "bertrand"), editText.getText().toString()));
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
