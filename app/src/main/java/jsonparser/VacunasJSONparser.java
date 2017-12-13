package jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Vacuna;

/**
 * Created by Admin on 16/11/2017.
 */

public class VacunasJSONparser {
    public static List<Vacuna> parse(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Vacuna> vacunasList = new ArrayList<>();

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Vacuna vacuna = new Vacuna();

                vacuna.setId(jsonObject.getInt("id"));
                vacuna.setNombreVacuna(jsonObject.getString("nombre"));
                vacuna.setFechaAplicacion(jsonObject.getString("fechaAplicacion"));
                vacuna.setAplicada(jsonObject.getString("aplicada"));

                vacunasList.add(vacuna);
            }
            return vacunasList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
