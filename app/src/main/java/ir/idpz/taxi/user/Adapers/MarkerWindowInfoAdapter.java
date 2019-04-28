package ir.idpz.taxi.user.Adapers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idpz_lenovo.passengertexi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import java.util.List;


import ir.idpz.taxi.user.Models.Taxi;
import ir.idpz.taxi.user.Utils.AppController;

/**
 * Created by hpen on 7/26/2018.
 */

public class MarkerWindowInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    List<Taxi> taxis;

    public MarkerWindowInfoAdapter(Context context, List<Taxi> taxis) {
        this.context = context.getApplicationContext();
        this.taxis = taxis;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.info_window_layout, null);
        TextView capacity = v.findViewById(R.id.title);

        ImageView p1 = v.findViewById(R.id.p1);

        ImageView p2 = v.findViewById(R.id.p2);
        ImageView p3 = v.findViewById(R.id.p3);
        ImageView p4 = v.findViewById(R.id.p4);


        for (Taxi taxi : taxis) {
            int empty_capacity = 4 - taxi.getCapacity();

            capacity.setText(String.valueOf("ظرفیت خالی " + empty_capacity + "نفر"));
            if (taxi.getId().equals(marker.getTag())) {

                if (empty_capacity == 0) {
                    p1.setImageResource(R.drawable.person_black);
                    p2.setImageResource(R.drawable.person_black);
                    p3.setImageResource(R.drawable.person_black);
                    p4.setImageResource(R.drawable.person_black);
                }


                if (empty_capacity == 1) {
                    p1.setImageResource(R.drawable.person_white);
                    p2.setImageResource(R.drawable.person_black);
                    p3.setImageResource(R.drawable.person_black);
                    p4.setImageResource(R.drawable.person_black);
                } else if (empty_capacity == 2) {
                    p1.setImageResource(R.drawable.person_black);
                    p2.setImageResource(R.drawable.person_black);
                    p3.setImageResource(R.drawable.person_white);
                    p4.setImageResource(R.drawable.person_white);
                } else if (empty_capacity == 3) {
                    p1.setImageResource(R.drawable.person_white);
                    p2.setImageResource(R.drawable.person_white);
                    p3.setImageResource(R.drawable.person_white);
                    p4.setImageResource(R.drawable.person_black);

                } else if (empty_capacity == 4) {
                    p1.setImageResource(R.drawable.person_white);
                    p2.setImageResource(R.drawable.person_white);
                    p3.setImageResource(R.drawable.person_white);
                    p4.setImageResource(R.drawable.person_white);
                }


            }
        }


//
//        Typeface face = Typeface.createFromAsset(context.getAssets(),
//                "fonts/IRANSans(FaNum).ttf");
//


        return v;
    }
}