package tk.lorddarthart.yandexweathertestapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FragmentOne extends Fragment {
    View view;
    Spinner spinnerDropDown;
    String[] countries = {
            "Россия",
            "Другие страны"
    };
    String selectedCountry, selectedDay;
    TextView today, tomorrow;
    Button btnOK;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    ArrayList<City> cityList = new ArrayList<>();
    private Cursor cursor;
    FirebaseAuth auth;
    String uid;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String apiKey;

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_1, container, false);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (user != null&&!(sharedPreferences.getString("uid", "777 -_-").equals("777 -_-"))) {
            uid = sharedPreferences.getString("uid", "777 -_-");
            db.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    apiKey = (String) documentSnapshot.get("apiKey");
                    if (!apiKey.equals("")) {
                        if (apiKey.equals("")) {
                            Fragment frLogin = new FragmentLogin();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frLay, frLogin);
                            transaction.commit();
                        } else {
                            today = view.findViewById(R.id.tvToday);
                            tomorrow = view.findViewById(R.id.tvTomorrow);
                            btnOK = view.findViewById(R.id.btnOk);

                            databaseHelper = new DatabaseHelper(getActivity(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
                            sqLiteDatabase = databaseHelper.getWritableDatabase();
                            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.DATABASE_TABLE_CITIES, new String[0]);
                            if (!(tableExists())) {
                                addCity("Москва", "Россия", " 45.2376", " 37.2236");
                                addCity("Санкт-Петербург", "Россия", "59.89444", "30.26417");
                                addCity("Кемерово", "Россия", "55.33333", "86.08333");
                                addCity("Пенза", "Россия", "53.20066", "45.00464");
                                addCity("Новосибирск", "Россия", "55.04150", "82.93460");
                                addCity("Нижний Новгород", "Россия", "56.32867", "44.00205");
                                addCity("Челябинск", "Россия", "55.15402", "61.42915");
                                addCity("Сургут", "Россия", "61.25000", "73.41667");
                                addCity("Архангельск", "Россия", "64.54010", "40.54330");
                                addCity("Калининград", "Россия", "54.70649", "20.51095");
                                addCity("Париж", "Франция", "48.85341", "2.34880");
                                addCity("Марсель", "Франция", "43.29695", "5.38107");
                                addCity("Берлин", "Германия", "52.52437", "13.41053");
                                addCity("Гамбург", "Германия", "53.57532", "10.01534");
                                addCity("Иматра", "Финляндия", "61.17185", "28.75242");
                                addCity("Котка", "Финлядия", "60.46667", "26.91667");
                                addCity("Варшава", "Польша", "52.22977", "21.01178");
                                addCity("Краков", "Польша", "50.06143", "19.93658");
                                addCity("Алсмер", "Нидерланды", "52.25917", "4.75972");
                                addCity("Роттердам", "Нидерланды", "51.92250", "4.47917");
                            } else {
                                addCitiesToList();
                            }

                            today.setTextColor(Color.BLUE);
                            selectedCountry = "Россия";
                            selectedDay = today.getText().toString();
                            spinnerDropDown = view.findViewById(R.id.spinner);

                            today.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    today.setTextColor(Color.BLUE);
                                    tomorrow.setTextColor(Color.parseColor("#8a000000"));
                                    selectedDay = today.getText().toString();
                                }
                            });

                            tomorrow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tomorrow.setTextColor(Color.BLUE);
                                    today.setTextColor(Color.parseColor("#8a000000"));
                                    selectedDay = tomorrow.getText().toString();
                                }
                            });

                            btnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment fr2 = new FragmentTwo();
                                    ((FragmentTwo) fr2).setCity(cityList);
                                    ((FragmentTwo) fr2).setSelectedCountry(selectedCountry);
                                    ((FragmentTwo) fr2).setSelectedDay(selectedDay);
                                    ((FragmentTwo) fr2).setApiKey(apiKey);
                                    ((FragmentTwo) fr2).setFragment(FragmentOne.this);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frLay, fr2);
                                    transaction.commit();
                                }
                            });

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.
                                    R.layout.simple_spinner_dropdown_item, countries);

                            spinnerDropDown.setAdapter(adapter);

                            spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    int sid = spinnerDropDown.getSelectedItemPosition();
                                    selectedCountry = countries[sid];
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    int sid = spinnerDropDown.getSelectedItemPosition();
                                    selectedCountry = countries[sid];
                                }
                            });
                        }
                    } else {
                        Fragment frDef = new FragmentDefine();
                        ((FragmentDefine) frDef).setUid(uid);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frLay, frDef);
                        transaction.commit();
                    }
                }
            });
        } else {
            Fragment frLogin = new FragmentLogin();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frLay, frLogin);
            transaction.commit();
        }
        return view;
    }

    private void addCity(String cityname, String country,  String latitude, String longitude) {
        City city = new City();
        city.setCity(cityname);
        city.setCountry(country);
        city.setLongitude(longitude);
        city.setLatitude(latitude);
        DatabaseHelper.addCity(sqLiteDatabase, city.getCity(), city.getCountry(), city.getLatitude(), city.getLongitude());
        cityList.add(city);
    }

    private void addCitiesToList() {
        cursor.moveToFirst();
        cursor.moveToPrevious();
        while (cursor.moveToNext()) {
            City city = new City();
            city.setCity(cursor.getString(cursor.getColumnIndex(databaseHelper.CITY_NAME)));
            city.setCountry(cursor.getString(cursor.getColumnIndex(databaseHelper.CITY_COUNTRY)));
            city.setLatitude(cursor.getString(cursor.getColumnIndex(databaseHelper.CITY_LATITUDE)));
            city.setLongitude(cursor.getString(cursor.getColumnIndex(databaseHelper.CITY_LONGITUDE)));
            cityList.add(city);
        }
    }

    private boolean tableExists() {
        return DatabaseUtils.longForQuery(sqLiteDatabase, "SELECT COUNT(*) FROM " + databaseHelper.DATABASE_TABLE_CITIES, null) != 0;
    }
}
