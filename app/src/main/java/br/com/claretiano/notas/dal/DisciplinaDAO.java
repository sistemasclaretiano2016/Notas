package br.com.claretiano.notas.dal;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.claretiano.notas.basedal.BaseDisciplinaDAO;
import br.com.claretiano.notas.model.Disciplina;

public class DisciplinaDAO extends BaseDisciplinaDAO {

    public List<Disciplina> getDisciplinasListComDefault() {

        List<Disciplina> disciplinas = new ArrayList<>();
        Disciplina def = new Disciplina();
        def.setId(-1L);
        def.setDescricao("Todas");
        def.setNota_minima(0D);
        disciplinas.add(def);

        Cursor c = getDisciplinas();
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Disciplina t = new Disciplina();
                    t.loadFromCursor(c);
                    disciplinas.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return disciplinas;
    }

}
