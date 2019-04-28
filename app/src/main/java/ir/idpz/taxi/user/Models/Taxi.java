
package ir.idpz.taxi.user.Models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Taxi {

    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;
    @SerializedName("station_id")
    private String mStationId;

    @SerializedName("notification_token")
    private String token;

    @SerializedName("capacity")
    private int Capacity;

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        mLng = lng;
    }

    public String getStationId() {
        return mStationId;
    }

    public void setStationId(String stationId) {
        mStationId = stationId;
    }

}
