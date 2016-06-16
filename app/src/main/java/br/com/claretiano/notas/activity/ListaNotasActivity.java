package br.com.claretiano.notas.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import br.com.claretiano.notas.R;
import br.com.claretiano.notas.adapter.AdapterListView;
import br.com.claretiano.notas.dal.DAOFactory;
import br.com.claretiano.notas.dal.DisciplinaDAO;
import br.com.claretiano.notas.dal.NotaDAO;
import br.com.claretiano.notas.model.Disciplina;
import br.com.claretiano.notas.model.Nota;

public class ListaNotasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView lstView;
    private List<Nota> lista;

    private NotaDAO notaDAO;
    private DisciplinaDAO disciplinaDAO;

    private Spinner spnDisciplina;
    private ArrayAdapter<Disciplina> adapterSpnDisciplina;

    private List<Disciplina> disciplinaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        lstView = (ListView) findViewById(R.id.lista);

        spnDisciplina = (Spinner) findViewById(R.id.spn_disciplina);

        notaDAO = DAOFactory.getNotaDAO(this);
        disciplinaDAO = DAOFactory.getDisciplinaDAO(this);

        if (spnDisciplina != null) {
            spnDisciplina.setOnItemSelectedListener(this);

            disciplinaList = disciplinaDAO.getDisciplinasListComDefault();

            adapterSpnDisciplina = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, disciplinaList);
            adapterSpnDisciplina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDisciplina.setAdapter(adapterSpnDisciplina);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spnDisciplina.getId()) {
            if (disciplinaList.get(position).getId() > 0)
                lista = notaDAO.getNotasPorDisciplina_idList(disciplinaList.get(position).getId(), true);
            else
                lista = notaDAO.getNotasList(true);

            AdapterListView adpt = new AdapterListView(this, lista);

            lstView.setAdapter(adpt);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
