package android.palharini.myhealth;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.palharini.myhealth.adaptadores.AdaptadorAbas;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TelaPrincipal extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private AdaptadorAbas tabsAdapter;
	private ActionBar actionBar;
	// Títulos das abas
	private String[] abas = {"7 dias", "30 dias", "1 ano"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		// Inicialização do modo de abas
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		tabsAdapter = new AdaptadorAbas(getSupportFragmentManager());
		viewPager.setAdapter(tabsAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        

		// Adicionando abas
		for (String nomes_abas : abas) {
			actionBar.addTab(actionBar.newTab().setText(nomes_abas)
					.setTabListener(this));
		}
	}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}
	}