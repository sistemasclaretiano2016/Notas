package br.com.claretiano.notas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import br.com.claretiano.notas.R;
import br.com.claretiano.notas.model.Nota;

public class AdapterListView extends BaseAdapter {

    private List<Nota> lista;
    private LayoutInflater layout;
    private NumberFormat formato = new DecimalFormat(",##.#");

    public AdapterListView(Context c, List<Nota> lista){
        this.lista = lista;
        this.layout = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;
        if (convertView == null) {
            //The view is not a recycled one: we have to inflate
            convertView = layout.inflate(R.layout.adapter_nota, parent, false);
            holder = new ViewHolder();
            holder.txtDisciplina = (TextView)convertView.findViewById(R.id.txtDisciplina);
            holder.txtSemestre = (TextView)convertView.findViewById(R.id.txtSemestre);
            holder.txtNota1 = (TextView)convertView.findViewById(R.id.txtNota1);
            holder.txtNota2 = (TextView)convertView.findViewById(R.id.txtNota2);
            holder.txtMedia = (TextView)convertView.findViewById(R.id.txtMedia);
            holder.txtSituacao = (TextView)convertView.findViewById(R.id.txtSituacao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Nota nota = lista.get(position);

        holder.txtDisciplina.setText(nota.getDisciplina().getDescricao());
        holder.txtSemestre.setText(nota.getSemestre().getDescricao());
        holder.txtNota1.setText(formato.format(nota.getNota_parcial()));
        holder.txtNota2.setText(formato.format(nota.getNota_final()));
        holder.txtMedia.setText(formato.format(nota.getMediaFinal()));
        holder.txtSituacao.setText(nota.getSituacaoFinal());

        return convertView;
    }

    static class ViewHolder {
        TextView txtDisciplina;
        TextView txtSemestre;
        TextView txtNota1;
        TextView txtNota2;
        TextView txtMedia;
        TextView txtSituacao;
    }

}
