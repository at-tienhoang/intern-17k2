package vn.asiantech.internship.Day6_Navigation;

/**
 * Created by at-dinhvo on 12/06/2017.
 */

public class DrawableItem {

    private String title;
    private boolean isSelected;

    public DrawableItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
