package com.agendapediatrica.pablo.agendapediatricanuevo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adaptadores.HijosAdapter;
import jsonparser.HijosJSONparser;
import models.Hijo;
import models.Usuario;
import util.HttpGestor;

import static com.agendapediatrica.pablo.agendapediatricanuevo.SignInActivity.GET_HIJOS_USUARIO;
import static com.agendapediatrica.pablo.agendapediatricanuevo.SignInActivity.PORT;
import static com.agendapediatrica.pablo.agendapediatricanuevo.SignInActivity.SERVER;
import static com.agendapediatrica.pablo.agendapediatricanuevo.SignInActivity.notificacionID;

public class VistaHijos extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Usuario usuario;//PADRE
    String usuarioStringJSON;
    List<Hijo> hijosList;
    HijosAdapter hijosAdapter;
    ListView listViewHijos;
    Button btnCerrarSesin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_hijos);
        btnCerrarSesin = (Button)findViewById(R.id.btnCerrarSesion);

        try {

            usuarioStringJSON = getIntent().getStringExtra("usuarioStringJSON");

            JSONObject jsonObject = new JSONObject(usuarioStringJSON);//jsonArray.getJSONObject(i);
            Usuario user = new Usuario();

            user.setIdUsuario(jsonObject.getInt("id"));
            user.setNombreUsuario(jsonObject.getString("desusuario"));
            user.setEmailUsuario(jsonObject.getString("correo"));

        } catch (Exception e) {

        }


        listViewHijos= (ListView)findViewById(R.id.listViewHijos);

        hijosList = new ArrayList<>();

        validarHijos(usuario.getIdUsuario());

        listViewHijos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Clic Hijos", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Pos: "+position+" . Id:"+hijosList.get(position).getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), VistaVacunas.class);
                intent.putExtra("idHijo", hijosList.get(position).getId());
                intent.putExtra("nombreHijo", hijosList.get(position).getNombre());
                startActivity(intent);
            }
        });

        btnCerrarSesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogInScreen();
            }
        });
    }

    private void goLogInScreen(){
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void validarHijos(int idPadre){
        pedirDatosHijos("http://"+ SERVER +PORT+GET_HIJOS_USUARIO+idPadre);
    };

    public void mostrarLista (Context context, List<Hijo> ListHijos){
        hijosAdapter = new HijosAdapter(ListHijos, context);
        listViewHijos.setAdapter(hijosAdapter);
    }

    public void pedirDatosHijos(String uri){
        AsynTaskHijos task = new AsynTaskHijos();
        task.execute(uri);
    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    public void enviarNotificacion(String titulo, String vacuna){
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                //.setSmallIcon(R.drawable.vacuna_image)
                .setContentTitle(titulo)
                .setContentText(vacuna)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(notificacionID, mBuilder.build());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class AsynTaskHijos extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String usu = HttpGestor.getData(params[0]);
            return usu;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null){
                Log.e("Error:", "Nulo");
                //Toast.makeText(getApplicationContext(), "Retorno Nulo OnPostExecute!", Toast.LENGTH_SHORT).show();
            }else{
                hijosList = HijosJSONparser.parse(s);
                if (hijosList.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No se han encontrado hijos/as!", Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(getApplicationContext(), ListaHijos.class);
                        intent.putExtra("usuarioStringJSON", s);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                }else{
                    mostrarLista(getApplication(), hijosList);
                }
            }
        }
    }


}
