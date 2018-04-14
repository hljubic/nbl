package ba.sum.sum.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import ba.sum.sum.R;
import ba.sum.sum.models.Task;

public class FragmentAbout extends Fragment {

    private static final String ARG_task_ID = "task_id";
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private Task task;

    public FragmentAbout() {
    }

    public static FragmentAbout newInstance(String taskId) {
        FragmentAbout fragment = new FragmentAbout();
        Bundle args = new Bundle();
        args.putString(ARG_task_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;

        task = Task.findById(String.valueOf(getArguments().getString(ARG_task_ID)));

        root = inflater.inflate(R.layout.fragment_about_no_images, container, false);
        initComponent(root);

        return root;
    }

    private void initComponent(final View root) {
        try {
            ((TextView) root.findViewById(R.id.client)).setText(task.getClient() == null ? "" : task.getClient().getTitle());
            ((TextView) root.findViewById(R.id.title)).setText(task.getTitle());
            ((HtmlTextView) root.findViewById(R.id.desc)).setHtml(task.getDesc());
            ((TextView) root.findViewById(R.id.date)).setText(task.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }

}