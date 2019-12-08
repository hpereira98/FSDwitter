package Application;

import java.util.*;

public class Posts {
    private Map<Topic, BoundedQueue<Post>> posts;

    public Posts(){
        this.posts = new HashMap<>();
        this.posts.put(Topic.NEWS, new BoundedQueue<>(10));
        this.posts.put(Topic.SPORTS, new BoundedQueue<>(10));
        this.posts.put(Topic.CULTURE, new BoundedQueue<>(10));
        this.posts.put(Topic.PEOPLE, new BoundedQueue<>(10));
    }

    synchronized void addTopic(Topic t){
        this.posts.put(t,new BoundedQueue<>(10));
    }

    public boolean make_post(Post p) {
        for(Topic t : p.getTopics()){
            if(!this.posts.containsKey(t))
                this.addTopic(t);

            BoundedQueue<Post> tagged_posts = this.posts.get(t);
            tagged_posts.add(p);
        }
        return true;
    }

    public List<Post> get_10_recent_posts(Map<Topic,Long> topics) {
        List<Post> subscribed_posts = new ArrayList<>();
        int num_posts = 0;

        for (Topic t : topics.keySet()){
            long subscribed_date = topics.get(t);

            for (Post post : this.posts.get(t).get()){
                if (post.getDate() > subscribed_date){
                    subscribed_posts.add(post);
                    num_posts++;
                }
            }
        }

        Collections.sort(subscribed_posts, new Comparator<Post>(){
            public int compare(Post p1, Post p2){
                return Double.compare(p2.getDate(),p1.getDate());
            }
        });

        if(num_posts>9) return new ArrayList<>(subscribed_posts.subList(0, 9));
        else return new ArrayList<>(subscribed_posts.subList(0, num_posts));
    }
}
