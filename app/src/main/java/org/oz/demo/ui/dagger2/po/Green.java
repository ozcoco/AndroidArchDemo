package org.oz.demo.ui.dagger2.po;

import javax.inject.Inject;

public class Green extends Color {

    @Inject
    public Green() {
    }

    @Override
    public String color() {
        return "#ff00ff00";
    }
}
