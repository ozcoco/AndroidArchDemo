package org.oz.demo.ui.dagger2.di.modules;

import org.oz.demo.ui.dagger2.po.Blue;
import org.oz.demo.ui.dagger2.po.Color;
import org.oz.demo.ui.dagger2.po.Gray;
import org.oz.demo.ui.dagger2.po.Green;
import org.oz.demo.ui.dagger2.po.Red;

import dagger.Module;
import dagger.Provides;

@Module
public class ColorModule {

    @RedColor
    @Provides
    Color provideRed() {
        return new Red();
    }

    @BlueColor
    @Provides
    Color provideBlue() {
        return new Blue();
    }

    @GreenColor
    @Provides
    Color provideGreen() {
        return new Green();
    }

    @GrayColor
    @Provides
    Color provideGray() {
        return new Gray();
    }

}
