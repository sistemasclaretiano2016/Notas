package br.com.claretiano.notas.dal;

import android.database.Cursor;

import br.com.claretiano.notas.basedal.BaseNotaDAO;
import br.com.claretiano.notas.model.Nota;

public class NotaDAO extends BaseNotaDAO {

    public Nota getNotaPorDisciplinaESemestre(long disciplina_id, long semestre_id, boolean... lazyLoading) {
        Nota nota = new Nota();

        Cursor c = getNotasPorParametros(disciplina_id, semestre_id);
        try {
            if (c != null && c.moveToFirst())
                nota.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
        } finally {
            if (c != null)
                c.close();
        }
        return nota;
    }

}
