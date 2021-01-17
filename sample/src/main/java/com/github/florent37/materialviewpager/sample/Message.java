package com.github.florent37.materialviewpager.sample;

import java.time.LocalTime;
import java.util.Date;

public class Message {
    private String text;
    private UserMessage userMessage;
    private String messageDate;
    private String messageTime;


    public Message(String text, UserMessage userMessage, String messageDate, String messageTime) {
        this.text = text;
        this.userMessage = userMessage;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }
}
