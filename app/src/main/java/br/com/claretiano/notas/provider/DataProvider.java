package br.com.claretiano.notas.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

// Content provider para acessar o banco de dados SQLite
public class DataProvider extends ContentProvider {

    static final int CODE_DISCIPLINA = 1;
    static final int CODE_DISCIPLINA_ID = 2;
    static final int CODE_DISCIPLINA_RAW = 3;
    static final int CODE_NOTA = 4;
    static final int CODE_NOTA_ID = 5;
    static final int CODE_NOTA_RAW = 6;
    static final int CODE_SEMESTRE = 7;
    static final int CODE_SEMESTRE_ID = 8;
    static final int CODE_SEMESTRE_RAW = 9;

    private static final String AUTHORITY = "br.com.claretiano.notas.provider.DataProvider";

    public static final Uri CONTENT_DISCIPLINA_URI = Uri.parse("content://" + AUTHORITY + "/disciplina");
    public static final Uri CONTENT_DISCIPLINA_RAW_URI = Uri.parse("content://" + AUTHORITY + "/disciplina_raw");
    public static final Uri CONTENT_NOTA_URI = Uri.parse("content://" + AUTHORITY + "/nota");
    public static final Uri CONTENT_NOTA_RAW_URI = Uri.parse("content://" + AUTHORITY + "/nota_raw");
    public static final Uri CONTENT_SEMESTRE_URI = Uri.parse("content://" + AUTHORITY + "/semestre");
    public static final Uri CONTENT_SEMESTRE_RAW_URI = Uri.parse("content://" + AUTHORITY + "/semestre_raw");

private UriMatcher matcher;

    // Os handlers são usados para tratar requisições, de acordo com o padrão da URI utilizada
    private DataHandler disciplinaHandler;
    private DataHandler notaHandler;
    private DataHandler semestreHandler;

    @Override
    public boolean onCreate() {
        // Cria os handlers
        disciplinaHandler = new DisciplinaHandler(getContext());
        notaHandler = new NotaHandler(getContext());
        semestreHandler = new SemestreHandler(getContext());

        // Configura o URI matcher
        matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, "disciplina", CODE_DISCIPLINA);
        matcher.addURI(AUTHORITY, "disciplina/#", CODE_DISCIPLINA_ID);
        matcher.addURI(AUTHORITY, "disciplina_raw", CODE_DISCIPLINA_RAW);
        matcher.addURI(AUTHORITY, "nota", CODE_NOTA);
        matcher.addURI(AUTHORITY, "nota/#", CODE_NOTA_ID);
        matcher.addURI(AUTHORITY, "nota_raw", CODE_NOTA_RAW);
        matcher.addURI(AUTHORITY, "semestre", CODE_SEMESTRE);
        matcher.addURI(AUTHORITY, "semestre/#", CODE_SEMESTRE_ID);
        matcher.addURI(AUTHORITY, "semestre_raw", CODE_SEMESTRE_RAW);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        Cursor c = handler.query(code, uri, projection, selection, selectionArgs, sortOrder);
        handler.setNotificationUri(c, uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        Uri newUri = handler.insert(code, uri, values);
        handler.notifyChange(newUri);
        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        int count = handler.update(code, uri, values, selection, selectionArgs);
        handler.notifyChange(uri);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        int count = handler.delete(code, uri, selection, selectionArgs);
        handler.notifyChange(uri);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        int code = matcher.match(uri);

        if (code == CODE_DISCIPLINA_ID
                || code == CODE_NOTA_ID
                || code == CODE_SEMESTRE_ID) {
            return "vnd.android.cursor.item/vnd.br.com.imperiumsolucoes";

        } else if (code == CODE_DISCIPLINA || code == CODE_DISCIPLINA_RAW
                || code == CODE_NOTA || code == CODE_NOTA_RAW
                || code == CODE_SEMESTRE || code == CODE_SEMESTRE_RAW) {
            return "vnd.android.cursor.dir/vnd.br.com.imperiumsolucoes";
        }
        throw new IllegalArgumentException("URI not supported: " + uri);
    }

    private DataHandler getHandler(int code) {
        if (code == CODE_DISCIPLINA || code == CODE_DISCIPLINA_ID || code == CODE_DISCIPLINA_RAW) {
            return disciplinaHandler;

        } else if (code == CODE_NOTA || code == CODE_NOTA_ID || code == CODE_NOTA_RAW) {
            return notaHandler;

        } else if (code == CODE_SEMESTRE || code == CODE_SEMESTRE_ID || code == CODE_SEMESTRE_RAW) {
            return semestreHandler;

        }
        return null;
    }
}
