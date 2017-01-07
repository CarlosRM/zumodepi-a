package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

public class NavMenuListener implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private DrawerLayout navDrawer;

    public NavMenuListener(Context context, DrawerLayout navDrawer) {
        this.context = context;
        this.navDrawer = navDrawer;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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
            case R.id.searchButton: {
                if (!context.getClass().equals(SearchActivity.class)) {
                    Intent myIntent = new Intent(context, SearchActivity.class);
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
            }
            default: return false;
        }
        return true;
    }
}
