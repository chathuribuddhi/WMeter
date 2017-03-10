package com.chathuribuddhi.dto;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public class JmeterTestPlanDTO {
    private String id;
    private String url;
    private int userCount;

    public JmeterTestPlanDTO() {
    }

    public JmeterTestPlanDTO(String id, String url, int usercount) {
        this.id = id;
        this.url = url;
        this.userCount = usercount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
