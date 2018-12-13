package tk.lorddarthart.yandexweathertestapp;

import android.content.Context;
import android.support.annotation.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CityListAdapter extends ArrayAdapter<CityList> {

    private Context context;
    private int resource;

    public CityListAdapter(Context context, int resource, ArrayList<CityList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String city = getItem(position).getCity();
        String country = getItem(position).getCountry();

        CityList cityList = new CityList(city, country);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView tvCity = convertView.findViewById(R.id.tvCity);
        TextView tvCountry = convertView.findViewById(R.id.tvCountry);

        tvCity.setText(cityList.getCity());
        tvCountry.setText(cityList.getCountry());

        return convertView;
    }
}
