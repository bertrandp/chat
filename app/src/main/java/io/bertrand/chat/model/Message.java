package io.bertrand.chat.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by Bertrand on 28/03/2017.
 */
public class Message {

    String uuid;
    String login;
    String message;
    List<String> images;
    List<Attachments> attachments;

    public Message() {
    }

    public Message(String login, String message) {
        this.uuid = UUID.randomUUID().toString();
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}
