package akash.sarkar.clickquick;

public class ColorsModel {

    Integer colorId;
    boolean isGrey;

    public ColorsModel() {
    }

    public ColorsModel(Integer colorId, boolean isGrey) {
        this.colorId = colorId;
        this.isGrey = isGrey;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public boolean isGrey() {
        return isGrey;
    }

    public void setGrey(boolean grey) {
        isGrey = grey;
    }
}
