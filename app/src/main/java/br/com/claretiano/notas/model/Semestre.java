package br.com.claretiano.notas.model;

import android.content.Context;
import android.database.Cursor;

import br.com.claretiano.notas.basemodel.BaseSemestre;

public class Semestre extends BaseSemestre {

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

    @Override
    public String toString() {
        return getDescricao();
    }
}
