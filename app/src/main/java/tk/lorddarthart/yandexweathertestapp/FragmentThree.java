package tk.lorddarthart.yandexweathertestapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FragmentThree extends Fragment {
    private View view;
    private ArrayList<City> city = new ArrayList<>();
    private String selectedCountry;
    private String selectedDay;
    private String selectedCity;
    private HttpServiceHelper httpServiceHelper;
    private String apiKey, realTemp, feelsLike;
    private JSONObject jsonObject;
    private SimpleDateFormat sdf;
    private Date selectedTaD;
    private int position;
    private TextView txtFr3Date;
    private TextView txtFr3City;
    private TextView txtFr3FeelsLike;
    private TextView txtFr3Temp;
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_3, container, false);

        try {
            new JSONer().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            selectedTaD = new Date(Long.valueOf(String.valueOf(jsonObject.getLong("now"))+"000"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtFr3Date = view.findViewById(R.id.txtFr3Date);
        txtFr3City = view.findViewById(R.id.txtFr3City);
        txtFr3FeelsLike = view.findViewById(R.id.txtFr3FeelsLike);
        txtFr3Temp = view.findViewById(R.id.txtFr3Temp);

        final TextView txtFr3Today = view.findViewById(R.id.txtFr3Today);
        final TextView txtFr3Tomorrow = view.findViewById(R.id.txtFr3Tomorrow);
        final TextView txtFr3AfterTomorrow = view.findViewById(R.id.txtFr3AfterTomorrow);

        txtFr3Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFr3Today.setTextColor(Color.BLUE);
                txtFr3Tomorrow.setTextColor(Color.parseColor("#8a000000"));
                txtFr3AfterTomorrow.setTextColor(Color.parseColor("#8a000000"));
                selectedDay="Сегодня";
                getTodayTemp();
            }
        });
        txtFr3Tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFr3Tomorrow.setTextColor(Color.BLUE);
                txtFr3Today.setTextColor(Color.parseColor("#8a000000"));
                txtFr3AfterTomorrow.setTextColor(Color.parseColor("#8a000000"));
                selectedDay="Завтра";
                getTomorrowTemp();
            }
        });
        txtFr3AfterTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFr3Tomorrow.setTextColor(Color.parseColor("#8a000000"));
                txtFr3Today.setTextColor(Color.parseColor("#8a000000"));
                txtFr3AfterTomorrow.setTextColor(Color.BLUE);
                selectedDay="Послезавтра";
                getAfterTomorrowTemp();
            }
        });

        switch (selectedDay) {
            case "Сегодня":{
                txtFr3Today.setTextColor(Color.BLUE);
                txtFr3Tomorrow.setTextColor(Color.parseColor("#8a000000"));
                txtFr3AfterTomorrow.setTextColor(Color.parseColor("#8a000000"));
                getTodayTemp();
                break;
            }
            case "Завтра":{
                txtFr3Tomorrow.setTextColor(Color.BLUE);
                txtFr3Today.setTextColor(Color.parseColor("#8a000000"));
                txtFr3AfterTomorrow.setTextColor(Color.parseColor("#8a000000"));
                getTomorrowTemp();
                break;
            }
        }

        txtFr3Date.setText(sdf.format(selectedTaD));
        txtFr3City.setText(city.get(position).getCity());
        txtFr3FeelsLike.setText("Ощущается как "+feelsLike+"°");
        txtFr3Temp.setText(""+realTemp+"°");
        return view;
    }

    private void getTodayTemp() {
        try {
            selectedTaD = new Date(Long.valueOf(String.valueOf(jsonObject.getLong("now"))+"000"));

            if (jsonObject.getJSONObject("fact").getInt("feels_like")>0) {
                feelsLike="+"+jsonObject.getJSONObject("fact").getInt("feels_like");
            }else {
                feelsLike=(String.valueOf(jsonObject.getJSONObject("fact").getInt("feels_like")));
            }
            if (jsonObject.getJSONObject("fact").getInt("temp")>0) {
                realTemp="+"+jsonObject.getJSONObject("fact").getInt("temp");
            }else {
                realTemp=(String.valueOf(jsonObject.getJSONObject("fact").getInt("temp")));
            }

            txtFr3Date.setText(sdf.format(selectedTaD));
            txtFr3City.setText(city.get(position).getCity());
            txtFr3FeelsLike.setText("Ощущается как "+feelsLike+"°");
            txtFr3Temp.setText(""+realTemp+"°");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getTomorrowTemp() {
        try {
            selectedTaD = new Date(Long.valueOf(String.valueOf(jsonObject.getLong("now"))+"000"));
            Calendar cl = Calendar.getInstance();
            cl.setTime(selectedTaD);
            cl.add(Calendar.DAY_OF_MONTH, 1);
            selectedTaD = cl.getTime();

            if (jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("feels_like")>0) {
                feelsLike="+"+jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("feels_like");
            }else {
                feelsLike=(String.valueOf(jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("feels_like")));
            }
            if (jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("temp_avg")>0) {
                realTemp="+"+jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("temp_avg");
            }else {
                realTemp=(String.valueOf(jsonObject.getJSONArray("forecasts").getJSONObject(1).getJSONObject("parts").getJSONObject("day").getInt("temp_avg")));
            }

            txtFr3Date.setText(sdf.format(selectedTaD));
            txtFr3City.setText(city.get(position).getCity());
            txtFr3FeelsLike.setText("Ощущается как "+feelsLike+"°");
            txtFr3Temp.setText(""+realTemp+"°");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAfterTomorrowTemp() {
        try {
            selectedTaD = new Date(Long.valueOf(String.valueOf(jsonObject.getLong("now"))+"000"));
            Calendar cl = Calendar.getInstance();
            cl.setTime(selectedTaD);
            cl.add(Calendar.DAY_OF_MONTH, 2);
            selectedTaD = cl.getTime();

            if (jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("feels_like")>0) {
                feelsLike="+"+jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("feels_like");
            }else {
                feelsLike=(String.valueOf(jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("feels_like")));
            }
            if (jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("temp_avg")>0) {
                realTemp="+"+jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("temp_avg");
            }else {
                realTemp=(String.valueOf(jsonObject.getJSONArray("forecasts").getJSONObject(2).getJSONObject("parts").getJSONObject("day").getInt("temp_avg")));
            }

            txtFr3Date.setText(sdf.format(selectedTaD));
            txtFr3City.setText(city.get(position).getCity());
            txtFr3FeelsLike.setText("Ощущается как "+feelsLike+"°");
            txtFr3Temp.setText(""+realTemp+"°");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class JSONer extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                httpServiceHelper = new HttpServiceHelper();
                jsonObject = httpServiceHelper.getWeather(new City(city.get(position).getCity(),city.get(position).getCountry(),city.get(position).getLatitude(),city.get(position).getLongitude()), apiKey);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
