package ba.sum.sum.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.hljubic.jsonorm.JsonTable;

/**
 * Created by HP_PC on 14.4.2018..
 */

public class Task extends JsonTable<Task> {

    private String title;
    private String desc;
    private String date;
    private String date_renewal;
    private String remark;
    private double price;
    @SerializedName("task_id")
    private int taskID;

    List<Task> tasks;

    public Task() {
    }


    public Task(String title, String desc, String date, String date_renewal, String remark, double price, int taskID) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.date_renewal = date_renewal;
        this.remark = remark;
        this.price = price;
        this.taskID = taskID;
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

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
