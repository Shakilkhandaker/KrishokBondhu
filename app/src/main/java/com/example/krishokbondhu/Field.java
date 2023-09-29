package com.example.krishokbondhu;

public class Field {
    private int field_id;
    private String field_mark;
    private String field_village;
    private String field_pouroshova;
    public Field(int field_id,String field_mark, String field_village, String field_pouroshova) {
        this.field_id = field_id;
        this.field_mark = field_mark;
        this.field_village = field_village;
        this.field_pouroshova = field_pouroshova;

    }
    public int getField_id() {return field_id;}
    public String getField_mark() {
        return field_mark;
    }
    public String getField_village() {return field_village;}
    public String getField_pouroshova() {
        return field_pouroshova;
    }
}
