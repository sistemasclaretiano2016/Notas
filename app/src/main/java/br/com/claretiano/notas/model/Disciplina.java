package br.com.claretiano.notas.model;

import android.content.Context;
import android.database.Cursor;

import br.com.claretiano.notas.basemodel.BaseDisciplina;

public class Disciplina extends BaseDisciplina {

    public void loadFromCursor(Cursor c) {
        loadFromCursor(c, false, null);
    }

    public void loadFromCursor(Cursor c, boolean lazyLoading, Context context) {
        super.loadFromCursor(c, lazyLoading, context);
    }

    public void clear() {
        super.clear();
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
