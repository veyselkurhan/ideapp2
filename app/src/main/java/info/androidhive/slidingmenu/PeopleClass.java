package info.androidhive.slidingmenu;

/**
 * Created by VEYSEL on 5.5.2016.
 */
public class PeopleClass {
    String name;
    String phone;
boolean engelleme;
    boolean takip;
    public PeopleClass(String name, String phone,boolean engelleme,boolean takip) {
        this.name = name;
        this.phone = phone;
        this.engelleme=engelleme;
        this.takip=takip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEngelleme() {
        return engelleme;
    }

    public void setEngelleme(boolean engelleme) {
        this.engelleme = engelleme;
    }

    public boolean isTakip() {
        return takip;
    }

    public void setTakip(boolean takip) {
        this.takip = takip;
    }
}
