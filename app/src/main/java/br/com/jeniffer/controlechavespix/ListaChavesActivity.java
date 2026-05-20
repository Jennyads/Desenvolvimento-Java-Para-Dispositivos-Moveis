package br.com.jeniffer.controlechavespix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListaChavesActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_APELIDO = "EXTRA_APELIDO";
    public static final String EXTRA_CHAVE = "EXTRA_CHAVE";
    public static final String EXTRA_TIPO = "EXTRA_TIPO";
    public static final String EXTRA_DONO = "EXTRA_DONO";
    public static final String EXTRA_FAVORITA = "EXTRA_FAVORITA";
    public static final String EXTRA_POSICAO = "EXTRA_POSICAO";
    public static final String EXTRA_MODO_EDICAO = "EXTRA_MODO_EDICAO";

    private static final String PREFS_NAME = "pix_prefs";
    private static final String PREF_SORT_ORDER = "sort_order";
    private static final String SORT_ASC = "asc";
    private static final String SORT_DESC = "desc";

    private AppDatabase db;
    private ChavePixDao chavePixDao;

    private ArrayList<ChavePix> lista;
    private ChavePixAdapter adapter;
    private int posicaoSelecionada = -1;

    private final ActivityResultLauncher<Intent> cadastroLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    int id = data.getIntExtra(EXTRA_ID, -1);
                    String apelido = data.getStringExtra(EXTRA_APELIDO);
                    String chave = data.getStringExtra(EXTRA_CHAVE);
                    String tipo = data.getStringExtra(EXTRA_TIPO);
                    String dono = data.getStringExtra(EXTRA_DONO);
                    boolean favorita = data.getBooleanExtra(EXTRA_FAVORITA, false);
                    int posicao = data.getIntExtra(EXTRA_POSICAO, -1);
                    boolean modoEdicao = data.getBooleanExtra(EXTRA_MODO_EDICAO, false);

                    if (modoEdicao && posicao >= 0 && posicao < lista.size()) {
                        ChavePix item = lista.get(posicao);
                        item.setId(id);
                        item.setApelido(apelido);
                        item.setChave(chave);
                        item.setTipoKey(tipo);
                        item.setDonoKey(dono);
                        item.setFavorita(favorita);

                        chavePixDao.update(item);

                        Toast.makeText(
                                this,
                                getString(R.string.toast_item_updated, apelido),
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        ChavePix novo = new ChavePix(apelido, chave, tipo, dono, favorita);
                        chavePixDao.insert(novo);

                        Toast.makeText(
                                this,
                                getString(R.string.toast_item_added, apelido),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    recarregarLista();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chaves);

        db = AppDatabaseProvider.getInstance(this);
        chavePixDao = db.chavePixDao();

        ListView lvChaves = findViewById(R.id.lvChaves);

        lista = new ArrayList<>(chavePixDao.getAll());
        adapter = new ChavePixAdapter(this, lista);
        lvChaves.setAdapter(adapter);

        registerForContextMenu(lvChaves);

        lvChaves.setOnItemClickListener((parent, view, position, id) -> {
            ChavePix clicado = lista.get(position);
            Toast.makeText(
                    this,
                    getString(R.string.toast_item_clicked, clicado.getApelido()),
                    Toast.LENGTH_SHORT
            ).show();
        });

        aplicarOrdenacao();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAdicionar) {
            Intent intent = new Intent(this, MainActivity.class);
            cadastroLauncher.launch(intent);
            return true;

        } else if (item.getItemId() == R.id.menuSobre) {
            startActivity(new Intent(this, SobreActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menuSortAz) {
            salvarOrdenacao(SORT_ASC);
            recarregarLista();
            Toast.makeText(this, getString(R.string.toast_sort_az), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menuSortZa) {
            salvarOrdenacao(SORT_DESC);
            recarregarLista();
            Toast.makeText(this, getString(R.string.toast_sort_za), Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menuRestoreDefault) {
            salvarOrdenacao(SORT_ASC);
            recarregarLista();
            Toast.makeText(this, getString(R.string.toast_sort_restored), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto_item, menu);
        menu.setHeaderTitle(getString(R.string.context_title));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        posicaoSelecionada = info.position;
        ChavePix selecionado = lista.get(posicaoSelecionada);

        if (item.getItemId() == R.id.menuEditar) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_ID, selecionado.getId());
            intent.putExtra(EXTRA_APELIDO, selecionado.getApelido());
            intent.putExtra(EXTRA_CHAVE, selecionado.getChave());
            intent.putExtra(EXTRA_TIPO, selecionado.getTipoKey());
            intent.putExtra(EXTRA_DONO, selecionado.getDonoKey());
            intent.putExtra(EXTRA_FAVORITA, selecionado.isFavorita());
            intent.putExtra(EXTRA_POSICAO, posicaoSelecionada);
            intent.putExtra(EXTRA_MODO_EDICAO, true);

            cadastroLauncher.launch(intent);
            return true;

        } else if (item.getItemId() == R.id.menuExcluir) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_delete_title))
                    .setMessage(getString(R.string.dialog_delete_message))
                    .setPositiveButton(getString(R.string.dialog_yes), (dialog, which) -> {
                        chavePixDao.delete(selecionado);
                        recarregarLista();

                        Toast.makeText(
                                this,
                                getString(R.string.toast_item_deleted),
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .setNegativeButton(getString(R.string.dialog_no), null)
                    .show();

            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void salvarOrdenacao(String ordem) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(PREF_SORT_ORDER, ordem).apply();
    }

    private String obterOrdenacao() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(PREF_SORT_ORDER, SORT_ASC);
    }

    private void aplicarOrdenacao() {
        String ordem = obterOrdenacao();

        Collections.sort(lista, new Comparator<ChavePix>() {
            @Override
            public int compare(ChavePix a, ChavePix b) {
                return a.getApelido().compareToIgnoreCase(b.getApelido());
            }
        });

        if (SORT_DESC.equals(ordem)) {
            Collections.reverse(lista);
        }
    }

    private void recarregarLista() {
        lista.clear();
        lista.addAll(chavePixDao.getAll());
        aplicarOrdenacao();
        adapter.notifyDataSetChanged();
    }
}