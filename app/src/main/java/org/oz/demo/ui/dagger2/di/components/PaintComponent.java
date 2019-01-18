package org.oz.demo.ui.dagger2.di.components;

import org.oz.demo.ui.dagger2.Dagger2Fragment;
import org.oz.demo.ui.dagger2.di.modules.ColorModule;
import org.oz.demo.ui.dagger2.di.modules.PaintModule;

import dagger.Component;

@Component(modules = {ColorModule.class, PaintModule.class})
public interface PaintComponent {
    void inject(Dagger2Fragment fragment);
}
