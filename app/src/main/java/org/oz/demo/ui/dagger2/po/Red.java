package org.oz.demo.ui.dagger2.po;

import javax.inject.Inject;

public class Red extends Color {

    @Inject
    public Red() {
    }

    @Override
    public String color() {
        return "#ffff0000";
    }
}
