package ir.idpz.taxi.user.Utils;

import ir.idpz.taxi.user.Models.Area;
import ir.idpz.taxi.user.Models.Line;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyServices {

    @FormUrlEncoded
    @POST("j/getdb.php")
    Call<List<Area>> getAreas(@Field("db") String state);

    @FormUrlEncoded
    @POST("getlines")
    Call<List<Line>> getLines(@Field("id") int id);

}
