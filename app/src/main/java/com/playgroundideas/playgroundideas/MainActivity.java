package com.playgroundideas.playgroundideas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom navigation view
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    /**
                     * This method defines the behavior when a different bottom navigation item is selected
                     */
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        boolean success = false;
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_designs:
                                fragment = new DesignsFragment();
                                success = true;
                                break;
                            case R.id.navigation_plans:
                                fragment = new PlansFragment();
                                success = true;
                                break;
                            case R.id.navigation_manuals:
                                fragment = new ManualsFragment();
                                success = true;
                                break;
                            case R.id.navigation_projects:
                                fragment = new ProjectsFragment();
                                success = true;
                                break;
                        }
                        if (success) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, fragment).commit();
                            return true;
                        } else {
                            return false;
                        }
                    }
                }

        );

        //initialise first fragment
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, we only need to do something
            // if we're not being restored from a previous state, or else
            // we could end up with overlapping fragments.
            if (savedInstanceState == null) {
                // Create a new Fragment to be placed in the activity layout
                DesignsFragment designsFragment = new DesignsFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                designsFragment.setArguments(getIntent().getExtras());

                // Add the fragments to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, designsFragment).commit();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI
                // TODO
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
