package traitement_pour_lesTaches;

import android.util.Log;

public class Utils {

    public static final boolean IS_TRIAL = false;

    public static void getErrors(final Exception e) {
        final String stackTrace = "Vimal ::" + Log.getStackTraceString(e);
        Utils.sout("" + stackTrace);
    }

    public static void sout(final String msg) {
        if (IS_TRIAL) {
            System.out.println("Vimal :: " + msg);
        }
    }
}