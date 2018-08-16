package com.example.user.finalclient;
import android.widget.EditText;
/**
 * Created by user on 2018/4/15.
 */

public class Utilities {
    public static int convertToInteger(EditText txt)
    {
        try
        {
            int x=Integer.parseInt("" + txt.getText());
            return x;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return 0;
        }
    }
}
