package ir.idpz.taxi.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.idpz_lenovo.passengertexi.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.user.Map.MapsActivity;
import ir.idpz.taxi.user.Utils.Helpers;

public class QrReaderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
        Intent i = new Intent(QrReaderActivity.this,QrCodeActivity.class);
        startActivityForResult( i,REQUEST_CODE_QR_SCAN);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("result","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if(result!=null)
            {       //String test=" http://idpz.ir/getprice?type=taxi&id=8";


                Pattern pattern = Pattern.compile("type=[a-z]+");
                Matcher matcher = pattern.matcher(result.toString());//todo result bejaye test

                String type = "";
                if (matcher.find(0)) {
                    type = matcher.group(0);
                }

                type = type.replace("type=", "");



                Pattern pattern2 = Pattern.compile("id=\\d+");
                Matcher matcher2 = pattern2.matcher(result.toString());
                String id = "";
                if (matcher2.find(0)) {
                    id = matcher2.group(0);
                }

                id = id.replace("id=", "");

                getUserData(type, id);

                Intent intent = new Intent(QrReaderActivity.this, PaymentActivity.class);
                intent.putExtra("KEY_QR_CODE", result);
                startActivity(intent);
                setResult(RESULT_OK, intent);
                finish();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            //todo vaghti dost scan nemikone
            Toast.makeText(this, "با موفقیت اسکن نشد.", Toast.LENGTH_SHORT).show();

        }
    }

    public void sendId(){
        RequestParams params=new RequestParams();
        params.put("id","");
        Helpers.client.post("", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Intent i1 = new Intent(getApplicationContext(), PaymentActivity.class);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i1);
                overridePendingTransition(0, 0);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i1 = new Intent(getApplicationContext(), MapsActivity.class);
        i1.setAction(Intent.ACTION_MAIN);
        i1.addCategory(Intent.CATEGORY_HOME);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i1);
        overridePendingTransition(0, 0);

        finish();
    }

    public void getUserData(String type, String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("type", type);
        params.put("api_token", Helpers.getSharePrf("api_token"));
        String url = Helpers.baseUrl + "qrcode";
        Helpers.client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });

    }
}
