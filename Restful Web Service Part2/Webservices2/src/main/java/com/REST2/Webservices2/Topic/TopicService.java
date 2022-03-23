package com.REST2.Webservices2.Topic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopicService {

    private static List<Topic> topics = new ArrayList<>();

    static {
        topics.add(new Topic(1, "Maths"));
        topics.add(new Topic(2, "Physics"));
        topics.add(new Topic(3, "Chemistry"));
        topics.add(new Topic(4, "Computer"));


    }

    public List<Topic> getAllTopics() {
        return topics;
    }
}
