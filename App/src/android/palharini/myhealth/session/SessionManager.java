package android.palharini.myhealth.session;
 
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.palharini.myhealth.activities.Login;
 
public class SessionManager {

    SharedPreferences pref;
    Editor editor;   
    Context contexto;
    
    Integer PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyHealthPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_EMAIL = "email";
     
    public SessionManager (Context context){
        this.contexto = context;
        pref = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    public void criarSessao(Integer id, String nome, String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, nome);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void checkLogin(){
    	if(!this.isLoggedIn()){
            Intent voltaTelaLogin = new Intent(contexto, Login.class);
            
            voltaTelaLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            voltaTelaLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            contexto.startActivity(voltaTelaLogin);
        }
         
    }
     
    //Busca dados do usuário
    public HashMap<String, String> getLoginUsuario(){
        HashMap<String, String> usuario = new HashMap<String, String>();

        usuario.put(KEY_NAME, pref.getString(KEY_NAME, null));
        usuario.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return usuario;
    }
    
    public Integer getUserID() {
    	Integer id = pref.getInt(KEY_ID, 0);
    	return id;
    }
     
    public void logoutUsuario(){
        editor.clear();
        editor.commit();

        Intent voltaTelaLogin = new Intent(contexto, Login.class);
        
        voltaTelaLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        voltaTelaLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        contexto.startActivity(voltaTelaLogin);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}