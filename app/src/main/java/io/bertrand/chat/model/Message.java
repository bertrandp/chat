package io.bertrand.chat.model;

/**
 * Created by Bertrand on 28/03/2017.
 */
public class Message {

    String uuid;
    String login;
    String message;

    public Message() {
    }

    public Message(String login, String message) {
        this.login = login;
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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
