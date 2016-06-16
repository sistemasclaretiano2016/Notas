package br.com.claretiano.notas.basemodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import br.com.claretiano.notas.dal.DAOFactory;
import br.com.claretiano.notas.model.Disciplina;
import br.com.claretiano.notas.model.Semestre;

public abstract class BaseNota extends BaseObject {

    public static final String TABLE_NAME = "nota";

    private Long id;
    private Long disciplina_id;
    private Long semestre_id;
    private Double nota_parcial;
    private Double nota_final;

    private Disciplina disciplina;
    private Semestre semestre;

    public BaseNota(BaseNota t) {
        super();
        this.id = t.id;
        this.disciplina_id = t.disciplina_id;
        this.semestre_id = t.semestre_id;
        this.nota_parcial = t.nota_parcial;
        this.nota_final = t.nota_final;
        this.disciplina = t.disciplina;
        this.semestre = t.semestre;
   }

    public BaseNota() {
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

    public Long getSemestre_id() {
        return semestre_id;
    }

    public void setSemestre_id(Long semestre_id) {
        this.semestre_id = semestre_id;
    }

    public Double getNota_parcial() {
        return nota_parcial;
    }

    public void setNota_parcial(Double nota_parcial) {
        this.nota_parcial = nota_parcial;
    }

    public Double getNota_final() {
        return nota_final;
    }

    public void setNota_final(Double nota_final) {
        this.nota_final = nota_final;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public ContentValues values() {
        ContentValues values = new ContentValues();

        if (disciplina_id != null) {
            values.put(Columns.DISCIPLINA_ID, disciplina_id);
        }

        if (semestre_id != null) {
            values.put(Columns.SEMESTRE_ID, semestre_id);
        }

        if (nota_parcial != null) {
            values.put(Columns.NOTA_PARCIAL, nota_parcial);
        }

        if (nota_final != null) {
            values.put(Columns.NOTA_FINAL, nota_final);
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

        index = c.getColumnIndex(Columns.SEMESTRE_ID);
        if (index > -1 && !c.isNull(index)) {
            setSemestre_id(c.getLong(index));
        }

        index = c.getColumnIndex(Columns.NOTA_PARCIAL);
        if (index > -1 && !c.isNull(index)) {
            setNota_parcial(c.getDouble(index));
        }

        index = c.getColumnIndex(Columns.NOTA_FINAL);
        if (index > -1 && !c.isNull(index)) {
            setNota_final(c.getDouble(index));
        }

        if (lazyLoading)
            lazyLoading(context);
    }

    protected void clear() {
        setId(null);
        setDisciplina_id(null);
        setSemestre_id(null);
        setNota_parcial(null);
        setNota_final(null);
        disciplina = null;
        semestre = null;
    }

    protected void lazyLoading(Context context) {
        if (disciplina_id != null)
            disciplina = DAOFactory.getDisciplinaDAO(context).getDisciplinaById(disciplina_id);

        if (semestre_id != null)
            semestre = DAOFactory.getSemestreDAO(context).getSemestreById(semestre_id);

    }

    public static final class Columns {
        public static final String _ID = "_id";
        public static final String DISCIPLINA_ID = "disciplina_id";
        public static final String SEMESTRE_ID = "semestre_id";
        public static final String NOTA_PARCIAL = "nota_parcial";
        public static final String NOTA_FINAL = "nota_final";
    }

    public static final class FullColumns {
        public static final String _ID = TABLE_NAME + "." + Columns._ID;
        public static final String DISCIPLINA_ID = TABLE_NAME + "." + Columns.DISCIPLINA_ID;
        public static final String SEMESTRE_ID = TABLE_NAME + "." + Columns.SEMESTRE_ID;
        public static final String NOTA_PARCIAL = TABLE_NAME + "." + Columns.NOTA_PARCIAL;
        public static final String NOTA_FINAL = TABLE_NAME + "." + Columns.NOTA_FINAL;
    }

    public static final class Aliases {
        public static final String _ID = TABLE_NAME + "_" + Columns._ID;
        public static final String DISCIPLINA_ID = TABLE_NAME + "_" + Columns.DISCIPLINA_ID;
        public static final String SEMESTRE_ID = TABLE_NAME + "_" + Columns.SEMESTRE_ID;
        public static final String NOTA_PARCIAL = TABLE_NAME + "_" + Columns.NOTA_PARCIAL;
        public static final String NOTA_FINAL = TABLE_NAME + "_" + Columns.NOTA_FINAL;
    }

}
