package com.example.weatherforyou.di.component;

import com.example.weatherforyou.di.module.PagerFragmentModule;
import com.example.weatherforyou.di.scope.FragmentScope;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {PagerFragmentModule.class})
public interface PagerFragmentComponent {
}
