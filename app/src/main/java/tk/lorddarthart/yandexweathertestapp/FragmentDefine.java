package tk.lorddarthart.yandexweathertestapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class FragmentDefine extends Fragment {
    private View view;
    private EditText etFrAPIpart1, etFrAPIpart2, etFrAPIpart3, etFrAPIpart4, etFrAPIpart5;
    private Button btnSave;
    private HttpServiceHelper httpServiceHelper;
    private int responseCode;
    private String apiKey;
    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_define, container, false);

        etFrAPIpart1 = view.findViewById(R.id.etFrAPIpart1);
        etFrAPIpart2 = view.findViewById(R.id.etFrAPIpart2);
        etFrAPIpart3 = view.findViewById(R.id.etFrAPIpart3);
        etFrAPIpart4 = view.findViewById(R.id.etFrAPIpart4);
        etFrAPIpart5 = view.findViewById(R.id.etFrAPIpart5);

        final Button btnSave = view.findViewById(R.id.button);

        etFrAPIpart1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onEtFrAPIpart1Completed()) {
                    if (!onEtFrAPIpart2Completed()) {
                        etFrAPIpart2.requestFocus();
                    } else {
                        if (!onEtFrAPIpart3Completed()) {
                            etFrAPIpart3.requestFocus();
                        } else {
                            if (!onEtFrAPIpart4Completed()) {
                                etFrAPIpart4.requestFocus();
                            } else {
                                if (!onEtFrAPIpart5Completed()) {
                                    etFrAPIpart5.requestFocus();
                                }
                            }
                        }
                    }
                }
                if (onEtFrAPIpart1Completed()&&onEtFrAPIpart2Completed()&&onEtFrAPIpart3Completed()&&onEtFrAPIpart4Completed()&&onEtFrAPIpart5Completed()) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFrAPIpart2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onEtFrAPIpart2Completed()) {
                    if (!onEtFrAPIpart1Completed()) {
                        etFrAPIpart1.requestFocus();
                    } else {
                        if (!onEtFrAPIpart3Completed()) {
                            etFrAPIpart3.requestFocus();
                        } else {
                            if (!onEtFrAPIpart4Completed()) {
                                etFrAPIpart4.requestFocus();
                            } else {
                                if (!onEtFrAPIpart5Completed()) {
                                    etFrAPIpart5.requestFocus();
                                }
                            }
                        }
                    }
                }
                if (onEtFrAPIpart1Completed()&&onEtFrAPIpart2Completed()&&onEtFrAPIpart3Completed()&&onEtFrAPIpart4Completed()&&onEtFrAPIpart5Completed()) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFrAPIpart3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onEtFrAPIpart3Completed()) {
                    if (!onEtFrAPIpart1Completed()) {
                        etFrAPIpart1.requestFocus();
                    } else {
                        if (!onEtFrAPIpart2Completed()) {
                            etFrAPIpart2.requestFocus();
                        } else {
                            if (!onEtFrAPIpart4Completed()) {
                                etFrAPIpart4.requestFocus();
                            } else {
                                if (!onEtFrAPIpart5Completed()) {
                                    etFrAPIpart5.requestFocus();
                                }
                            }
                        }
                    }
                }
                if (onEtFrAPIpart1Completed()&&onEtFrAPIpart2Completed()&&onEtFrAPIpart3Completed()&&onEtFrAPIpart4Completed()&&onEtFrAPIpart5Completed()) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFrAPIpart4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onEtFrAPIpart4Completed()) {
                    if (!onEtFrAPIpart1Completed()) {
                        etFrAPIpart1.requestFocus();
                    } else {
                        if (!onEtFrAPIpart2Completed()) {
                            etFrAPIpart2.requestFocus();
                        } else {
                            if (!onEtFrAPIpart3Completed()) {
                                etFrAPIpart3.requestFocus();
                            } else {
                                if (!onEtFrAPIpart5Completed()) {
                                    etFrAPIpart5.requestFocus();
                                }
                            }
                        }
                    }
                }
                if (onEtFrAPIpart1Completed()&&onEtFrAPIpart2Completed()&&onEtFrAPIpart3Completed()&&onEtFrAPIpart4Completed()&&onEtFrAPIpart5Completed()) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFrAPIpart5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onEtFrAPIpart5Completed()) {
                    if (!onEtFrAPIpart1Completed()) {
                        etFrAPIpart1.requestFocus();
                    } else {
                        if (!onEtFrAPIpart2Completed()) {
                            etFrAPIpart2.requestFocus();
                        } else {
                            if (!onEtFrAPIpart3Completed()) {
                                etFrAPIpart3.requestFocus();
                            } else {
                                if (!onEtFrAPIpart4Completed()) {
                                    etFrAPIpart4.requestFocus();
                                }
                            }
                        }
                    }
                }
                if (onEtFrAPIpart1Completed()&&onEtFrAPIpart2Completed()&&onEtFrAPIpart3Completed()&&onEtFrAPIpart4Completed()&&onEtFrAPIpart5Completed()) {
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiKey = etFrAPIpart1.getText()+"-"+etFrAPIpart2.getText()+"-"+etFrAPIpart3.getText()+"-"+etFrAPIpart4.getText()+"-"+etFrAPIpart5.getText();
                final HashMap lol = new HashMap();
                lol.put("apiKey", apiKey);
                FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        try {
                            new ResponseCode().execute().get();
                            if (responseCode==200) {
                                lol.put("apiKey", apiKey);
                                FirebaseFirestore.getInstance().collection("users").document(uid).set(lol);
                                Fragment fr1 = new FragmentOne();
                                ((FragmentOne) fr1).setUid(uid);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frLay, fr1);
                                transaction.commit();
                            }else{
                                Snackbar.make(view, "Произошла ошибка, код ответа сервера: " + responseCode, Snackbar.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return view;
    }

    private boolean onEtFrAPIpart1Completed(){
        return etFrAPIpart1.getText().length()==8;
    }

    private boolean onEtFrAPIpart2Completed(){
        return etFrAPIpart2.getText().length()==4;
    }

    private boolean onEtFrAPIpart3Completed(){
        return etFrAPIpart3.getText().length()==4;
    }

    private boolean onEtFrAPIpart4Completed(){
        return etFrAPIpart4.getText().length()==4;
    }

    private boolean onEtFrAPIpart5Completed(){
        return etFrAPIpart5.getText().length()==12;
    }

    private class ResponseCode extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                httpServiceHelper = new HttpServiceHelper();
                responseCode = httpServiceHelper.checkConnection(apiKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
