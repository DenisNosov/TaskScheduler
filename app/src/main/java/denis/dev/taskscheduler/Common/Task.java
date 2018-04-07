package denis.dev.taskscheduler.Common;

import java.util.Date;
import io.realm.RealmObject;

public class Task extends RealmObject {
    private String name;
    private Date date;
    private Date time;
    private String description;



    public Task(String name, Date date, Date time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public Task(String name, Date date, Date time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Task(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
