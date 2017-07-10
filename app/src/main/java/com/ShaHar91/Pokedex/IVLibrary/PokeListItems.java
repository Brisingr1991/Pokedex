package com.ShaHar91.Pokedex.IVLibrary;

public class PokeListItems {

    private String pokeName;
    private String gender;
    private boolean box;
    private int HP;
    private int att;
    private int def;
    private int sp_att;
    private int sp_def;
    private int speed;
    private int RowIdTag;
    private String drawableIndex;

    public PokeListItems(String pokeName, String gender, boolean box,
                         int HP, int att, int def, int sp_att, int sp_def,
                         int speed, int rowIdTag, String drawableIndex) {
        this.pokeName = pokeName;
        this.gender = gender;
        this.box = box;
        this.HP = HP;
        this.att = att;
        this.def = def;
        this.sp_att = sp_att;
        this.sp_def = sp_def;
        this.speed = speed;
        RowIdTag = rowIdTag;
        this.drawableIndex = drawableIndex;
    }

    public String getpokeName() {
        return pokeName;
    }

    public String getGender() {
        return gender;
    }

    public int getHP() {
        return HP;
    }

    public int getRowIdTag() {
        return RowIdTag;
    }

    public int getAtt() {
        return att;
    }

    public int getDef() {
        return def;
    }

    public int getSp_Att() {
        return sp_att;
    }

    public int getSp_Def() {
        return sp_def;
    }

    public int getSpeed() {
        return speed;
    }

    public String getImageIndex() {
        return drawableIndex;
    }

    public boolean isSelected() {
        return box;
    }

    public void setSelected(boolean box) {
        this.box = box;
    }

}
