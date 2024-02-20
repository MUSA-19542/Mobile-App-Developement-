package Domain;

public class CategoryDomain {
    private String title,pic,type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CategoryDomain(String title, String pic, String Type) {
        this.title = title;
        this.pic = pic;
        this.type=Type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
