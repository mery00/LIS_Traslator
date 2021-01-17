package com.github.florent37.materialviewpager.sample;



public class UserMessage {
    private String name;
    private String profileUrl;
    public UserMessage(String name, String profileUrl)
    {
        this.name = name;
        this.profileUrl = profileUrl;
    }
    public UserMessage(String name)
    {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
