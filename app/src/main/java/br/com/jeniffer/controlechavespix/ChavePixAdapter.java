package br.com.jeniffer.controlechavespix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChavePixAdapter extends ArrayAdapter<ChavePix> {

    public ChavePixAdapter(Context context, List<ChavePix> itens) {
        super(context, 0, itens);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_chave_pix, parent, false);
        }

        ChavePix item = getItem(position);

        TextView tvApelido = view.findViewById(R.id.tvApelido);
        TextView tvTipoDono = view.findViewById(R.id.tvTipoDono);
        TextView tvChave = view.findViewById(R.id.tvChave);
        TextView tvFavorita = view.findViewById(R.id.tvFavorita);

        if (item != null) {
            tvApelido.setText(item.getApelido());
            tvTipoDono.setText(getTipoLabel(item.getTipoKey()) + " • " + getDonoLabel(item.getDonoKey()));
            tvChave.setText(item.getChave());
            tvFavorita.setText(item.isFavorita()
                    ? getContext().getString(R.string.favorite_yes)
                    : getContext().getString(R.string.favorite_no));
        }

        return view;
    }

    private String getTipoLabel(String tipoKey) {
        switch (tipoKey) {
            case "email":
                return getContext().getString(R.string.type_email);
            case "phone":
                return getContext().getString(R.string.type_phone);
            case "cpf":
                return getContext().getString(R.string.type_cpf);
            default:
                return getContext().getString(R.string.type_random);
        }
    }

    private String getDonoLabel(String donoKey) {
        if ("own".equals(donoKey)) {
            return getContext().getString(R.string.owner_own);
        }
        return getContext().getString(R.string.owner_third);
    }
}