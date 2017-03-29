package io.bertrand.chat.service;


import io.bertrand.chat.model.Message;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Bertrand on 28/03/2017.
 */

public interface ChatService {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://training.loicortola.com/chat-rest/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("connect/{login}/{password}")
    Call<Void> connect(@Path("login") String login, @Path("password") String password);

    @GET("messages/{login}/{password}")
    Call<List<Message>> messageList(@Path("login") String login, @Path("password") String password);

    @POST("messages/{login}/{password}")
    Call<Void> sendMessage(@Path("login") String login, @Path("password") String password, @Body Message message);

}
