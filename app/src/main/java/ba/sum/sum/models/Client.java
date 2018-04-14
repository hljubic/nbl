package ba.sum.sum.models;

import com.google.gson.annotations.SerializedName;

import ba.hljubic.jsonorm.JsonTable;

/**
 * Created by HP_PC on 14.4.2018..
 */

public class Client extends JsonTable<Client> {
    private String title;
    @SerializedName("contact_person")
    private String person;
    @SerializedName("contact_phone")
    private String phone;
    private String latitude;
    private String longitude;

    public Client() {
    }

    public Client(String title, String person, String phone, String latitude, String longitude) {
        this.title = title;
        this.person = person;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
