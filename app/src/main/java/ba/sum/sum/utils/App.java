package ba.sum.sum.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ba.hljubic.jsonorm.JsonOrm;
import ba.sum.sum.models.Institution;
import ba.sum.sum.models.Task;

/**
 * Created by hrvoje on 25/03/2018.
 */

public class App extends Application {

    // Static instance
    private static App instance;

    private List<Institution> institutions;
    private List<Task> tasks;

    public static synchronized App get() {
        if (instance == null) {
            instance = new App();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        JsonOrm.with(this);

        tasks = new ArrayList<>();
    }

    public void initInstitutions() {
        institutions = Institution.listAll(Institution.class);
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
