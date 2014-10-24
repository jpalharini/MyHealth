package android.palharini.myhealth;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.palharini.myhealth.abas.AbaSemana;
import android.palharini.myhealth.abas.AdaptadorAbasPeriodos;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TelaAcompanhamento extends FragmentActivity implements ActionBar.TabListener {
	
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_acompanhamento);

		final ActionBar actionBar = getActionBar();
	    
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	 
        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.abaSemana).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.abaMes).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.abaAno).setTabListener(this));
	}
	
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		    if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
		        getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		    }
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			if (tab.getPosition() == 0) {
			      Fragment listaIndSemana = new Fragment();
			      getSupportFragmentManager().beginTransaction().replace(R.id.container, listaIndSemana).commit();
			     } 
	
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
	
		}
	}