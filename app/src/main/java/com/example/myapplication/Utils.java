package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;

public class Utils {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_BLACK = 1;
    public static boolean isActive = true;

    /***
     * Définir le theme de l'activité et redemarrer l'activité avec ce theme
     * @param activity
     * @param theme
     */
    public static void changeToTheme(Activity activity, int theme){
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /***
     * Définir le theme de l'activité selon la configuration
     * @param activity
     */
    public static void onActivityCreateSetTheme(Activity activity){
        switch (sTheme)
        {
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                isActive = true;
                break;

            case THEME_BLACK:
                activity.setTheme(R.style.Black_Theme);
                isActive = false;
                break;

            default:
                break;
        }
    }
}
