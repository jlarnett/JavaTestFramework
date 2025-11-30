package entity_contracts;

import java.util.List;

public class Post {
    public int id;
    public String summary;
    public String creationDate;
    public String userId;
    public User user;
    public Integer parentPostId;
    public Post parentPost;
    public int likeCount;
    public int dislikeCount;
    public boolean userDislikedPost;
    public boolean userLikedPost;
    public boolean hasImagesAttached;
    public List<String> imageFiles;
    public String uuid;
}
