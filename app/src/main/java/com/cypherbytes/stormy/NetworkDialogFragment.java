package com.cypherbytes.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by travis on 3/16/15.
 */
public class NetworkDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.network_error_title))
                .setMessage(context.getString(R.string.network_error_message))
                .setPositiveButton(context.getString(R.string.network_error_ok), null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
