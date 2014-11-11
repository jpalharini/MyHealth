package android.palharini.myhealth.notificacoes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.palharini.myhealth.R;
import android.palharini.myhealth.TelaCadastroIndicador;

public class ServicoNotificacao extends Service {

	public static int ID_NOTIFICACAO;
	
	private Context context;
	
	private Notification.Builder builder;
	private Intent notificacaoIntent;

	private String ttNotificacao, txNotificacao;
	private NotificationManager notificationManager;
	private PendingIntent pendingNotificacaoIntent;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		ID_NOTIFICACAO = (int) intent.getExtras().get("ID_NOTIFICACAO");
		ttNotificacao = (String) intent.getExtras().get("ttNotificacao");
		txNotificacao = (String) intent.getExtras().get("txNotificacao");
		
		context = this.getApplicationContext();
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notificacaoIntent = new Intent(this, TelaCadastroIndicador.class);
		pendingNotificacaoIntent = PendingIntent.getActivity(context, 0, notificacaoIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		builder = new Notification.Builder(context);
        builder.setContentTitle(ttNotificacao);
        builder.setContentText(txNotificacao);
        builder.setSmallIcon(R.drawable.ic_launcher);
    	builder.setContentIntent(pendingNotificacaoIntent);

		notificationManager = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
		notificationManager.notify(ID_NOTIFICACAO, builder.build());
		
		return super.onStartCommand(intent, flags, startId);
	}

}
