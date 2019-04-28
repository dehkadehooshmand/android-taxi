package ir.idpz.taxi.user.Utils;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    Context mcontext;
    private static OkHttpClient.Builder httpClient;

    /**
     * @param isJsonApi اگر مقدار true باشد اطلاعات به صورت جیسون جابه جا می شوند اگر مقدار false باشد مقادیر به صورت چارامتر جا به جا می شوند
     * @return
     */

    //http://hpen.ir/test/public_html/api/v1/
    //http://192.168.1.246/hpenbackend/public_html/api/v1/
    protected static Retrofit getRefrofit(boolean isJsonApi) {
        Retrofit retrofit;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://admin.idpz.ir/api/");
        if (isJsonApi) {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            builder.addConverterFactory(new JSONAPIConverterFactory(objectMapper))
                    .build().create(MyServices.class);
            ResourceConverter converter = new ResourceConverter();
            converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
        } else
            builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        return retrofit;
    }

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Retrofit getClient() {
        return getRefrofit(true);
    }

    public static Retrofit getClient(Boolean isJsonApi) {
        return getRefrofit(isJsonApi);
    }

}
