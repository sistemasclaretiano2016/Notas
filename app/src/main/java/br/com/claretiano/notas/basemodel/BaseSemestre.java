package br.com.claretiano.notas.basemodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import br.com.claretiano.notas.dal.DAOFactory;
import br.com.claretiano.notas.model.Disciplina;

public abstract class BaseSemestre extends BaseObject {

    public static final String TABLE_NAME = "semestre";

    private Long id;
    private Long disciplina_id;
    private String descricao;

    private Disciplina disciplina;

    public BaseSemestre(BaseSemestre t) {
        super();
        this.id = t.id;
        this.disciplina_id = t.disciplina_id;
        this.descricao = t.descricao;
        this.disciplina = t.disciplina;
   }

    public BaseSemestre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisciplina_id() {
        return disciplina_id;
    }

    public void setDisciplina_id(Long disciplina_id) {
        this.disciplina_id = disciplina_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public ContentValues values() {
        ContentValues values = new ContentValues();

        if (disciplina_id != null) {
            values.put(Columns.DISCIPLINA_ID, disciplina_id);
        }

        if (descricao != null) {
            values.put(Columns.DESCRICAO, descricao);
        }

        return values;
    }

    protected void loadFromCursor(Cursor c, boolean lazyLoading, Context context) {
        clear();

        int index = c.getColumnIndex(Columns._ID);
        if (index > -1 && !c.isNull(index)) {
            setId(c.getLong(index));
        }

        index = c.getColumnIndex(Columns.DISCIPLINA_ID);
        if (index > -1 && !c.isNull(index)) {
            setDisciplina_id(c.getLong(index));
        }

        index = c.getColumnIndex(Columns.DESCRICAO);
        if (index > -1 && !c.isNull(index)) {
            setDescricao(c.getString(index));
        }

        if (lazyLoading)
            lazyLoading(context);
    }

    protected void clear() {
        setId(null);
        setDisciplina_id(null);
        setDescricao(null);
        disciplina = null;
    }

    protected void lazyLoading(Context context) {
        if (disciplina_id != null)
            disciplina = DAOFactory.getDisciplinaDAO(context).getDisciplinaById(disciplina_id);

    }

    public static final class Columns {
        public static final String _ID = "_id";
        public static final String DISCIPLINA_ID = "disciplina_id";
        public static final String DESCRICAO = "descricao";
    }

    public static final class FullColumns {
        public static final String _ID = TABLE_NAME + "." + Columns._ID;
        public static final String DISCIPLINA_ID = TABLE_NAME + "." + Columns.DISCIPLINA_ID;
        public static final String DESCRICAO = TABLE_NAME + "." + Columns.DESCRICAO;
    }

    public static final class Aliases {
        public static final String _ID = TABLE_NAME + "_" + Columns._ID;
        public static final String DISCIPLINA_ID = TABLE_NAME + "_" + Columns.DISCIPLINA_ID;
        public static final String DESCRICAO = TABLE_NAME + "_" + Columns.DESCRICAO;
    }

}
