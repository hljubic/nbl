package ba.sum.sum.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ba.sum.sum.R;
import ba.sum.sum.adapters.AdapterListSectioned;
import ba.sum.sum.models.Task;
import ba.sum.sum.utils.Constants;

public class FragmentSimple extends Fragment {

    Gson gson;
    private ArrayList<Task> tasks;
    private SwipeRefreshLayout swipe_refresh;
    public static final String ARG_URL = "arg_url";
    private AdapterListSectioned mAdapter;

    private Task task;

    public static FragmentSimple newInstance(String url) {
        FragmentSimple fragment = new FragmentSimple();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();

        tasks = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        getData();
        initComponent(root);


        return root;

    }


    private void initComponent(final View root) {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AdapterListSectioned(getContext(), tasks);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(mAdapter);


        swipe_refresh = root.findViewById(R.id.swipe_refresh_layout);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refresh.setRefreshing(false);

                        getData();
                        onPageFinished();
                    }
                }, 1000);
            }

        });

    }
    public void getData() {
        StringRequest taskRequest = new StringRequest(Request.Method.GET, Constants.BASE_API_URL + getArguments().getString(ARG_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Task> list = gson.fromJson(response, new TypeToken<List<Task>>() {
                }.getType());

                Task.saveAll(Task.class, list);

                tasks.clear();
                tasks.addAll(list);

                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.cant_connect, Toast.LENGTH_LONG).show();

                tasks.clear();
                tasks.addAll(Task.listAll(Task.class));

                mAdapter.notifyDataSetChanged();
            }
        });

        Volley.newRequestQueue(getContext()).add(taskRequest);

    }
    public void onPageFinished() {
        swipe_refresh.setRefreshing(false);
    }
      /*  if (getArguments().getBoolean(ARG_STUDIES)) {
            try {
                AdapterListSectioned mAdapter = new AdapterListSectioned(getActivity(), institution.getChildrenSectioned());
                recyclerView.setAdapter(mAdapter);
            } catch (Exception e) {
                AdapterListSectioned mAdapter = new AdapterListSectioned(getActivity(), institution.getChildren());
                recyclerView.setAdapter(mAdapter);
            }
        } else {
            AdapterDocument mAdapter = new AdapterDocument(getActivity(), institution.getDocuments());
            recyclerView.setAdapter(mAdapter);
        }*/
    }
