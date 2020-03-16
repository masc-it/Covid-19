package com.onipot.covid19;

public class Dimension {

    private String name;
    private boolean visible = false;

    public void toggle() {
        visible = !visible;
    }

    public Dimension(String name){
        this.name = name;

        if (name.equals("tamponi")){
            toggle();
        }
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }
}
