package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.agendapediatrica.pablo.agendapediatricanuevo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import models.Hijo;

/**
 * Created by Admin on 5/11/2017.
 */

public class HijosAdapter extends BaseAdapter {
    List<Hijo> listHijos = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public HijosAdapter(List<Hijo> listHijos, Context context) {
        this.listHijos = listHijos;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return listHijos.size();
    }

    @Override
    public Hijo getItem(int position) {
        return listHijos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Hijo hijo = getItem(position);
        viewHolder.nombreHijo.setText(hijo.getNombre());
        viewHolder.sexoHijo.setText(hijo.getSexoExtendido());
        viewHolder.edadHijo.setText("Edad: "+Integer.toString(hijo.getEdad())+" años");
        //viewHolder.hijoImage.setImageResource(hijo.getDrawImage());
        return convertView;
    }

    public class ViewHolder{
        TextView nombreHijo;
        TextView sexoHijo;
        TextView edadHijo;
        ImageView hijoImage;

        public ViewHolder(View item){
            nombreHijo = (TextView) item.findViewById(R.id.nombreHijo);
            sexoHijo = (TextView) item.findViewById(R.id.sexoHijo);
            edadHijo = (TextView) item.findViewById(R.id.edadHijo);
            //hijoImage = (ImageView) item.findViewById(R.id.hijoImage);
        }
    }
}