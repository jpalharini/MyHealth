package android.palharini.myhealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.palharini.myhealth.R;
import android.palharini.myhealth.session.SessionManager;

public class Splash extends Activity {
	
	private int SPLASH_TIME_OUT;
	
	private SessionManager sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		SPLASH_TIME_OUT = 1500;
		
		sessao = new SessionManager(getApplicationContext());
	 
		new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {

            	if (sessao.isLoggedIn()){
        			Intent irTelaPrincipal = new Intent(getApplicationContext(), Main.class);
                    startActivity(irTelaPrincipal);
                    finish();
        		}
        		else {
        			Intent irTelaLogin = new Intent(getApplicationContext(), Login.class);
                    startActivity(irTelaLogin);
                    finish();
        		}
            }
        }, SPLASH_TIME_OUT);
	}
}
