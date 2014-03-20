package com.trm.trmclient.Utils;

/**
 * Created by Hieu on 3/16/14.
 */
import com.trm.trmclient.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogBox {
	/**
	 * Show dialog box with one button
	 * @param context : context (Activity)
	 * @param title : Title of dialog
	 * @param message : content message
	 * @param textButton : Caption of button
	 */
    public static void showMessage(Context context, String title, String message, String textButton)
    {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.alert_dialog);
        dialog.setTitle(title);
        dialog.setCanceledOnTouchOutside(false);

        final TextView txtContent=(TextView)dialog.findViewById(R.id.txtContent);
        txtContent.setText(" " + message);
        Button btn=(Button)dialog.findViewById(R.id.btn);
        btn.setText(textButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
