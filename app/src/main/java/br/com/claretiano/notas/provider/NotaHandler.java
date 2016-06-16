package br.com.claretiano.notas.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.claretiano.notas.model.Nota;

public class NotaHandler extends DataHandler {

    protected NotaHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columns = {
                Nota.FullColumns._ID,
                Nota.FullColumns.DISCIPLINA_ID,
                Nota.FullColumns.SEMESTRE_ID,
                Nota.FullColumns.NOTA_PARCIAL,
                Nota.FullColumns.NOTA_FINAL,
        };

        if (code == DataProvider.CODE_NOTA) {
            return db().query(Nota.TABLE_NAME, projection != null ? projection : columns, selection, selectionArgs, null, null, (sortOrder != null ? sortOrder : Nota.Columns._ID));

        } else if (code == DataProvider.CODE_NOTA_RAW) {
            return db().rawQuery(selection, selectionArgs);

        } else {
            long id = ContentUris.parseId(uri);
            return db().query(Nota.TABLE_NAME, projection != null ? projection : columns, Nota.Columns._ID + " = ? ", new String[]{String.valueOf(id)}, null, null, Nota.Columns._ID);
        }
    }

    @Override
    public Cursor rawQuery(String selection, String[] selectionArgs) {
        return db().rawQuery(selection, selectionArgs);
    }

    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        if (code == DataProvider.CODE_NOTA_ID || code == DataProvider.CODE_NOTA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = db().insert(Nota.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_NOTA || code == DataProvider.CODE_NOTA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().update(Nota.TABLE_NAME, values, Nota.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }

    @Override
    public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_NOTA || code == DataProvider.CODE_NOTA_RAW) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().delete(Nota.TABLE_NAME, Nota.Columns._ID + " = ? ", new String[]{String.valueOf(id)});
    }
}
