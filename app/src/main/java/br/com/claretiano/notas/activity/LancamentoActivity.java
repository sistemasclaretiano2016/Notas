package br.com.claretiano.notas.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.claretiano.notas.R;
import br.com.claretiano.notas.dal.DAOFactory;
import br.com.claretiano.notas.dal.DisciplinaDAO;
import br.com.claretiano.notas.dal.NotaDAO;
import br.com.claretiano.notas.dal.SemestreDAO;
import br.com.claretiano.notas.model.Disciplina;
import br.com.claretiano.notas.model.Nota;
import br.com.claretiano.notas.model.Semestre;

public class LancamentoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    private Button btnSalvar;
    private EditText edtNotaParcial;
    private EditText edtNotaFinal;
    private TextView txtStatus;

    private Spinner spnDisciplina;
    private Spinner spnSemestre;
    private ArrayAdapter<Disciplina> adapterSpnDisciplina;
    private ArrayAdapter<Semestre> adapterSpnSemestre;

    private NotaDAO notaDAO;
    private DisciplinaDAO disciplinaDAO;
    private SemestreDAO semestreDAO;

    private List<Disciplina> disciplinaList;
    private List<Semestre> semestreList;
    private long IdNota = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento);

        edtNotaParcial = (EditText) findViewById(R.id.edt_nota_parcial);
        edtNotaFinal = (EditText) findViewById(R.id.edt_nota_final);

        txtStatus = (TextView) findViewById(R.id.txt_status);

        btnSalvar = (Button) findViewById(R.id.btn_salvar);

        spnSemestre = (Spinner) findViewById(R.id.spn_semestre);
        spnDisciplina = (Spinner) findViewById(R.id.spn_disciplina);

        notaDAO = DAOFactory.getNotaDAO(this);
        disciplinaDAO = DAOFactory.getDisciplinaDAO(this);
        semestreDAO = DAOFactory.getSemestreDAO(this);

        disciplinaList = disciplinaDAO.getDisciplinasList();

        if (btnSalvar != null)
            btnSalvar.setOnClickListener(this);

        if (spnSemestre != null)
            spnSemestre.setOnItemSelectedListener(this);

        if (spnDisciplina != null) {
            spnDisciplina.setOnItemSelectedListener(this);

            adapterSpnDisciplina = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, disciplinaList);
            adapterSpnDisciplina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDisciplina.setAdapter(adapterSpnDisciplina);
        }
    }

    void CarregarDadosJaSalvos() {
        IdNota = 0;
        txtStatus.setText("Inserindo...");
        edtNotaParcial.setText("");
        edtNotaFinal.setText("");

        try {
            Disciplina disciplina = (Disciplina) spnDisciplina.getSelectedItem();
            if (disciplina != null) {
                Semestre semestre = (Semestre) spnSemestre.getSelectedItem();
                if (semestre != null) {
                    Nota nota = notaDAO.getNotaPorDisciplinaESemestre(disciplina.getId(), semestre.getId());
                    if (nota != null && nota.getId() != null && nota.getId() > 0) {
                        IdNota = nota.getId();
                        edtNotaParcial.setText(String.valueOf(nota.getNota_parcial()));
                        edtNotaFinal.setText(String.valueOf(nota.getNota_final()));
                        txtStatus.setText("Atualizando...");
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spnDisciplina.getId()) {
            if (spnSemestre != null) {
                semestreList = semestreDAO.getSemestresPorDisciplina_idList(disciplinaList.get(position).getId());

                adapterSpnSemestre = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semestreList);
                adapterSpnSemestre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnSemestre.setAdapter(adapterSpnSemestre);
            }
        } else if (parent.getId() == spnSemestre.getId()) {
            CarregarDadosJaSalvos();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSalvar.getId()) {
            try {
                double notaParcial = 0D;
                try {
                    notaParcial = Double.parseDouble(edtNotaParcial.getText().toString());
                } catch (Exception ignored) {
                    toast("Informar a nota parcial.");
                    return;
                }

                double notaFinal = 0D;
                try {
                    notaFinal = Double.parseDouble(edtNotaFinal.getText().toString());
                } catch (Exception ignored) {
                    toast("Informar a nota final.");
                    return;
                }

                Disciplina disciplina = (Disciplina) spnDisciplina.getSelectedItem();
                if (disciplina != null) {
                    Semestre semestre = (Semestre) spnSemestre.getSelectedItem();
                    if (semestre != null) {
                        Nota nota = new Nota();
                        if (IdNota > 0)
                            nota.setId(IdNota);
                        nota.setDisciplina_id(disciplina.getId());
                        nota.setSemestre_id(semestre.getId());
                        nota.setNota_parcial(notaParcial);
                        nota.setNota_final(notaFinal);

                        if (notaDAO.salvar(nota)) {
                            toast("Salvo com sucesso.");
                            CarregarDadosJaSalvos();
                        } else
                            toast("Não conseguiu salvar a nota.");
                    } else
                        toast("Selecionar o semestre.");
                } else
                    toast("Selecionar a disciplina.");
            } catch (Exception e) {
                toast("Não foi possível salvar os dados.\n\n" + e.getMessage());
            }
        }
    }
}
