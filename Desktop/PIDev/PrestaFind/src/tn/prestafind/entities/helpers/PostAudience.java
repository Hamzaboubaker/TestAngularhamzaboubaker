package tn.prestafind.entities.helpers;

public enum PostAudience {
    PUBLIC(0, "Public", "../../resources/media/ic_public.png"),
    FRIENDS(1, "Friends", "../../resources/media/ic_friend.png");

    private int id;
    private String name;
    private String imgSrc;

    PostAudience(int id, String name, String imgSrc) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
