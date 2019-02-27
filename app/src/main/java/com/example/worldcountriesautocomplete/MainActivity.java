package com.example.worldcountriesautocomplete;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CountryItem> countryItemList;
    ArrayList<HashMap<String, String>> countryCode = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            JSONArray m_jArray = obj.getJSONArray("countries");
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArray.length(); i++) {
                JSONObject jo_inside = m_jArray.getJSONObject(i);
                String counterCode = jo_inside.getString("Code").toLowerCase();
                String countryName = jo_inside.getString("Name");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("code", counterCode);
                m_li.put("name", countryName);

                countryCode.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fillCountryList();

        AutoCompleteTextView editText = findViewById(R.id.actv);
        AutoCompleteCountryAdapter autoCompleteCountryAdapter = new AutoCompleteCountryAdapter(this,countryItemList);
        editText.setAdapter(autoCompleteCountryAdapter);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,countryItemList);
        Spinner spinner = findViewById(R.id.country_spinner);

        spinner.setAdapter(spinnerAdapter);

        ImageView imageView = findViewById(R.id.image_view);
        String url = "https://theculturetrip.com/wp-content/uploads/2017/01/flag-map-denmark-puerto.jpg";
        Picasso.with(this)
                .load(url)
                // resize uses pixells
                // resizeDimen uses dp
//                .resize(750,500)
                .resizeDimen(R.dimen.image_size,R.dimen.image_size)
                .centerInside()
                //fit is used to fit the pre set dimens in layout.xml file
//                .fit()
                // makes sure the u down scale of large images only use onlyScaleDown
                .onlyScaleDown()
                .into(imageView);

    }

    public void fillCountryList(){
        countryItemList = new ArrayList<>();
        for (int i =0;i<countryCode.size();i++) {
            String code = countryCode.get(i).get("code");
            countryItemList.add(new CountryItem(countryCode.get(i).get("name"),getResources().getIdentifier(code, "drawable", getPackageName())));
        }
    }


    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }

}
