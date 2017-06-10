package de.juliushetzel.vagar.processor.generator;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.TypeElement;

import de.juliushetzel.vagar.processor.environment.Environment;

public abstract class Generator<I, O> {
    private final Environment mEnvironment;

    Generator(Environment environment){
        mEnvironment = environment;
    }

    Environment getEnvironment(){
        return mEnvironment;
    }

    public abstract O generate(I input);

    public static Generator<TypeElement, MethodSpec.Builder> forMethodBind(Environment environment){
        return new BindMethodGenerator(environment);
    }

    public static Generator<List<TypeElement>, TypeSpec.Builder> forClassVagar(Environment environment){
        return new EntryPointClassGenerator(environment);
    }

    public static Generator<List<TypeElement>, TypeSpec.Builder> forClassViewModelBuilderContainer(Environment environment){
        return new ViewModelBuilderContainerGenerator(environment);
    }

    public static Generator<Class<?>, TypeSpec.Builder> forClassViewModelBuilder(Environment environment){
        return new ViewModelBuilderInnerClassGenerator(environment);
    }
}
