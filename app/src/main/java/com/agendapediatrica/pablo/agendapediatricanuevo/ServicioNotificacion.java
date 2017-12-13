package com.agendapediatrica.pablo.agendapediatricanuevo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static com.agendapediatrica.pablo.agendapediatricanuevo.SignInActivity.notificacionID;


/**
 * Created by Admin on 26/11/2017.
 */

public class ServicioNotificacion extends Service {
    @Override
    public void onCreate() {
        //super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int interval = 1000*60*1; /* dos horas */
        Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_LONG).show();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(getApplicationContext(), CheckPostsReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);

        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pendingIntent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class CheckPostsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            pedirDatosVacunas("");
            //verificarVacunasPendientes(1);
            //new MyNotificationTask( arg0 ).execute();

        }

        private void pedirDatosVacunas(String uri) {
            enviarNotificacion("Titulo Notif", "Cuerpo Notif");
            //AsynTaskVacunasNoti task = new AsynTaskVacunasNoti();
            //task.execute(uri);
        }

        public void enviarNotificacion(String titulo, String vacuna){
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                    //.setSmallIcon(R.drawable.vacuna_image)
                    .setContentTitle(titulo)
                    .setContentText(vacuna)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(notificacionID, mBuilder.build());
        }

        public class AsynTaskVacunasNoti extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String content = "Hola";//HttpManager.getData(params[0]);
                enviarNotificacion("Titulo Not", "Cuerpo Not");
                return content;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                /*if (s == null){
                    Log.e("Error:", "Nulo");
                }else{
                    if (s.equals("C")){
                        // /Toast.makeText(getApplicationContext(), "No se pudo conectar al Web Service", Toast.LENGTH_LONG).show();
                    }else {
                        Log.e("Vacunas", s);
                        vacunasList = VacunasJSONparser.parse(s);
                        if (vacunasList.isEmpty()){

                        }else{
                            if (vacunasList.size() >= 2){
                                enviarNotificacion("Título", "Cuerpo de la Notificación");//EMITIR NOTIFICACION
                            }
                        }
                    }
                }*/
            }
        }

    }
}
