package br.com.claretiano.notas.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.claretiano.notas.model.Semestre;

public class SemestreHandler extends DataHandler {

    protected SemestreHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columns = {
                Semestre.FullColumns._ID,
                Semestre.FullColumns.DISCIPLINA_ID,
                Semestre.FullColumns.DESCRICAO,
        };

        if (code == DataProvider.CODE_SEMESTRE) {
            return db().query(Semestre.TABLE_NAME, projection != null ? projection : columns, selection, selectionArgs, null, null, (sortOrder != null ? sortOrder : Semestre.Columns.DESCRICAO));

        } else if (code == DataProvider.CODE_SEMESTRE_RAW) {
            return db().rawQuery(selection, selectionArgs);

        } else {
            long id = ContentUris.parseId(uri);
            return db().query(Semestre.TABLE_NAME, projection != null ? projection : columns, Semestre.Columns._ID + " = ? ", new String[]{String.valueOf(id)}, null, null, Semestre.Columns._ID);
        }
    }

    @Override
    public Cursor rawQuery(String selection, String[] selectionArgs) {
        return db().rawQuery(selection, selectionArgs);
    }

    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        if (code == DataProvider.CODE_SEMESTRE_ID || code == DataProvider.CODE_SEMESTRE_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = db().insert(Semestre.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_SEMESTRE || code == DataProvider.CODE_SEMESTRE_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().update(Semestre.TABLE_NAME, values, Semestre.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }

    @Override
    public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_SEMESTRE || code == DataProvider.CODE_SEMESTRE_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().delete(Semestre.TABLE_NAME, Semestre.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }
}
