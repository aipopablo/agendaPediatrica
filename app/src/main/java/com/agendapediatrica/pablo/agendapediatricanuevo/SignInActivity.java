package com.agendapediatrica.pablo.agendapediatricanuevo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import jsonparser.UsuarioJSONparser;
import models.Usuario;
import util.HttpGestor;

//para los objetos JSon


public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    //esta es la ip en casa
    public static final String SERVER = "192.168.1.60";
    //esta es la ip en lo de pao
    //public static final String SERVER = "192.168.2.117";
    public static final String PORT = ":8080";

    //public static final String POST_VALIDAR_USUARIO = "/AgendaPediatricaNuevo/webresources/persitenceusuario.usuario/validar";
    public static final String GET_VALIDAR_USUARIO = "/AgendaPediatricaNuevo/webresources/persistenceusuario.usuario/usuario/";
    public static final String GET_HIJOS_USUARIO = "/AgendaPediatricaNuevo/webresources/persistencehijos.hijos/listadohijos/";
    public static final String GET_VACUNAS_HIJO = "/AgendaPediatricaNuevo/webresources/persistencevacunas.vacunas/listadoVacunas/";
    public static final int notificacionID = 123;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;

    private String url;
    private String correo;

    Usuario thisUsuario;

    public Usuario user = new Usuario();

    //private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
            signIn();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleSignInResult(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            //traemos el correo del usuario
            correo = acct.getEmail();

            validarUsuario(correo);

        } else {

            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    /*
    * en esta funcion hacemos las operaciones correspondientes a la validación
    * del iusuario
    * */
    public  void  validarUsuario (String correo){
        AsyncTaskUsu task = new AsyncTaskUsu();

        url = "http://"+ SERVER + PORT + GET_VALIDAR_USUARIO + correo ;

        task.execute(url);
    }

    private void ListadoHijos(String usu){
        Intent intent = new Intent(this, VistaHijos.class);
        intent.putExtra("UsuarioCadenaJson", usu);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    //generé de vuelta el asynctask de porquería
    private class AsyncTaskUsu extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            try {
                super.onPreExecute();

            } catch (Exception e) {
                String error = e.toString();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String usu = HttpGestor.getData(strings[0]);

            return usu;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null){
                //progress.dismiss();
                Toast.makeText(getApplicationContext(), "El usuario no esta registrado en la base de datos!", Toast.LENGTH_SHORT).show();
            }else{
                try {

                    thisUsuario = UsuarioJSONparser.parse(s);
                    if (thisUsuario != null){
                        Intent intent = new Intent(getApplicationContext(), VistaHijos.class);
                        intent.putExtra("usuarioStringJSON", s);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }


    //asynctask anterior, igual al otro, pero más bonito
    /*
    private class AsyncTaskUsuario extends AsyncTask<String, String, String> {
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
        protected void onPostExecute(String o) {
            super.onPostExecute(o);

            if (o == null){
                //progress.dismiss();
                Toast.makeText(getApplicationContext(), "El usuario no esta registrado en la base de datos!", Toast.LENGTH_SHORT).show();
            }else{
                try {

                    thisUsuario = UsuarioJSONparser.parse(o);
                    if (thisUsuario != null){
                        Intent intent = new Intent(getApplicationContext(), VistaHijos.class);
                        intent.putExtra("usuarioStringJSON", o);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }



    }
    */


}
