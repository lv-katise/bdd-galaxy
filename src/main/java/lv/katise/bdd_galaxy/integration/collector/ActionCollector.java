package lv.katise.bdd_galaxy.integration.collector;

import lv.katise.bdd_galaxy.integration.bdd.DefinedStep;
import lv.katise.bdd_galaxy.integration.bdd.Entrypoint;
import lv.katise.bdd_galaxy.integration.collector.action.BDDAction;
import lv.katise.bdd_galaxy.integration.collector.action.IAction;
import lv.katise.bdd_galaxy.integration.collector.graph.ActionGraph;
import lv.katise.bdd_galaxy.integration.collector.graph.IActionGraph;
import lv.katise.bdd_galaxy.integration.collector.graph.context.ActionContext;
import lv.katise.bdd_galaxy.integration.collector.graph.context.IActionContext;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionCollector<T> implements IActionCollector<T> {

    private final String gluePath;
    private final Class<T> tClass;
    private final Map<Class<? extends T>, IActionContext<T>> contextMap = new HashMap<>();
    ;

    public ActionCollector(String gluePath, Class<T> tClass) {
        this.gluePath = gluePath;
        this.tClass = tClass;
    }

    @Override
    public List<IAction<T>> collectEntryPoints() {
        List<Method> methods = getMethods(gluePath, Entrypoint.class);
        return methods.stream().map(e -> {
            Entrypoint annotation = e.getAnnotation(Entrypoint.class);
            return new BDDAction<>(true, (Class<T>) e.getDeclaringClass(), e.getName(), annotation.value(), null, e.getReturnType());
        }).collect(Collectors.toList());
    }

    @Override
    public List<IAction<T>> collectSteps(Class<? extends T> rootClass) {
        List<Method> methods = getMethods(rootClass, DefinedStep.class);
        return methods.stream().map(e -> {
            DefinedStep annotation = e.getAnnotation(DefinedStep.class);
            return new BDDAction<T>(false, (Class<T>) e.getDeclaringClass(), e.getName(), annotation.value(), null, e.getReturnType());
        }).collect(Collectors.toList());
    }

    @Override
    public IActionGraph<T> buildStepGraph() {
        ActionGraph<T> actionGraph = new ActionGraph<>();

        List<IAction<T>> entryPoints = collectEntryPoints();
        actionGraph.addEntryPoints(entryPoints);
        fill(entryPoints);

        return actionGraph;
    }

    @Override
    public IActionContext<T> provideContext(Class<? extends T> returnType) {
        if (!contextMap.containsKey(returnType)) {
            ActionContext<T> actionContext = new ActionContext<>();
            actionContext.setContextClass(returnType);
            contextMap.put(returnType, actionContext);
            List<IAction<T>> actions = collectSteps(returnType);
            actionContext.setActions(actions);
            fill(actions);
            return actionContext;
        } else {
            return contextMap.get(returnType);
        }
    }

    private void fill(List<IAction<T>> actions) {
        for (IAction<T> action : actions) {
            Class<?> returnType = action.getReturnType();
            if (tClass.isAssignableFrom(returnType)) {
                action.setReturnContext(provideContext((Class<? extends T>) returnType));
            } else {
                action.setReturnContext(provideContext(action.getDefinedClass()));
            }
        }
    }

    private List<Method> getMethods(String packageName, Class<? extends Annotation> annotation) {
        if (packageName != null && !packageName.isEmpty()) {
            Reflections reflections = new Reflections(packageName, new SubTypesScanner(),
                    new TypeAnnotationsScanner(),
                    new FieldAnnotationsScanner(), new MethodAnnotationsScanner());
            return new ArrayList<>(reflections.getMethodsAnnotatedWith(annotation));
        }
        return new ArrayList<>();
    }

    private List<Method> getMethods(Class<? extends T> rootClazz, Class<? extends Annotation> annotation) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : rootClazz.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
        return methods;
    }
}
