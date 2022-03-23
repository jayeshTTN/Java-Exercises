package com.REST2.Webservices2.Topic;

public class Topic {
    private Integer topicId;
    private String topicName;

    public Topic(Integer topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
