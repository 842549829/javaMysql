package com.company.classes.model;

public class Apple {

    String name;
    String color;
    String size;

    public Apple() {
        super();
    }

    public Apple(String name) {
        this.name = name;
    }

    public Apple(String name, String color) {
        this(name);
        this.color = color;
    }

    public Apple(String name, String color, String size) {
        this(name, color);
        this.size = size;
    }

    /* 自动生成的get、set方法 */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {

        return "Apple:{name:" + this.getName() + ";    color:" + this.getColor() + ";    size:" + this.getSize() + ";}";
    }
}
