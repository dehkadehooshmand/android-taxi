package ir.idpz.taxi.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.idpz_lenovo.passengertexi.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.user.Map.MapsActivity;
import ir.idpz.taxi.user.Models.User;
import ir.idpz.taxi.user.Utils.Helpers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    TextView txt_mobile;

    Toolbar toolbar;
    ImageView arrow_back;

    EditText name, email;
    Spinner dropdown;
    int gender;
    Button btn_confrim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        btn_confrim = findViewById(R.id.btn_confrim);

        btn_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
        name = findViewById(R.id.txt_name);

        email = findViewById(R.id.txt_email);


        toolbar = findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.TRANSPARENT);

        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_mobile = findViewById(R.id.txt_mobile);
        dropdown = findViewById(R.id.gender);
        setProfile();


//create a list of items for the spinner.
        String[] items = new String[]{"زن", "مرد"};

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = 0;
                } else gender = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


    }

    public void setProfile() {
        RequestParams params = new RequestParams();

        try {
            params.put("mobile", Helpers.getMobile());
            params.put("api_token",Helpers.getSharePrf("api_token"));

        } catch (Exception e) {
        }




        Helpers.client.post(Helpers.baseUrl + "user_profile", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.contains("ok")) {
                    try {
                        User user=Helpers.gson.fromJson(responseString, User.class);


                        txt_mobile.setText(Helpers.getMobile());
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        if (user.getGender().equals("0"))
                            dropdown.setSelection(0);
                        else dropdown.setSelection(1);
                        Helpers.setEmail(user.getEmail());
                        Helpers.setName(user.getName());
                        Helpers.setgender(user.getGender());
                    } catch (Exception e) {
                    }


                }

            }
        });
    }

    public void UpdateProfile() {

        RequestParams params = new RequestParams();

        params.put("name", name.getText().toString());
        try {
            params.put("mobile", Helpers.getMobile());
            params.put("api_token",Helpers.getSharePrf("api_token"));

        } catch (Exception e) {
        }

        params.put("email", email.getText().toString());
        params.put("gender", String.valueOf(gender));


        Helpers.client.post(Helpers.baseUrl + "user_update", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.contains("ok")) {
//                    Helpers.setEmail(email.getText().toString());
//                    Helpers.setName(name.getText().toString());
//                    Helpers.setgender(gender);
                    dialog();
                }

            }
        });
    }


    public void dialog() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.pardakhtdialog);
        dialog.setCancelable(false);
        dialog.show();
        Button btnGo = dialog.findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
