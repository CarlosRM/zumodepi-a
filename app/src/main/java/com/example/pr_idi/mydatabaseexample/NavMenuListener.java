package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class NavMenuListener implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private DrawerLayout navDrawer;

    public static final int homneButton = 0;
    public static final int advancedViewButton = 1;
    public static final int insertFilmButton = 2;

    public NavMenuListener(Context context, DrawerLayout navDrawer) {
        this.context = context;
        this.navDrawer = navDrawer;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_button: {
                if (!context.getClass().equals(MainActivity.class)) {
                    Intent myIntent = new Intent(context, MainActivity.class);
                    context.startActivity(myIntent);
                }
                navDrawer.closeDrawers();
                break;
            }
            case R.id.switchActivity: {
                if (!context.getClass().equals(RecyclerViewActivity.class)) {
                    Intent myIntent = new Intent(context, RecyclerViewActivity.class);
                    context.startActivity(myIntent);
                }
                navDrawer.closeDrawers();
                break;
            }
            case R.id.addFilmButton: {
                if (!context.getClass().equals(InsertFilmActivity.class)) {
                    Intent myIntent = new Intent(context, InsertFilmActivity.class);
                    context.startActivity(myIntent);
                }
                navDrawer.closeDrawers();
                break;
            }
            case R.id.help_button: {
                if (!context.getClass().equals(HelpActivity.class)) {
                    Intent myIntent = new Intent(context, HelpActivity.class);
                    context.startActivity(myIntent);
                }
                navDrawer.closeDrawers();
                break;
            }
            case R.id.about_button: {
                if (!context.getClass().equals(AboutActivity.class)) {
                    Intent myIntent = new Intent(context, AboutActivity.class);
                    context.startActivity(myIntent);
                }
                navDrawer.closeDrawers();
                break;
            }
            default: return false;
        }
        return true;
    }
}
