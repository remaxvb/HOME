package com.trm.trmclient.Widget;

import android.content.Context;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class ActiveTourCard extends Card {
	
	private CardHeader cardheader;

	public ActiveTourCard(Context context) {
		super(context);
		cardheader=new CardHeader(context);
	}
	
}
