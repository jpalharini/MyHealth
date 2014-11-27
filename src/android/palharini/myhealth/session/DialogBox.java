package android.palharini.myhealth.session;

import android.palharini.myhealth.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
  
public class DialogBox {

	public void showAlertDialog(Context context, String titulo, String mensagem,
            Boolean status) {
        AlertDialog.Builder caixaDialogo = new AlertDialog.Builder(context);

        caixaDialogo.setTitle(titulo);
        caixaDialogo.setMessage(mensagem);
  
        if(status != null)
        	caixaDialogo.setIcon((status) ? R.drawable.ok : R.drawable.fail);

        caixaDialogo.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
  
        caixaDialogo.show();
    }
}