package info.androidhive.slidingmenu;

/**
 * Created by VEYSEL on 4.5.2016.
 */
public class TitleClass {
    private String titleDetail;
    String phone;
    private String userName, day, month;

    public TitleClass(String phone,String userName, String titleDetail, String day, String month) {
        this.titleDetail = titleDetail;
        this.userName = userName;
        this.day = day;
        this.month=month;
        this.phone=phone;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDay(){return day;}

    public void setDay(String day) {this.day=day;}

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
