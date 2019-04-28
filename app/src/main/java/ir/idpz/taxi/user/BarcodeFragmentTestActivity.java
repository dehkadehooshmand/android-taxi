package ir.idpz.taxi.user;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.idpz_lenovo.passengertexi.R;

public class BarcodeFragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_fragment_test);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BarcodeFragment bf = new BarcodeFragment();
        ft.add(R.id.container, bf);
        ft.commit();
    }
}
