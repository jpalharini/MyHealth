package android.palharini.myhealth.session;
 
import java.util.HashMap;
 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class GerenciamentoSessao {

    SharedPreferences pref;
    Editor editor;   
    Context contexto;
    
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyHealthPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "nome";
    public static final String KEY_EMAIL = "email";
     
    public GerenciamentoSessao (Context context){
        this.contexto = context;
        pref = contexto.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    public void criarSessao(String nome, String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, nome);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }   

    public void checkLogin(){
    	if(!this.isLoggedIn()){
            Intent voltaTelaLogin = new Intent(contexto, TelaLogin.class);
            
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
     
    public void logoutUsuario(){
        editor.clear();
        editor.commit();

        Intent voltaTelaLogin = new Intent(contexto, TelaLogin.class);
        
        voltaTelaLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        voltaTelaLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        contexto.startActivity(voltaTelaLogin);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}