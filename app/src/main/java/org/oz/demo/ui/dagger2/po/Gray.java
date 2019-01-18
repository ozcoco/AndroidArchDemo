package org.oz.demo.ui.dagger2.po;

import javax.inject.Inject;

public class Gray extends Color {

    @Inject
    public Gray() {
    }

    @Override
    public String color() {
        return "#ff555555";
    }
}
