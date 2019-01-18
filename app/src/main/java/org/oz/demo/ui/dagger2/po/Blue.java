package org.oz.demo.ui.dagger2.po;

import javax.inject.Inject;

public class Blue extends Color{

    @Inject
    public Blue() {
    }


    @Override
    public String color() {
       return "#ff0000ff";
    }
}
