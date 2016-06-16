package br.com.claretiano.notas.basedal;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.claretiano.notas.dal.DAO;
import br.com.claretiano.notas.model.Nota;
import br.com.claretiano.notas.provider.DataProvider;

public abstract class BaseNotaDAO extends DAO {

    public BaseNotaDAO() {
    }

    public boolean salvar(Nota nota) {
        return nota.getId() != null && nota.getId() > 0
                ? update(nota)
                : insert(nota);
    }

    private boolean insert(Nota nota) {
        Uri newUri = contentResolver().insert(DataProvider.CONTENT_NOTA_URI, nota.values()); 

        long id = ContentUris.parseId(newUri);
        nota.setId(id);
        return id > 0;
    }

    private boolean update(Nota nota) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_NOTA_URI, nota.getId());
        return contentResolver().update(uri, nota.values(), null, null) > 0;
    }

    public int delete(Nota nota) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_NOTA_URI, nota.getId());
        return contentResolver().delete(uri, null, null);
    }

    public void deleteAll() {
        rawQuery(DataProvider.CONTENT_NOTA_RAW_URI, "DELETE FROM " + Nota.TABLE_NAME, null);
        rawQuery(DataProvider.CONTENT_NOTA_RAW_URI, "UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = '" + Nota.TABLE_NAME + "'", null);
        rawQuery(DataProvider.CONTENT_NOTA_RAW_URI, "VACUUM", null);
    }

    public Nota getNotaById(long id, boolean... lazyLoading) {
        Nota nota = new Nota();

        Cursor c = getNotaByIdLoader(id).loadInBackground();
        try {
            if (c != null && c.moveToFirst())
                nota.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
        } finally {
            if (c != null)
                c.close();
        }
        return nota;
    }

    public List<Nota> getNotasPorDisciplina_idList(long disciplina_id, boolean... lazyLoading) {

        List<Nota> notas = new ArrayList<>();

        Cursor c = getNotasPorDisciplina_idCursor(disciplina_id);
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Nota t = new Nota();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    notas.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return notas;
    }

    public Cursor getNotasPorDisciplina_idCursor(long disciplina_id) {

        String selection = Nota.FullColumns.DISCIPLINA_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id)};

        return contentResolver().query(DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getNotasPorDisciplina_idLoader(long disciplina_id) {

        String selection = Nota.FullColumns.DISCIPLINA_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id)};

        return new CursorLoader(context(), DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public List<Nota> getNotasPorSemestre_idList(long semestre_id, boolean... lazyLoading) {

        List<Nota> notas = new ArrayList<>();

        Cursor c = getNotasPorSemestre_idCursor(semestre_id);
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Nota t = new Nota();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    notas.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return notas;
    }

    public Cursor getNotasPorSemestre_idCursor(long semestre_id) {

        String selection = Nota.FullColumns.SEMESTRE_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(semestre_id)};

        return contentResolver().query(DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getNotasPorSemestre_idLoader(long semestre_id) {

        String selection = Nota.FullColumns.SEMESTRE_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(semestre_id)};

        return new CursorLoader(context(), DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public List<Nota> getNotasPorParametrosList(long disciplina_id, long semestre_id, boolean... lazyLoading) {

        List<Nota> notas = new ArrayList<>();

        Cursor c = getNotasPorParametros(disciplina_id, semestre_id);
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Nota t = new Nota();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    notas.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return notas;
    }

    public Cursor getNotasPorParametros(long disciplina_id, long semestre_id) {

        String selection = Nota.FullColumns.DISCIPLINA_ID + " = ? AND " + Nota.FullColumns.SEMESTRE_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id), String.valueOf(semestre_id)};

        return contentResolver().query(DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public CursorLoader getNotasPorParametrosLoader(long disciplina_id, long semestre_id) {

        String selection = Nota.FullColumns.DISCIPLINA_ID + " = ? AND " + Nota.FullColumns.SEMESTRE_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(disciplina_id), String.valueOf(semestre_id)};

        return new CursorLoader(context(), DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, null);
    }

    public List<Nota> getNotasList(boolean... lazyLoading) {

        List<Nota> notas = new ArrayList<>();

        Cursor c = getNotas();
        try {
            if (c != null && c.moveToFirst()) {
                do {
                    Nota t = new Nota();
                    t.loadFromCursor(c, lazyLoading.length > 0 && lazyLoading[0], context());
                    notas.add(t);
                } while (c.moveToNext());
            }
        } finally {
            if (c != null)
                c.close();
        }
        return notas;
    }

    public Cursor getNotas() {
        return contentResolver().query(DataProvider.CONTENT_NOTA_URI, null, null, null, null);
    }

    public CursorLoader getNotasLoader() {
        return new CursorLoader(context(), DataProvider.CONTENT_NOTA_URI, null, null, null, null);
    }

    public CursorLoader getNotasLoader(Bundle args) {
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

        return new CursorLoader(context(), DataProvider.CONTENT_NOTA_URI, null, selection, selectionArgs, orderBy);
    }

    public CursorLoader getNotaByIdLoader(long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_NOTA_URI, id);
        return new CursorLoader(context(), uri, null, null, null, null);
    }

    public void notifyNota() {
        contentResolver().notifyChange(DataProvider.CONTENT_NOTA_URI, null, true);
    }

}
