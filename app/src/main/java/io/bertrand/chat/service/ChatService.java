package io.bertrand.chat.service;


import java.util.List;

import io.bertrand.chat.model.Message;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Bertrand on 28/03/2017.
 */

public interface ChatService {

    @GET("/connect/{login}/{password}")
    Call<String> connect(@Path("login") String login, @Path("password") String password );

    @GET("/messages/{login}/{password}")
    Call<List<Message>> messageList(@Path("login") String login, @Path("password") String password );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://training.loicortola.com/chat-rest/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
