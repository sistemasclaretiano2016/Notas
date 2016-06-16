package br.com.claretiano.notas.model;

import android.content.Context;
import android.database.Cursor;

import br.com.claretiano.notas.basemodel.BaseNota;

public class Nota extends BaseNota {

    public void loadFromCursor(Cursor c) {
        loadFromCursor(c, false, null);
    }

    public void loadFromCursor(Cursor c, boolean lazyLoading, Context context) {
        super.loadFromCursor(c, lazyLoading, context);
    }

    public void clear() {
        super.clear();
    }

    public void lazyLoading(Context context) {
        super.lazyLoading(context);
    }

    public double getMediaFinal() {
        return (getNota_parcial() + getNota_final()) / 2;
    }

    public String getSituacaoFinal() {
        if (getDisciplina() != null) {
            if (getMediaFinal() >= getDisciplina().getNota_minima()) {
                return "Aprovado";
            } else {
                return "Reprovado";
            }
        }
        return "Sem informação";
    }

}
