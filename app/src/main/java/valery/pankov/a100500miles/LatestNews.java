package valery.pankov.a100500miles;

/**
 * Created by Valery on 16.03.2017.
 */

public class LatestNews {
    private String title;
    private String url;
    private String urlImage;
    private String date;

    public LatestNews(String title, String url, String urlImage, String date) {
        this.title = title;
        this.url = url;
        this.urlImage = urlImage;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDate(String date){
        this.date = date;
    }


    public String getDate(){return date;}
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
