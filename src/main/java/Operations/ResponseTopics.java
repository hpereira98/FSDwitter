package Operations;

import java.util.List;
import util.Topic;


public class ResponseTopics{
    private List<Topic> topics;

    public ResponseTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Topic> getTopics() {
        return topics;
    }    
}