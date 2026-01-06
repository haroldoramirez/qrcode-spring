package br.com.haroldo.qrcode.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstagramFollowersResponseDTO {

    @JsonProperty("followers_count")
    private int followersCount;

    private String id;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}