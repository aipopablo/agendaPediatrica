package jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

import models.Usuario;

/**
 * Created by Admin on 24/10/2017.
 */

public class UsuarioJSONparser {
    public static Usuario parse(String content)/* throws UnsupportedEncodingException*/ {

        try {

            JSONObject jsonObject = new JSONObject(content);//jsonArray.getJSONObject(i);
            Usuario user = new Usuario();

            user.setIdUsuario(jsonObject.getInt("id"));
            user.setNombreUsuario(jsonObject.getString("nombre"));
            user.setEmailUsuario(jsonObject.getString("correo"));

            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
