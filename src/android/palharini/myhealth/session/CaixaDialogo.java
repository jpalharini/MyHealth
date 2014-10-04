package android.palharini.myhealth.session;

import android.palharini.myhealth.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
  
public class CaixaDialogo {

    @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String titulo, String mensagem,
            Boolean status) {
        AlertDialog caixaDialogo = new AlertDialog.Builder(context).create();

        caixaDialogo.setTitle(titulo);
        caixaDialogo.setMessage(mensagem);
  
        if(status != null)
        	caixaDialogo.setIcon((status) ? R.drawable.ok : R.drawable.fail);

        caixaDialogo.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
  
        // Showing Alert Message
        caixaDialogo.show();
    }
}