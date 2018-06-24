package tigerstyle.social.com.banggiaxe.model;

/**
 * Created by quydv on 3/3/18.
 */

public class OldPompetitor {
    private String img_doithu;

    private String title_doithu;

    private String detail_competitor;

    public OldPompetitor() {
    }

    public OldPompetitor(String img_doithu, String title_doithu, String detail_competitor) {
        this.img_doithu = img_doithu;
        this.title_doithu = title_doithu;
        this.detail_competitor = detail_competitor;
    }

    public String getImg_doithu() {
        return img_doithu;
    }

    public void setImg_doithu(String img_doithu) {
        this.img_doithu = img_doithu;
    }

    public String getTitle_doithu() {
        return title_doithu;
    }

    public void setTitle_doithu(String title_doithu) {
        this.title_doithu = title_doithu;
    }

    public String getDetail_competitor() {
        return detail_competitor;
    }

    public void setDetail_competitor(String detail_competitor) {
        this.detail_competitor = detail_competitor;
    }
}
