package com.auh.opencomune;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Principale extends FragmentActivity {

	public int numeronotizie = 0;
	public int numeromoduli = 0;
	public ArrayList <ArrayList <String>> moduli;
	public ArrayList <String> notizie;
	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private int currentColor = 0xFFF4842D;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principale);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
		changeColor(currentColor);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principale, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		}

		return super.onOptionsItemSelected(item);
	}
	
	public ArrayList <ArrayList <String>> ricevimoduli()
	{
		ArrayList <ArrayList <String>> risultato = new ArrayList<ArrayList <String>>();
		Connection conn = null;
		Statement mess = null;
		ResultSet ris = null;
		
		String url = "jdbc:mysql://db4free.net:3306/";
        String nomedb = "auhprojectdb";
        String driver = "com.mysql.jdbc.Driver";
        String username = "auhcoders";
        String password = "auh2013";
        String query = "SELECT `Tipo modulo`,Link FROM Servizi;";       
        
        try {
          Class.forName(driver).newInstance();
          conn = DriverManager.getConnection(url+nomedb,username,password);
          mess = conn.createStatement();
          ris = mess.executeQuery(query);
          
          int i=0;
          while (ris.next()) 
          {
        	  ArrayList <String> riga = new ArrayList<String>();
        	  riga.add(ris.getString(1));
        	  riga.add(ris.getString(2));
        	  risultato.add(riga);
        	  i++;
          }
          numeromoduli=i;
          ris.close();
          mess.close();
          conn.close();
          
          Toast.makeText(this, "Connessione riuscita, "+numeromoduli+" elementi", Toast.LENGTH_SHORT).show();
          
        } catch (Exception e) {
        	e.printStackTrace();
        	Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return risultato;
	}
	
	public ArrayList <String> ricevinotizie()
	{
		
		ArrayList <String> risultato = new ArrayList<String>();
		Connection conn = null;
		Statement mess = null;
		ResultSet ris = null;
		
		String url = "jdbc:mysql://db4free.net:3306/";
        String nomedb = "auhprojectdb";
        String driver = "com.mysql.jdbc.Driver";
        String username = "auhcoders";
        String password = "auh2013";
        String query = "SELECT notizie FROM Notizie;";       
        
        try {
          Class.forName(driver).newInstance();
          conn = DriverManager.getConnection(url+nomedb,username,password);
          mess = conn.createStatement();
          ris = mess.executeQuery(query);
          
          int i =0;
          while (ris.next()) 
          {
        	  risultato.add(ris.getString(1));
        	  i++;
          }
          
          numeronotizie=i;
          ris.close();
          mess.close();
          conn.close();
          
          Toast.makeText(this, "Connessione riuscita, "+numeronotizie+" elementi", Toast.LENGTH_SHORT).show();
          
        } catch (Exception e) {
        	e.printStackTrace();
        	Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return risultato;
	}
	
	public int inviadati(String dest,String nome, String cognome, String testo, String ora, String data, String luogo)
	{
		Connection conn = null;
		Statement mess = null;
		int aggiorna = 0;
		
		String url = "jdbc:mysql://db4free.net:3306/";
        String nomedb = "auhprojectdb";
        String driver = "com.mysql.jdbc.Driver";
        String username = "auhcoders";
        String password = "auh2013";
        String query = "INSERT INTO Segnalazioni (nome,cognome,destinazione,segnalazione,data,ora,luogo) VALUES ('"+nome+"','"+cognome+"','"+dest+"','"+testo+"','"+data+"','"+ora+"','"+luogo+"');";       
        
        try {
          Class.forName(driver).newInstance();
          conn = DriverManager.getConnection(url+nomedb,username,password);
          Toast.makeText(this, "Connessione riuscita", Toast.LENGTH_SHORT).show();
          mess=conn.createStatement();
          aggiorna = mess.executeUpdate(query);
          if (aggiorna != 0) {
        	  Toast.makeText(this, "Invio effettuato", Toast.LENGTH_SHORT).show();
        	  mess.close();
        	  conn.close();
        	  }
        } catch (Exception e) {
        	e.printStackTrace();
        	Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
          return 1;
        }
      
		return 0;
	}
	
	public void onClick (View v){
		switch (v.getId())
		{
		case R.id.segnalainvia:
		{
			EditText casellanome = (EditText) findViewById(R.id.txtnome);
			EditText casellacognome = (EditText) findViewById(R.id.txtcognome);
			EditText casellatesto = (EditText) findViewById(R.id.txttesto);
			EditText caselladata = (EditText) findViewById(R.id.txtdata);
			EditText casellaora = (EditText) findViewById(R.id.txtora);
			EditText casellaluogo = (EditText) findViewById(R.id.txtluogo);
			if (casellanome.getText().toString().matches("") || casellacognome.getText().toString().matches("") || casellatesto.getText().toString().matches("") || caselladata.getText().toString().matches("") || casellaora.getText().toString().matches("") || casellaluogo.getText().toString().matches("")) 
			{
				Toast.makeText(this, "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
				
			}
			else
			{
				Spinner scelta = (Spinner) findViewById(R.id.sceltasegnala);
				inviadati(scelta.getSelectedItem().toString(),casellanome.getText().toString(),casellacognome.getText().toString(),casellatesto.getText().toString(),caselladata.getText().toString(),casellaora.getText().toString(),casellaluogo.getText().toString());
				casellanome.setText("");
				casellacognome.setText("");
				casellatesto.setText("");
				caselladata.setText("");
				casellaora.setText("");
				casellaluogo.setText("");
			}
			break;
		}
		case R.id.notifica:
		{	
			notizie = ricevinotizie();
			LinearLayout listanotizie = (LinearLayout) findViewById(R.id.notiziecont);
			listanotizie.removeAllViewsInLayout();
			if (numeronotizie!=0)
			{
				for (int i=0; i<numeronotizie;i++)
				{
					TextView casella = (TextView) getLayoutInflater().inflate(R.layout.notizia, null);
					casella.setText(notizie.get(i));
					listanotizie.addView(casella);
					View divider = (View) getLayoutInflater().inflate(R.layout.divisore, null);
					listanotizie.addView(divider);
				}
			}
			break;
		}
		case R.id.richiedimoduli:
		{
			moduli = ricevimoduli();
			LinearLayout listamoduli = (LinearLayout) findViewById(R.id.listamoduli);
			listamoduli.removeAllViewsInLayout();
			if (numeromoduli!=0)
			{
				for (int i=0; i<numeromoduli;i++)
				{
					ArrayList <String> riga = new ArrayList<String>();
					riga = moduli.get(i);
					TextView casella = (TextView) getLayoutInflater().inflate(R.layout.modulo,null);
					casella.setText(riga.get(0));
					casella.setTag(i);
					listamoduli.addView(casella);
					View divider = (View) getLayoutInflater().inflate(R.layout.divisore, null);
					listamoduli.addView(divider);
					
				}
			}
			break;
		}
		}
	}
	
	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);
/*
		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}
*/
		currentColor = newColor;

	}

	public void onmoduloclicked(View v) {

		int i = Integer.parseInt(v.getTag().toString());
		ArrayList <String> riga = new ArrayList<String>();
		riga = moduli.get(i);
		String url = riga.get(1);
		Intent navigaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(navigaIntent);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}
    
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Notizie", "Segnala", "Modulistica", "Info" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return CardFragment.newInstance(position);
		}

	}
    
		
}
