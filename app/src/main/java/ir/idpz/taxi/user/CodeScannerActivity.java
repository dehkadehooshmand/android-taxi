package ir.idpz.taxi.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.idpz_lenovo.passengertexi.R;
import com.google.zxing.Result;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.user.Utils.Helpers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CodeScannerActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {


        Pattern pattern = Pattern.compile("type=[a-z]+");
        Matcher matcher = pattern.matcher(rawResult.toString());

        String type = "";
        if (matcher.find(0)) {
            type = matcher.group(0);
        }

        type = type.replace("type=", "");



        Pattern pattern2 = Pattern.compile("id=\\d+");
        Matcher matcher2 = pattern2.matcher(rawResult.toString());
        String id = "";
        if (matcher2.find(0)) {
            id = matcher2.group(0);
        }

        id = id.replace("id=", "");

        getUserData(type, id);

        Intent intent = new Intent(CodeScannerActivity.this, PaymentActivity.class);
        intent.putExtra("KEY_QR_CODE", rawResult.getText());
        startActivity(intent);
        setResult(RESULT_OK, intent);
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
