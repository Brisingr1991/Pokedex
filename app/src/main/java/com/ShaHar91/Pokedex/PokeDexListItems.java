package com.ShaHar91.Pokedex;

public class PokeDexListItems {

    String pokeName;
    String type1;
    String type2;
    String natDex;
    int isMega;

    long dex;

    int RowIdTag;
    String drawableIndex;

    public int getisMega() {
        return isMega;
    }

    public void setisMega(int isMega) {
        this.isMega = isMega;
    }

    public String getpokeName() {
        return pokeName;
    }

    public void setpokeName(String pokeName) {
        this.pokeName = pokeName;
    }

    public Long getLongNatDex() {
        return dex;
    }

    public void setLongNatDex(Long dex) {
        this.dex = dex;
    }

    public void setNatDex(String natDex) {
        this.natDex = natDex;
    }

    public String getNatDex() {
        return natDex;
    }

    public void setType1Dex(String type1) {
        this.type1 = type1;
    }

    public String getType1() {
        return type1;
    }

    public void setType2Dex(String type2) {
        this.type2 = type2;
    }

    public String getType2() {
        return type2;
    }

    public int getRowIdTag() {
        return RowIdTag;
    }

    public void setRowIdTag(int RowId) {
        this.RowIdTag = RowId;
    }

    public void setDrawable(String drawableIndex) {
        this.drawableIndex = drawableIndex;
    }

    public String getImageIndex() {
        return drawableIndex;
    }

}
