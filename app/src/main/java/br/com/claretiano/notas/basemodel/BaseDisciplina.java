package br.com.claretiano.notas.basemodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import br.com.claretiano.notas.dal.DAOFactory;

public abstract class BaseDisciplina extends BaseObject {

    public static final String TABLE_NAME = "disciplina";

    private Long id;
    private String descricao;
    private Double nota_minima;

    public BaseDisciplina(BaseDisciplina t) {
        super();
        this.id = t.id;
        this.descricao = t.descricao;
        this.nota_minima = t.nota_minima;
   }

    public BaseDisciplina() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getNota_minima() {
        return nota_minima;
    }

    public void setNota_minima(Double nota_minima) {
        this.nota_minima = nota_minima;
    }

    public ContentValues values() {
        ContentValues values = new ContentValues();

        if (descricao != null) {
            values.put(Columns.DESCRICAO, descricao);
        }

        if (nota_minima != null) {
            values.put(Columns.NOTA_MINIMA, nota_minima);
        }

        return values;
    }

    protected void loadFromCursor(Cursor c, boolean lazyLoading, Context context) {
        clear();

        int index = c.getColumnIndex(Columns._ID);
        if (index > -1 && !c.isNull(index)) {
            setId(c.getLong(index));
        }

        index = c.getColumnIndex(Columns.DESCRICAO);
        if (index > -1 && !c.isNull(index)) {
            setDescricao(c.getString(index));
        }

        index = c.getColumnIndex(Columns.NOTA_MINIMA);
        if (index > -1 && !c.isNull(index)) {
            setNota_minima(c.getDouble(index));
        }

    }

    protected void clear() {
        setId(null);
        setDescricao(null);
        setNota_minima(null);
    }

    public static final class Columns {
        public static final String _ID = "_id";
        public static final String DESCRICAO = "descricao";
        public static final String NOTA_MINIMA = "nota_minima";
    }

    public static final class FullColumns {
        public static final String _ID = TABLE_NAME + "." + Columns._ID;
        public static final String DESCRICAO = TABLE_NAME + "." + Columns.DESCRICAO;
        public static final String NOTA_MINIMA = TABLE_NAME + "." + Columns.NOTA_MINIMA;
    }

    public static final class Aliases {
        public static final String _ID = TABLE_NAME + "_" + Columns._ID;
        public static final String DESCRICAO = TABLE_NAME + "_" + Columns.DESCRICAO;
        public static final String NOTA_MINIMA = TABLE_NAME + "_" + Columns.NOTA_MINIMA;
    }

}
