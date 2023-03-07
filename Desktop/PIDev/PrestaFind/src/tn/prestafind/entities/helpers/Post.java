package tn.prestafind.entities.helpers;

public class Post {

    private Account account;
    private PostAudience audience;
    private String date;
    private String caption;
    private String image;
    private int totalReactions;
    private int nbComments;
    private int nbShares;
    private int id;
    private int nbLikes;
    private int nbDisLikes;
    private int nbReports;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PostAudience getAudience() {
        return audience;
    }

    public void setAudience(PostAudience audience) {
        this.audience = audience;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(int totalReactions) {
        this.totalReactions = totalReactions;
    }

    public int getNbComments() {
        return nbComments;
    }

    public void setNbComments(int nbComments) {
        this.nbComments = nbComments;
    }

    public int getNbShares() {
        return nbShares;
    }

    public void setNbShares(int nbShares) {
        this.nbShares = nbShares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public int getNbDisLikes() {
        return nbDisLikes;
    }

    public void setNbDisLikes(int disLikes) {
        this.nbDisLikes = disLikes;
    }

    public int getNbReports() {
        return nbReports;
    }

    public void setNbReports(int nbReports) {
        this.nbReports = nbReports;
    }

}
