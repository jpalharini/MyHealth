package android.palharini.myhealth.notificacoes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PublicacaoNotificacao extends BroadcastReceiver {
 
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ServicoNotificacao.class);
        context.startService(service);
 
    }
}
