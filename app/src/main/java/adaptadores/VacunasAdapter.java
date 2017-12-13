package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agendapediatrica.pablo.agendapediatricanuevo.R;

import java.util.ArrayList;
import java.util.List;

import models.Vacuna;

/**
 * Created by Admin on 16/11/2017.
 */

public class VacunasAdapter extends BaseAdapter {
    List<Vacuna> listVacunas = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public VacunasAdapter(List<Vacuna> listVacunas, Context context) {
        this.listVacunas = listVacunas;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return listVacunas.size();
    }

    @Override
    public Vacuna getItem(int position) {
        return listVacunas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_vacuna, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Vacuna vacuna = getItem(position);
        viewHolder.nombreVacuna.setText(vacuna.getNombreVacuna());
        viewHolder.fechaAplicacion.setText(vacuna.getFechaAplicacionString());
        viewHolder.aplicada.setText(vacuna.getAplicadaExtendida());
        return convertView;
    }

    public class ViewHolder{
        TextView nombreVacuna;
        TextView fechaAplicacion;
        TextView aplicada;

        public ViewHolder(View item){
            nombreVacuna = (TextView) item.findViewById(R.id.nombreVacuna);
            fechaAplicacion = (TextView) item.findViewById(R.id.fechaAplicacion);
            aplicada = (TextView) item.findViewById(R.id.aplicada);
        }
    }
}