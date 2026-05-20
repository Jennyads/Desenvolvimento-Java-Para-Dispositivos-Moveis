package br.com.jeniffer.controlechavespix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etChave;
    private Spinner spTipoChave;
    private RadioGroup rgDono;
    private CheckBox cbFavorita;
    private boolean modoEdicao = false;
    private int posicaoEdicao = -1;
    private int idEdicao = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(getString(R.string.title_form));
        }

        etNome = findViewById(R.id.etNome);
        etChave = findViewById(R.id.etChave);
        spTipoChave = findViewById(R.id.spTipoChave);
        rgDono = findViewById(R.id.rgDono);
        cbFavorita = findViewById(R.id.cbFavorita);

        configurarSpinner();
        carregarModoEdicaoSeExistir();
    }

    private void configurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.key_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoChave.setAdapter(adapter);
    }

    private void carregarModoEdicaoSeExistir() {
        Intent intent = getIntent();
        modoEdicao = intent.getBooleanExtra(ListaChavesActivity.EXTRA_MODO_EDICAO, false);

        if (modoEdicao) {
            idEdicao = intent.getIntExtra(ListaChavesActivity.EXTRA_ID, -1);
            posicaoEdicao = intent.getIntExtra(ListaChavesActivity.EXTRA_POSICAO, -1);

            String apelido = intent.getStringExtra(ListaChavesActivity.EXTRA_APELIDO);
            String chave = intent.getStringExtra(ListaChavesActivity.EXTRA_CHAVE);
            String tipoKey = intent.getStringExtra(ListaChavesActivity.EXTRA_TIPO);
            String donoKey = intent.getStringExtra(ListaChavesActivity.EXTRA_DONO);
            boolean favorita = intent.getBooleanExtra(ListaChavesActivity.EXTRA_FAVORITA, false);

            etNome.setText(apelido);
            etChave.setText(chave);
            setTipoFromKey(tipoKey);

            if ("own".equals(donoKey)) {
                rgDono.check(R.id.rbPropria);
            } else {
                rgDono.check(R.id.rbTerceiro);
            }

            cbFavorita.setChecked(favorita);
        }
    }

    private void setTipoFromKey(String key) {
        int posicao = 0;
        if ("email".equals(key)) posicao = 0;
        else if ("phone".equals(key)) posicao = 1;
        else if ("cpf".equals(key)) posicao = 2;
        else if ("random".equals(key)) posicao = 3;

        spTipoChave.setSelection(posicao);
    }

    private String getSelectedTipoKey() {
        int pos = spTipoChave.getSelectedItemPosition();
        switch (pos) {
            case 0:
                return "email";
            case 1:
                return "phone";
            case 2:
                return "cpf";
            default:
                return "random";
        }
    }

    private String getSelectedDonoKey() {
        int selecionado = rgDono.getCheckedRadioButtonId();
        if (selecionado == R.id.rbPropria) {
            return "own";
        }
        return "third";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSalvar) {
            salvarComValidacao();
            return true;
        } else if (item.getItemId() == R.id.menuLimpar) {
            limparFormulario();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limparFormulario() {
        etNome.setText("");
        etChave.setText("");
        rgDono.clearCheck();
        cbFavorita.setChecked(false);
        spTipoChave.setSelection(0);

        etNome.requestFocus();
        Toast.makeText(this, getString(R.string.toast_form_cleared), Toast.LENGTH_SHORT).show();
    }

    private void salvarComValidacao() {
        String nome = etNome.getText().toString().trim();
        String chave = etChave.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_fill_name), Toast.LENGTH_SHORT).show();
            etNome.requestFocus();
            return;
        }

        if (chave.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_fill_key), Toast.LENGTH_SHORT).show();
            etChave.requestFocus();
            return;
        }

        int selecionado = rgDono.getCheckedRadioButtonId();
        if (selecionado == -1) {
            Toast.makeText(this, getString(R.string.error_select_owner), Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoKey = getSelectedTipoKey();
        String donoKey = getSelectedDonoKey();
        boolean favorita = cbFavorita.isChecked();

        Log.d("PIX_APP", "Saving key: " + nome);

        Intent data = new Intent();
        data.putExtra(ListaChavesActivity.EXTRA_ID, idEdicao);
        data.putExtra(ListaChavesActivity.EXTRA_APELIDO, nome);
        data.putExtra(ListaChavesActivity.EXTRA_CHAVE, chave);
        data.putExtra(ListaChavesActivity.EXTRA_TIPO, tipoKey);
        data.putExtra(ListaChavesActivity.EXTRA_DONO, donoKey);
        data.putExtra(ListaChavesActivity.EXTRA_FAVORITA, favorita);
        data.putExtra(ListaChavesActivity.EXTRA_MODO_EDICAO, modoEdicao);
        data.putExtra(ListaChavesActivity.EXTRA_POSICAO, posicaoEdicao);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}