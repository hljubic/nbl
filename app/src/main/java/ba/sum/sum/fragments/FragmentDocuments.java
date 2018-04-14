package ba.sum.sum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import ba.sum.sum.R;
import ba.sum.sum.adapters.AdapterDocument;
import ba.sum.sum.models.Task;

public class FragmentDocuments extends Fragment {

    Gson gson;

    public static final String ARG_TASK_ID = "task_id";

    private Task task;

    public static FragmentDocuments newInstance(String taskId) {
        FragmentDocuments fragment = new FragmentDocuments();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();

        task = Task.findById(Task.class, getArguments().getString(ARG_TASK_ID));

        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        initComponent(root);

        return root;

    }


    private void initComponent(final View root) {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        AdapterDocument mAdapter = new AdapterDocument(getActivity(), task.getDocuments());
        recyclerView.setAdapter(mAdapter);
    }
}