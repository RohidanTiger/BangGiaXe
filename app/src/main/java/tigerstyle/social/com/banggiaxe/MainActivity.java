package tigerstyle.social.com.banggiaxe;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.config.Contants;
import tigerstyle.social.com.banggiaxe.customize.DialogLoading;
import tigerstyle.social.com.banggiaxe.listener.SearchingListener;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.service.FragmentStackManager;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;
import tigerstyle.social.com.banggiaxe.utils.DialogUtil;
import tigerstyle.social.com.banggiaxe.utils.Logger;
import tigerstyle.social.com.banggiaxe.view.adapters.DrawerMenuAdapter;
import tigerstyle.social.com.banggiaxe.view.fragments.ExamMenuFragment;
import tigerstyle.social.com.banggiaxe.view.fragments.HomeMotoFragment;
import tigerstyle.social.com.banggiaxe.view.fragments.HomeOtoFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public SCApplication mApplication;
    public FragmentStackManager fragmentStackManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView listMenu;
    private DrawerMenuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Number Fragment In Stack
    private int currentStackSize = 0;
    public BaseFragment currentFragment;

    // Screen Width, Height
    public static float screenHeight = 0;
    public static float screenWidth = 0;
    protected DialogLoading mLoadingDialog;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private Toolbar toolbar;
    private ArrayList<CarBrand> listCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listMenu = (RecyclerView) findViewById(R.id.navList);
        setupDrawer();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initGlobalVariable();

        initFragmentStackManager();

        enableNetworkOnMainThread();

        getScreenDimension();

        pushFragments(new HomeMotoFragment(), false, true);
    }

    private void enableNetworkOnMainThread() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    // Get screen width , height
    private void getScreenDimension() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
    private void setupDrawer() {
        mAdapter = new DrawerMenuAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        listMenu.setLayoutManager(mLayoutManager);
        listMenu.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DrawerMenuAdapter.OnItemClickListener() {
            @Override
            public void onClick(int index) {
                switch (index){
                    case 0:{
                        clearAllPreviousFragment();
                        pushFragments(new HomeMotoFragment(), false, true);
                        break;
                    }case 1:{
                        clearAllPreviousFragment();
                        pushFragments(new HomeOtoFragment(), false, true);
                        break;
                    }case 2:{
                        clearAllPreviousFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(ExamMenuFragment.AGR_KEY,ExamMenuFragment.ARG_EXAM_A1_TYPE);
                        pushFragments(new ExamMenuFragment(), bundle, false, true);
                        break;
                    }case 3:{
                        clearAllPreviousFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(ExamMenuFragment.AGR_KEY,ExamMenuFragment.ARG_EXAM_B2_TYPE);
                        pushFragments(new ExamMenuFragment(), bundle, false, true);
                        break;
                    }
                }
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(mDrawerToggle);

    }

    public void initGlobalVariable() {
        mApplication = (SCApplication) getApplication();
        mLoadingDialog = new DialogLoading(MainActivity.this);
        mLoadingDialog.setCancelable(false);
    }

    private void initFragmentStackManager() {
        fragmentStackManager = FragmentStackManager.forContainer(this,
                R.id.container, new FragmentStackManager.Callback() {
                    @Override
                    public void onStackChanged(int stackSize,
                                               Fragment topFragment) {
                        currentFragment = (BaseFragment) topFragment;
                        currentStackSize = stackSize;
                        Log.d("FragmentManager",
                                "===FRAGMENT STACK MANAGER: STACK CHANGED : New Size: "
                                        + stackSize
                                        + " . Current Fragment : "
                                        + topFragment.getClass()
                                        .getSimpleName());
                    }
                });

        fragmentStackManager.setDefaultAnimation(R.anim.slide_in_right,
                R.anim.slide_out_left, R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    // Default tab is TAB_HOME

    public void pushFragments(Fragment fragment, Bundle bundle,
                              boolean shouldAdd) {
        pushFragments(Contants.TAB_HOME, fragment, bundle, true, shouldAdd);
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate,
                              boolean shouldAdd) {
        pushFragments(Contants.TAB_HOME, fragment, null, shouldAnimate,
                shouldAdd);
    }

    public void pushFragments(Fragment fragment, Bundle bundle,
                              boolean shouldAnimate, boolean shouldAdd) {
        pushFragments(Contants.TAB_HOME, fragment, bundle, shouldAnimate,
                shouldAdd);
    }

    public void pushFragments(String tag, Fragment fragment, Bundle bundle,
                              boolean shouldAnimate, boolean shouldAdd) {
        fragmentStackManager.push(fragment,
                fragment.getClass().getSimpleName(), bundle, shouldAnimate,
                shouldAdd);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
    }

    public void popFragments() {
        popFragments(true);
    }

    public void popFragments(boolean isSlideBack) {
        fragmentStackManager.pop(isSlideBack);
    }
    //
    // Clear all current fragment
    public void clearAllPreviousFragment() {
        fragmentStackManager.clearAllScreen();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (currentStackSize == 1) {
            DialogUtil.showYesNoDialog(this, R.string.msg_confirm_quit_app,
                    R.string.cmn_yes, R.string.cmn_no, onOKClick);
            return;
        }
        if (currentStackSize > 1 && !currentFragment.canPressBack()) {
            if (fragmentStackManager.pop(true))
                return;
        } else{
            finish();
        }
    }

    DialogInterface.OnClickListener onOKClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };

    public void showLoading() {
        try {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mLoadingDialog.show();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoading(int strResId) {
        showLoading();
    }


    public void hideLoading() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mLoadingDialog != null) {
                        mLoadingDialog.hide();
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });
    }


    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                    if(fragment instanceof SearchingListener){
                        ((SearchingListener)fragment).onQueryTextChange(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    hideKeyboard();
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerToggle.setDrawerIndicatorEnabled(false);
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    return false;
                }
            });
        }

        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_search:{
                // Not implemented here
                return false;
            }
            case android.R.id.home:{
                if (currentStackSize > 1 && !currentFragment.canPressBack()) {
                   popFragments();
                }else{
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                    if(fragment instanceof SearchingListener){
                        ((SearchingListener)fragment).cancelSearch();
                    }
                    searchView.onActionViewCollapsed();
                }
                return false;
            }
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void setHideActionBarSearchItem(boolean shouldShow){
        mDrawerToggle.setDrawerIndicatorEnabled(shouldShow);
        drawer.setDrawerLockMode((shouldShow)?DrawerLayout.LOCK_MODE_UNLOCKED:DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void setListCar(ArrayList<CarBrand> listCar){
        this.listCar = listCar;
    }

    public ArrayList<CarBrand> getListCar(){
        return listCar;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
