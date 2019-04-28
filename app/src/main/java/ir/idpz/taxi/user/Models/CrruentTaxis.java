
package ir.idpz.taxi.user.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CrruentTaxis {

    @SerializedName("status")
    private String mStatus;
    @SerializedName("taxi")
    private List<Taxi> mTaxi;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public List<Taxi> getTaxis() {
        return mTaxi;
    }

    public void setTaxis(List<Taxi> taxi) {
        mTaxi = taxi;
    }

}
