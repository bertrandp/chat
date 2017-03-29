package io.bertrand.chat.model;

import java.util.UUID;

/**
 * Created by Bertrand on 28/03/2017.
 */
public class Message {

    UUID uuid;
    String login;
    String message;

    public Message() {
    }

    public Message(String login, String message) {
        this.uuid = UUID.randomUUID();
        this.login = login;
        this.message = message;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
