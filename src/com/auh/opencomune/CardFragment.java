/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.auh.opencomune;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CardFragment extends Fragment {

	private static final String ARG_POSITION = "position";

	private int position;

	public static CardFragment newInstance(int position) {
		CardFragment f = new CardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.setMargins(0,0,0,0);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());

		ScrollView scorrimento = new ScrollView(getActivity());
		params.setMargins(0, 0, 0, 0);
		scorrimento.setLayoutParams(params);
		scorrimento.setBackgroundResource(android.R.drawable.screen_background_light);
		
		LinearLayout layoutvert = new LinearLayout(getActivity());
		params.setMargins(0,0,0,0);
		layoutvert.setPadding(margin, margin, margin, margin);
		layoutvert.setLayoutParams(params);
			
		switch (position){
		case 0: //notizie
		{
			Button cliccami = new Button(getActivity());
			cliccami.setText("Cliccami");
			layoutvert.addView(cliccami);
			break;
		}
		case 1: //segnala
		{
			final TextView testo = new TextView(getActivity());
			testo.setText("Non hai cliccato");
			LayoutParams parametri = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			parametri.setMargins(10,10,10,10);
			testo.setLayoutParams(parametri);
			layoutvert.addView(testo);
			Button pulsante = new Button(getActivity());
			pulsante.setText("Invia");
			pulsante.setOnClickListener(new OnClickListener()
		    {
		      public void onClick(View v)
		      {
		    	  if (testo.getText()=="Non hai cliccato") 
		    	  {
		    		  testo.setText("Hai cliccato");
		    	  }
		    	  else
		    	  {
		    		 testo.setText("Non hai cliccato"); 
		    	  };
		      }
		    });
			layoutvert.addView(pulsante);
			break;
		}
		};
		
		scorrimento.addView(layoutvert);
		fl.addView(scorrimento);
		return fl;
	}
	
	

}