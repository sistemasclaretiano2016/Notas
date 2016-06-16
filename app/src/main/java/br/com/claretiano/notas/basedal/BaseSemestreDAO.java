package br.com.claretiano.notas.basedal;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.claretiano.notas.dal.DAO;
import br.com.claretiano.notas.model.Semestre;
import br.com.claretiano.notas.provider.DataProvider;

public abstract class BaseSemestreDAO extends DAO {

    public BaseSemestreDAO() {
    }

    public boolean salvar(Semestre semestre) {
        return semestre.getId() != null && semestre.getId() > 0
                ? update(semestre)
                : insert(semestre);
    }

    private boolean insert(Semestre semestre) {
        Uri newUri = contentResolver().insert(DataProvider.CONTENT_SEMESTRE_URI, semestre.values()); 

        long id = ContentUris.parseId(newUri);
        semestre.setId(id);
        return id > 0;
    }

    private boolean update(Semestre semestre) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_SEMESTRE_URI, semestre.getId());
        return contentResolver().update(uri, semestre.values(), null, null) > 0;
    }

    public int delete(Semestre semestre) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_SEMESTRE_URI, semestre.getId());
        return contentResolver().delete(uri, null, null);
    }

    public void deleteAll() {
        rawQuery(DataProvider.CONTENT_SEMESTRE_RAW_URI, "DELETE FROM " + Semestre.TABLE_NAME, null);
        rawQuery(DataProvider.CONTENT_SEMESTRE_RAW_URI, "UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = '" + Semestre.TABLE_NAME + "'", null);
        rawQuery(DataProvider.CONTENT_SEMESTRE_RAW_URI, "VACUUM", null);
    }

    public Semestre getSemestreById(long id, boolean... lazyLoading) {
        Semestre semestre = new Semestre();

        Cursor c = getSemestreByIdLoader(id).loadInBackground();
        try {
            if (c != null && c.moveToFirst())
                semestre.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
        } finally {
            if (c != null)
                c.close();
        }
        return semestre;
    }

    public List<Semestre> getSemestresPorDisciplina_idList(long disciplina_id, boolean... lazyLoading) {

        List<Semestre> semestres = new ArrayList<>();

        Cursor c = getSemestresPorDisciplina_idCursor(disciplina_id);
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Semestre t = new Semestre();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    semestres.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return semestres;
    }

    public Cursor getSemestresPorDisciplina_idCursor(long disciplina_id) {

        String selection = Semestre.FullColumns.DISCIPLINA_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id)};

        return contentResolver().query(DataProvider.CONTENT_SEMESTRE_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getSemestresPorDisciplina_idLoader(long disciplina_id) {

        String selection = Semestre.FullColumns.DISCIPLINA_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id)};

        return new CursorLoader(context(), DataProvider.CONTENT_SEMESTRE_URI, null, selection, selectionArgs, null);
    }

    public List<Semestre> getSemestresPorDescricaoList(String descricao, boolean... lazyLoading) {

        List<Semestre> semestres = new ArrayList<>();

        Cursor c = getSemestresPorDescricaoCursor(descricao);
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Semestre t = new Semestre();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    semestres.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return semestres;
    }

    public Cursor getSemestresPorDescricaoCursor(String descricao) {

        String selection = Semestre.FullColumns.DESCRICAO + " = ? ";
        String[] selectionArgs = {String.valueOf(descricao)};

        return contentResolver().query(DataProvider.CONTENT_SEMESTRE_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getSemestresPorDescricaoLoader(String descricao) {

        String selection = Semestre.FullColumns.DESCRICAO + " = ? ";
        String[] selectionArgs = {String.valueOf(descricao)};

        return new CursorLoader(context(), DataProvider.CONTENT_SEMESTRE_URI, null, selection, selectionArgs, null);
    }

    public List<Semestre> getSemestresList(boolean... lazyLoading) {

        List<Semestre> semestres = new ArrayList<>();

        Cursor c = getSemestres();
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Semestre t = new Semestre();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    semestres.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return semestres;
    }

    public Cursor getSemestres() {
        return contentResolver().query(DataProvider.CONTENT_SEMESTRE_URI, null, null, null, null);
    }

    public CursorLoader getSemestresLoader() {
        return new CursorLoader(context(), DataProvider.CONTENT_SEMESTRE_URI, null, null, null, null);
    }

    public CursorLoader getSemestresLoader(Bundle args) {
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

        return new CursorLoader(context(), DataProvider.CONTENT_SEMESTRE_URI, null, selection, selectionArgs, orderBy);
    }

    public CursorLoader getSemestreByIdLoader(long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_SEMESTRE_URI, id);
        return new CursorLoader(context(), uri, null, null, null, null);
    }

    public void notifySemestre() {
        contentResolver().notifyChange(DataProvider.CONTENT_SEMESTRE_URI, null, true);
    }

}
