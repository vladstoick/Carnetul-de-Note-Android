package com.stoicavlad.carnet.DaggerModules;

/**
 * Created by Vlad on 1/26/14.
 */
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface ForActivity {
}