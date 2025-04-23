package com.example.ulmanaala.request;

import java.util.List;

public class ChatRequest {
    private String model = "gpt-3.5-turbo";
    private List<Message> messages;

    public ChatRequest(List<Message> messages) {
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}
