package kp.schools.notications.Model;

public class NotificationModel {
    String description,date,title,imageurl;

    public NotificationModel() {
    }

    public NotificationModel(String description, String date, String title, String imageurl) {
        this.description = description;
        this.date = date;
        this.title = title;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
