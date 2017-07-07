package tigerstyle.social.com.banggiaxe.model;

/**
 * Created by QuyDV on 7/7/17.
 */

public class NewsObject {
    private String urlDetail;
    private String urlImage;
    private String title;
    private String desc;

    public NewsObject() {
    }

    public NewsObject(String urlDetail, String urlImage, String title, String desc) {
        this.urlDetail = urlDetail;
        this.urlImage = urlImage;
        this.title = title;
        this.desc = desc;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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
}
