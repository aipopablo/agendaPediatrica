package com.agendapediatrica.pablo.agendapediatricanuevo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//para los objetos JSon
import org.json.JSONException;
import org.json.JSONObject;

import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;
import models.Usuario;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final String SERVER = "192.168.1.60";
    private static final String PORT = ":8080";

    private static final String POST_VALIDAR_USUARIO = "/AgendaPediatricaNuevo/webresources/persitencia.usuario/validar";
    private static final String GET_VALIDAR_USUARIO = "/AgendaPediatricaNuevo/webresources/persitencia.usuario/validar/";


    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;

    private String url;
    private String correo;

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) throws JSONException {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            //traemos el correo del usuario
            correo = acct.getEmail();

            validarUsuario(correo);

            goMainScreen();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

            //updateUI(true);
        } else {

            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }
    /*
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            //mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void goMainScreen(){
        Intent intent = new Intent(this, VistaHijos.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*
    * en esta funcion hacemos las operaciones correspondientes a la validación
    * del iusuario
    * */
    public  void  validarUsuario (String correo){
            AsyncTaskUsuario task = new AsyncTaskUsuario();

            url = SERVER + PORT + GET_VALIDAR_USUARIO + correo ;

            task.execute(url);
    }

    private class AsyncTaskUsuario extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            HttpClient client = new HttpClient(new OnHttpRequestComplete() {
                @Override
                public String onComplete(Response status) {
                    String result = "";
                    if(status.isSuccess()){

                        result = status.getResult();

                    }
                    else{

                    }

                    return result;
                }
            });

            client.excecute(params[0]);

            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);

            if (o == null){
                //progress.dismiss();
                Toast.makeText(getApplicationContext(), "El usuario no esta registrado en la base de datos!", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    //creamos el chirimbolo del JSon
                    Gson gson = new GsonBuilder().create();

                    JSONObject jsonobj = new JSONObject(o);

                    //Deserializamos el objeto para validar
                    Usuario usu = gson.fromJson(o, Usuario.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }


}
