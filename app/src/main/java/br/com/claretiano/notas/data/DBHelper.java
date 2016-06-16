package br.com.claretiano.notas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.Scanner;

import br.com.claretiano.notas.R;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notasfacul";
    public static final int DB_VERSION = 1;

    private static DBHelper instance;

    private Context context;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public void executaScript(SQLiteDatabase db, int resource) {
        InputStream in = context.getResources().openRawResource(resource);
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(";");
        try {
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();

                if (!sql.equals("")) {
                    db.execSQL(sql);
                }
            }

            Log.d("DB", "Tabelas criadas/atualizadas");
        } catch (Exception ex) {
            Log.d("DB", "Erro ao criar/atualizar tabelas: " + ex.getMessage());
        } finally {
            scanner.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "Iniciar criação das tabelas");
        executaScript(db, R.raw.db_script_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int antigo, int novo) {
        //if (antigo <= 1) {                                                               
        //    executaScript(db, R.raw.db_script_update_1);                                 
        //}                                                                                
    }

    @Override
    // Chamado pelo Android quando o banco de dados é aberto                               
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // Habilita as chaves estrangeiras. O SQLite exige que isto seja feito a cada      
        // abertura do banco de dados                                                      
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}                                                                                          
