package io.bertrand.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.bertrand.chat.model.Message;
import io.bertrand.chat.service.ChatService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMessageActivity extends AppCompatActivity {

    private static final String TAG = ListMessageActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        ChatService chatService = ChatService.retrofit.create(ChatService.class);

        Call<List<Message>> call = chatService.messageList("bertrand", "bertrand");
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "sucess");

                    List<Message> dataset = new ArrayList<>();
                    dataset.add(new Message("jean", "yooooo"));
                    dataset.add(new Message("claude", "salut"));
                    dataset.add(new Message("dusssssss", "yes"));

                    // specify an adapter
                    MessageAdapter messageAdapter = new MessageAdapter(dataset);
                    recyclerView.setAdapter(messageAdapter);
                } else {
                    Log.i(TAG, "error");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.i(TAG, "failure");
            }
        });

    }
}
