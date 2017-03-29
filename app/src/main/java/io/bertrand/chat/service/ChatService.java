package io.bertrand.chat.service;


import io.bertrand.chat.model.Message;
import io.bertrand.chat.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Bertrand on 28/03/2017.
 */

public interface ChatService {

    /* API 2.0 */

    @GET("connect")
    Call<Void> connect();

    @GET("messages")
    Call<List<Message>> messageList(@QueryMap Map<String, String> options);

    @POST("messages")
    Call<Void> sendMessage(@Body Message message);

    @GET("profile/{login}")
    Call<Void> getProfile(@Path("login") String login);

    @POST("profile")
    Call<Void> setProfile(@Body User message);


    /* API 1.0 */

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://training.loicortola.com/chat-rest/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("register/{login}/{password}")
    Call<Void> register(@Path("login") String login, @Path("password") String password);

    @GET("connect/{login}/{password}")
    Call<Void> connect(@Path("login") String login, @Path("password") String password);

    @GET("messages/{login}/{password}")
    Call<List<Message>> messageList(@Path("login") String login, @Path("password") String password);

    @POST("messages/{login}/{password}")
    Call<Void> sendMessage(@Path("login") String login, @Path("password") String password, @Body Message message);

}
