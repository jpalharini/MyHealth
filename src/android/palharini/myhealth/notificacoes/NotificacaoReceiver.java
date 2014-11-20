package android.palharini.myhealth.notificacoes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.palharini.myhealth.R;
import android.palharini.myhealth.TelaCadastroIndicador;

public class NotificacaoReceiver extends BroadcastReceiver {
 
	private int ID_NOTIFICACAO;
		
	private Notification.Builder builder;
	private Intent notificacaoIntent;

	private String ttNotificacao, txNotificacao;
	private NotificationManager notificationManager;
	private PendingIntent pendingNotificacaoIntent;
	
    public void onReceive(Context context, Intent intent) {
 
    	ID_NOTIFICACAO = intent.getIntExtra("ID_NOTIFICACAO", 0);
    			
		ttNotificacao = intent.getStringExtra("ttNotificacao");
		txNotificacao = intent.getStringExtra("txNotificacao");
		
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notificacaoIntent = new Intent(context, TelaCadastroIndicador.class);
	    notificacaoIntent.putExtra("tipoSelecionado", ID_NOTIFICACAO);
		pendingNotificacaoIntent = PendingIntent.getActivity(context, 0, notificacaoIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		builder = new Notification.Builder(context);
        builder.setContentTitle(ttNotificacao);
        builder.setContentText(txNotificacao);
        builder.setSmallIcon(R.drawable.ic_launcher);
    	builder.setContentIntent(pendingNotificacaoIntent);

    	notificationManager.notify(ID_NOTIFICACAO, builder.build());
        
    }
}
