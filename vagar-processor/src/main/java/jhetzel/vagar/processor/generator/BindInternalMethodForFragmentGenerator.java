package jhetzel.vagar.processor.generator;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import jhetzel.vagar.processor.environment.Environment;
import jhetzel.vagar.processor.imitation.Imitations;

final class BindInternalMethodForFragmentGenerator extends Generator<List<TypeElement>, MethodSpec.Builder> {
    private static final String GENERIC_BINDING = "B";
    private static final String GENERIC_VIEW_MODEL = "V";
    private static final String METHOD_NAME = "bindInternal";

    BindInternalMethodForFragmentGenerator(Environment environment) {
        super(environment);
    }

    @Override
    public MethodSpec.Builder generate(List<TypeElement> annotatedClasses) {
        getEnvironment().getLog().note("%s -> Start generating '%s' method implementation",
                getClass().getSimpleName(), METHOD_NAME);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Imitations.Classes.FRAGMENT, "fragment")
                .addParameter(ParameterizedTypeName.get(Imitations.Interfaces.VIEW_MODEL_FACTORY, TypeVariableName.get(GENERIC_VIEW_MODEL)), "factory")
                .addParameter(Imitations.Classes.VIEW_GROUP, "container")
                .addTypeVariable(TypeVariableName.get(GENERIC_VIEW_MODEL, Imitations.Classes.VIEW_MODEL))
                .addTypeVariable(TypeVariableName.get(GENERIC_BINDING, Imitations.Classes.VIEW_DATA_BINDING))
                .addCode(generateInstanceChecks(annotatedClasses))
                .returns(TypeVariableName.get(GENERIC_BINDING));

        getEnvironment().getLog().note("%s -> Generating implementation of method '%s' finished",
                getClass().getSimpleName(), METHOD_NAME);

        return builder;
    }

    private CodeBlock generateInstanceChecks(List<TypeElement> annotatedClasses){
        List<TypeName> arguments = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for(int index = 0 ; index < annotatedClasses.size() ; index++){
            TypeElement annotatedElement = annotatedClasses.get(index);

            if(index == 0){
                builder.append("if(fragment.getClass().equals($T.class)){")
                        .append("\n\treturn bind(($T) fragment, factory, container);")
                        .append("\n}");
            }else{
                builder.append("else if(fragment.getClass().equals($T.class)){")
                        .append("\n\treturn bind(($T) fragment, factory, container);")
                        .append("\n}");
            }
            arguments.add(TypeName.get(annotatedElement.asType()));
            arguments.add(TypeName.get(annotatedElement.asType()));
        }

        builder.append("\nreturn null;\n");
        return CodeBlock.of(builder.toString(), arguments.toArray());
    }
}
