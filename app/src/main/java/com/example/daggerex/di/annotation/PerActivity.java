package com.example.daggerex.di.annotation;

import javax.inject.Qualifier;
import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Scope
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
