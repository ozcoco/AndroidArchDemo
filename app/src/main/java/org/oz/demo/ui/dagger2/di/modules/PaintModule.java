package org.oz.demo.ui.dagger2.di.modules;

import org.oz.demo.ui.dagger2.Paint;

import dagger.Module;
import dagger.Provides;

@Module
public class PaintModule {

    @Provides
    Paint providePaint() {

        return new Paint();
    }

}
