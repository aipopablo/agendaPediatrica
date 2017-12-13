package jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.Hijo;

/**
 * Created by Admin on 5/11/2017.
 */

public class HijosJSONparser {

    public static List<Hijo> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Hijo> hijosList = new ArrayList<>();

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Hijo hijo = new Hijo();

                hijo.setId(jsonObject.getInt("id"));
                hijo.setNombre(jsonObject.getString("nombre"));
                hijo.setEdad(jsonObject.getInt("edad"));
                hijo.setSexo(jsonObject.getString("sexo"));

                hijosList.add(hijo);
            }
            return hijosList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Hijo parseHijo(String content)/* throws UnsupportedEncodingException*/ {

        try {

            JSONObject jsonObject = new JSONObject(content);//jsonArray.getJSONObject(i);
            Hijo hijo = new Hijo();

            hijo.setId(jsonObject.getInt("id"));
            hijo.setNombre(jsonObject.getString("nombre"));
            hijo.setEdad(jsonObject.getInt("edad"));
            hijo.setSexo(jsonObject.getString("sexo"));

            return hijo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}