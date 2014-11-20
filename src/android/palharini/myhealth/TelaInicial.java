package android.palharini.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.palharini.myhealth.sessao.GerenciamentoSessao;

public class TelaInicial extends Activity {
	
	private int SPLASH_TIME_OUT;
	
	private GerenciamentoSessao sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_inicial);
		
		SPLASH_TIME_OUT = 1500;
		
		sessao = new GerenciamentoSessao(getApplicationContext());
	 
		new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {

            	if (sessao.isLoggedIn()){
        			Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
                    startActivity(irTelaPrincipal);
                    finish();
        		}
        		else {
        			Intent irTelaLogin = new Intent(getApplicationContext(), TelaLogin.class);
                    startActivity(irTelaLogin);
                    finish();
        		}
            }
        }, SPLASH_TIME_OUT);
	}
}
