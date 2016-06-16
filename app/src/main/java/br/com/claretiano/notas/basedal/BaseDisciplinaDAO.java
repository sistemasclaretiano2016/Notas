package br.com.claretiano.notas.basedal;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.claretiano.notas.dal.DAO;
import br.com.claretiano.notas.model.Disciplina;
import br.com.claretiano.notas.provider.DataProvider;

public abstract class BaseDisciplinaDAO extends DAO {

    public BaseDisciplinaDAO() {
    }

    public boolean salvar(Disciplina disciplina) {
        return disciplina.getId() != null && disciplina.getId() > 0
                ? update(disciplina)
                : insert(disciplina);
    }

    private boolean insert(Disciplina disciplina) {
        Uri newUri = contentResolver().insert(DataProvider.CONTENT_DISCIPLINA_URI, disciplina.values()); 

        long id = ContentUris.parseId(newUri);
        disciplina.setId(id);
        return id > 0;
    }

    private boolean update(Disciplina disciplina) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DISCIPLINA_URI, disciplina.getId());
        return contentResolver().update(uri, disciplina.values(), null, null) > 0;
    }

    public int delete(Disciplina disciplina) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DISCIPLINA_URI, disciplina.getId());
        return contentResolver().delete(uri, null, null);
    }

    public void deleteAll() {
        rawQuery(DataProvider.CONTENT_DISCIPLINA_RAW_URI, "DELETE FROM " + Disciplina.TABLE_NAME, null);
        rawQuery(DataProvider.CONTENT_DISCIPLINA_RAW_URI, "UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = '" + Disciplina.TABLE_NAME + "'", null);
        rawQuery(DataProvider.CONTENT_DISCIPLINA_RAW_URI, "VACUUM", null);
    }

    public Disciplina getDisciplinaById(long id, boolean... lazyLoading) {
        Disciplina disciplina = new Disciplina();

        Cursor c = getDisciplinaByIdLoader(id).loadInBackground();
        try {
            if (c != null && c.moveToFirst())
                disciplina.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
        } finally {
            if (c != null)
                c.close();
        }
        return disciplina;
    }

    public List<Disciplina> getDisciplinasPorDescricaoList(String descricao) {

        List<Disciplina> disciplinas = new ArrayList<>();

        Cursor c = getDisciplinasPorDescricaoCursor(descricao);
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

    public Cursor getDisciplinasPorDescricaoCursor(String descricao) {

        String selection = Disciplina.FullColumns.DESCRICAO + " = ? ";
        String[] selectionArgs = {String.valueOf(descricao)};

        return contentResolver().query(DataProvider.CONTENT_DISCIPLINA_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getDisciplinasPorDescricaoLoader(String descricao) {

        String selection = Disciplina.FullColumns.DESCRICAO + " = ? ";
        String[] selectionArgs = {String.valueOf(descricao)};

        return new CursorLoader(context(), DataProvider.CONTENT_DISCIPLINA_URI, null, selection, selectionArgs, null);
    }

    public List<Disciplina> getDisciplinasList() {

        List<Disciplina> disciplinas = new ArrayList<>();

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

    public Cursor getDisciplinas() {
        return contentResolver().query(DataProvider.CONTENT_DISCIPLINA_URI, null, null, null, null);
    }

    public CursorLoader getDisciplinasLoader() {
        return new CursorLoader(context(), DataProvider.CONTENT_DISCIPLINA_URI, null, null, null, null);
    }

    public CursorLoader getDisciplinasLoader(Bundle args) {
        String selection = null;
        String[] selectionArgs = null;

        String orderBy = null;
        if (args != null) {
            orderBy = args.getString("orderBy");

            if (args.getString("where") != null) {
                selection = args.getString("where");
                selectionArgs = args.getStringArray("whereArray");
            }
        }

        return new CursorLoader(context(), DataProvider.CONTENT_DISCIPLINA_URI, null, selection, selectionArgs, orderBy);
    }

    public CursorLoader getDisciplinaByIdLoader(long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DISCIPLINA_URI, id);
        return new CursorLoader(context(), uri, null, null, null, null);
    }

    public void notifyDisciplina() {
        contentResolver().notifyChange(DataProvider.CONTENT_DISCIPLINA_URI, null, true);
    }

}
