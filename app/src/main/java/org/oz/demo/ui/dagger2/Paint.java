package org.oz.demo.ui.dagger2;

import org.oz.demo.ui.dagger2.di.modules.RedColor;
import org.oz.demo.ui.dagger2.po.Color;

import javax.inject.Inject;

public class Paint {

    @RedColor
    @Inject
    Color color;

    @Inject
    public Paint() {
    }


    public void setColor(@RedColor Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
