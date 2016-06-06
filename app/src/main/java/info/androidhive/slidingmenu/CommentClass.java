package info.androidhive.slidingmenu;

/**
 * Created by VEYSEL on 28.4.2016.
 */
public class CommentClass {
    String text;
    int like;
    int dislike;
    String id;
    String phoneNumber;
    public  CommentClass(String text,int like,int dislike,String id,String phoneNumber){
        this.text=text;
        this.like=like;
        this.dislike=dislike;
        this.id=id;
        this.phoneNumber=phoneNumber;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
