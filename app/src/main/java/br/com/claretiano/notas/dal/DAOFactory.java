package br.com.claretiano.notas.dal;

import android.content.Context;

public class DAOFactory {

    private static DisciplinaDAO disciplinaDAO;
    private static NotaDAO notaDAO;
    private static SemestreDAO semestreDAO;

    public static DisciplinaDAO getDisciplinaDAO(Context context) {
        if (disciplinaDAO == null) {
            disciplinaDAO = new DisciplinaDAO();
            disciplinaDAO.init(context.getApplicationContext());
        }
        return disciplinaDAO;
    }

    public static NotaDAO getNotaDAO(Context context) {
        if (notaDAO == null) {
            notaDAO = new NotaDAO();
            notaDAO.init(context.getApplicationContext());
        }
        return notaDAO;
    }

    public static SemestreDAO getSemestreDAO(Context context) {
        if (semestreDAO == null) {
            semestreDAO = new SemestreDAO();
            semestreDAO.init(context.getApplicationContext());
        }
        return semestreDAO;
    }

}
