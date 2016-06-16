package br.com.claretiano.notas.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.claretiano.notas.model.Disciplina;

public class DisciplinaHandler extends DataHandler {

    protected DisciplinaHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columns = {
                Disciplina.FullColumns._ID,
                Disciplina.FullColumns.DESCRICAO,
                Disciplina.FullColumns.NOTA_MINIMA,
        };

        if (code == DataProvider.CODE_DISCIPLINA) {
            return db().query(Disciplina.TABLE_NAME, projection != null ? projection : columns, selection, selectionArgs, null, null, (sortOrder != null ? sortOrder : Disciplina.Columns.DESCRICAO));

        } else if (code == DataProvider.CODE_DISCIPLINA_RAW) {
            return db().rawQuery(selection, selectionArgs);

        } else {
            long id = ContentUris.parseId(uri);
            return db().query(Disciplina.TABLE_NAME, projection != null ? projection : columns, Disciplina.Columns._ID + " = ? ", new String[]{String.valueOf(id)}, null, null, Disciplina.Columns._ID);
        }
    }

    @Override
    public Cursor rawQuery(String selection, String[] selectionArgs) {
        return db().rawQuery(selection, selectionArgs);
    }

    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        if (code == DataProvider.CODE_DISCIPLINA_ID || code == DataProvider.CODE_DISCIPLINA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = db().insert(Disciplina.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_DISCIPLINA || code == DataProvider.CODE_DISCIPLINA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().update(Disciplina.TABLE_NAME, values, Disciplina.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }

    @Override
    public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_DISCIPLINA || code == DataProvider.CODE_DISCIPLINA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().delete(Disciplina.TABLE_NAME, Disciplina.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }
}
