package ba.sum.sum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ba.sum.sum.adapters.AdapterPager;
import ba.sum.sum.fragments.FragmentAbout;
import ba.sum.sum.fragments.FragmentDocuments;
import ba.sum.sum.models.Task;
import ba.sum.sum.utils.Constants;
import ba.sum.sum.utils.ViewAnimation;

public class DetailsActivity extends AppCompatActivity {

    private Task task;
    private View backDrop;
    private boolean rotate = false;
    private View fabNavigate;
    private View fabContact;
    private View fabCall;
    private FloatingActionButton fabMain, fabFollow;
    private ViewPager viewPager;
    private boolean shouldHideNavigate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getIntent().getExtras() == null)
            return;

        try {
            String id = getIntent().getExtras().getString("task_id", "-1");
            task = Task.findById(id);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(task.getTitle());
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

            viewPager = findViewById(R.id.view_pager);
            setupViewPager(viewPager);

            TabLayout tabLayout = findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        backDrop = findViewById(R.id.back_drop);

        final FloatingActionButton fabNavigateBtn = findViewById(R.id.fab_map);
        final FloatingActionButton fabContactBtn = findViewById(R.id.fab_contact);
        final FloatingActionButton fabCallBtn = findViewById(R.id.fab_call);

        fabMain = findViewById(R.id.fab_add);
        fabFollow = findViewById(R.id.fab_follow);

        fabNavigate = findViewById(R.id.lyt_map);
        fabCall = findViewById(R.id.lyt_call);
        fabContact = findViewById(R.id.lyt_contact);

        ViewAnimation.initShowOut(fabNavigate);
        ViewAnimation.initShowOut(fabCall);
        ViewAnimation.initShowOut(fabContact);

        backDrop.setVisibility(View.GONE);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        backDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(fabMain);
            }
        });

        fabNavigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate();
            }
        });

        fabCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", task.getClient().getPhone(), null));
                startActivity(intent);
            }
        });

        fabContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest taskRequest = new StringRequest(Request.Method.GET, Constants.BASE_API_URL + "realiziraj/" + task.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                finish();
                            }
                        },null);

                Volley.newRequestQueue(getApplicationContext()).add(taskRequest);

            }
        });

    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(fabNavigate);
            ViewAnimation.showIn(fabCall);
            ViewAnimation.showIn(fabContact);
            backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(fabNavigate);
            ViewAnimation.showOut(fabCall);
            ViewAnimation.showOut(fabContact);
            backDrop.setVisibility(View.GONE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterPager adapter = new AdapterPager(getSupportFragmentManager());

        adapter.addFragment(FragmentAbout.newInstance(task.getId()), "Informacije");
        adapter.addFragment(FragmentDocuments.newInstance(task.getId()), "Dokumenti");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_navigate) {
            navigate();
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(DetailsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigate() {
        try {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + task.getClient().getLatitude() + ","
                    + task.getClient().getLongitude() + "&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void handleChildWithoutFab() {
        fabMain.setVisibility(View.GONE);

        shouldHideNavigate = true;
    }

    @Override
    public void onBackPressed() {
        if (rotate) {
            toggleFabMode(fabMain);
            return;
        }

        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(0);
            return;
        }

        super.onBackPressed();
    }
}
