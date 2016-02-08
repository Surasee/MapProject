package com.tho.mapprippree;

public class DrawerItem {
    public String ItemName;
    public int imgResID;
    public String title;
    public boolean isChecked;

    public DrawerItem(String itemName, int imgResID, boolean isChecked) {
        ItemName = itemName;
        this.imgResID = imgResID;
        this.isChecked = isChecked;
    }
    public DrawerItem(String title) {
        this(null, 0, false);
        this.title = title;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
