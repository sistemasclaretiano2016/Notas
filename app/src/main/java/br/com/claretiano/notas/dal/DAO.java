package br.com.claretiano.notas.dal;                                                   
                                                                              
import android.content.ContentResolver;                                       
import android.content.Context;                                               
import android.database.Cursor;                                               
import android.net.Uri;                                                       
                                                                              
// Superclasse de todos os DAOs da aplicação                                  
public abstract class DAO {                                                   
                                                                              
    private Context context;                                                  
    private ContentResolver cr;                                               
                                                                              
    public DAO() {                                                            
    }                                                                         
                                                                              
    public void init(Context context) {                                       
        this.context = context;                                               
        this.cr = context.getContentResolver();                               
    }                                                                         
                                                                              
    protected Context context() {                                             
        return context;                                                       
    }                                                                         
                                                                              
    protected ContentResolver contentResolver() {                             
        return cr;                                                            
    }                                                                         
                                                                              
    protected Cursor rawQuery(Uri uri, String sql, String[] selectionArgs) {  
        return contentResolver().query(uri, null, sql, selectionArgs, null);  
    }                                                                         
}                                                                             
