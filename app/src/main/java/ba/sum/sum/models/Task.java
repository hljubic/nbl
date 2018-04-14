package ba.sum.sum.models;

import java.util.List;

import ba.sum.sum.utils.App;

/**
 * Created by HP_PC on 14.4.2018..
 */

public class Task {

    private String id;
    private String title;
    private String desc;
    private String date;
    private String date_renewal;
    private String remark;
    private double price;

    private Client client;

    private List<Document> documents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_renewal() {
        return date_renewal;
    }

    public void setDate_renewal(String date_renewal) {
        this.date_renewal = date_renewal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static Task findById(String id) {
        List<Task> tasks = App.get().getTasks();

        for (Task task : tasks) {
            if (task.getId().equals(id))
                return task;
        }

        return new Task(); // TODO: return null;
    }
}
