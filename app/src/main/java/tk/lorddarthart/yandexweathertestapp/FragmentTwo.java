package tk.lorddarthart.yandexweathertestapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentTwo extends Fragment {
    private View view;
    private ArrayList<City> city = new ArrayList<>();
    private ArrayList<CityList> cityList = new ArrayList<>();
    private String selectedCountry;
    private String selectedDay;
    private ListView cityListView;
    private String apiKey;
    private int cityposition;
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_2, container, false);

        cityListView = view.findViewById(R.id.cityList);

        if (city.size()>0) {
            if (selectedCountry.equals("Россия")) {
                for (int i = 0; i < city.size(); i++) {
                    if (city.get(i).getCountry().equals("Россия")) {
                        cityList.add(new CityList(city.get(i).getCity(), city.get(i).getCountry()));
                    }
                }
            } else {
                for (int i = 0; i < city.size(); i++) {
                    if (!(city.get(i).getCountry().equals("Россия"))) {
                        cityList.add(new CityList(city.get(i).getCity(), city.get(i).getCountry()));
                    }
                }
            }
        }

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityList o = (CityList) cityListView.getItemAtPosition(position);
                String cityName = o.getCity();
                String country = o.getCountry();
                if (country.equals("Россия")) {
                    cityposition = position;
                } else {
                    cityposition = position+10;
                }
                Fragment fr3 = new FragmentThree();
                ((FragmentThree) fr3).setCity(city);
                ((FragmentThree) fr3).setSelectedCity(cityName);
                ((FragmentThree) fr3).setSelectedCountry(country);
                ((FragmentThree) fr3).setSelectedDay(selectedDay);
                ((FragmentThree) fr3).setApiKey(apiKey);
                ((FragmentThree) fr3).setPosition(cityposition);
                ((FragmentThree) fr3).setFragment(FragmentTwo.this);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frLay, fr3);
                transaction.commit();

            }
        });

        CityListAdapter arrayAdapter = new CityListAdapter(getActivity(), R.layout.adapter_citylistview, cityList);
        cityListView.setAdapter(arrayAdapter);

        return view;
    }
}
