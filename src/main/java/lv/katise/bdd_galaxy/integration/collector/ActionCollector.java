package lv.katise.bdd_galaxy.integration.collector;

import lv.katise.bdd_galaxy.core.UUIDGenerator;
import lv.katise.bdd_galaxy.integration.bdd.Action;
import lv.katise.bdd_galaxy.integration.bdd.BDDContext;
import lv.katise.bdd_galaxy.integration.bdd.Group;
import lv.katise.bdd_galaxy.integration.collector.action.IActionReturn;
import lv.katise.bdd_galaxy.integration.collector.action.defauld.ActionArgument;
import lv.katise.bdd_galaxy.integration.collector.action.defauld.ArgumentType;
import lv.katise.bdd_galaxy.integration.collector.action.defauld.BDDStep;
import lv.katise.bdd_galaxy.integration.collector.action.defauld.DefaultReturn;
import lv.katise.bdd_galaxy.integration.collector.hierarchy.ActionHierarchy;
import lv.katise.bdd_galaxy.integration.collector.hierarchy.IActionHierarchy;
import lv.katise.bdd_galaxy.integration.collector.hierarchy.context.ActionGroup;
import lv.katise.bdd_galaxy.integration.collector.hierarchy.context.ITestStepGroup;
import lv.katise.bdd_galaxy.properties.PropertiesService;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ActionCollector implements IActionCollector {

    private final Map<UUID, ITestStepGroup> groupMap = new HashMap<>();

    @Override
    public IActionHierarchy buildActionsHierarchy(String... gluePaths) {
        ActionHierarchy hierarchy = new ActionHierarchy(PropertiesService.getPOMProperties().getVersion());
        List<BDDStep> actions = new ArrayList<>();
        if (gluePaths != null) {
            for (String path : gluePaths) {
                actions.addAll(collectActions(path));
            }
        }
        for (BDDStep action : actions) {
            ITestStepGroup group = groupMap.get(action.getGroupId());
            group.addStep(action);
        }
        groupMap.forEach((key, value) -> hierarchy.addContext(value));
        return hierarchy;
    }

    private List<BDDStep> collectActions(String gluePath) {
        List<Method> methods = getMethods(gluePath, Action.class);
        return methods.stream().map(e -> {
            Class<?> declaringClass = e.getDeclaringClass();
            Action annotation = e.getAnnotation(Action.class);

            IActionReturn returnInstance = buildReturn(e.getReturnType(), declaringClass);
            BDDStep bddStep = new BDDStep(
                    annotation.isEntrypoint(),
                    annotation.value(),
                    null,
                    returnInstance);
            bddStep.setGroupId(provideCurrentGroup(declaringClass).getId());
            for (Parameter parameter : e.getParameters()) {
                Class<?> parameterType = parameter.getType();
                Optional<ArgumentType> argumentType = provideArgumentType(parameterType);
                if (argumentType.isEmpty()) {
                    throw new RuntimeException(String.format("For method: '%s' Unsupported argument type: '%s'",
                            e.getName(), parameterType));
                }
                bddStep.addArgument(new ActionArgument(argumentType.get(), parameter.getName()));
            }
            bddStep.setId(UUIDGenerator.generateUUIDFromString(bddStep.getGroupId() + " " + bddStep.getName()));

            return bddStep;
        }).collect(Collectors.toList());
    }

    private IActionReturn buildReturn(Class<?> returnClass, Class<?> definedClass) {
        DefaultReturn defaultReturn = new DefaultReturn();
        ITestStepGroup group = provideReturnGroup(returnClass, definedClass);
        defaultReturn.setGroupId(group.getId());

        if (returnClass.equals(Void.TYPE)) {
            group = provideCurrentGroup(definedClass);
            defaultReturn.setGroupId(group.getId());
            return defaultReturn;
        } else if (!BDDContext.class.isAssignableFrom(returnClass)) {
            Optional<ArgumentType> argumentType = provideArgumentType(returnClass);
            if (argumentType.isEmpty()) {
                throw new RuntimeException(String.format("For class: '%s' Unsupported return type: %s",
                        defaultReturn, returnClass));
            }
            defaultReturn.setReturn(new ActionArgument(argumentType.get(), null));
        }
        return defaultReturn;
    }

    private Optional<ArgumentType> provideArgumentType(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return Optional.of(ArgumentType.STRING);
        } else if (clazz.equals(Enum.class)) {
            return Optional.of(ArgumentType.STRING);
        } else if (clazz.equals(Boolean.class)) {
            return Optional.of(ArgumentType.BOOLEAN);
        } else if (clazz.equals(Integer.class)) {
            return Optional.of(ArgumentType.INTEGER);
        } else if (clazz.equals(Long.class)) {
            return Optional.of(ArgumentType.INTEGER);
        } else if (clazz.equals(Double.class)) {
            return Optional.of(ArgumentType.DOUBLE);
        } else if (clazz.equals(Float.class)) {
            return Optional.of(ArgumentType.DOUBLE);
        } else if (clazz.equals(LocalDateTime.class)) {
            return Optional.of(ArgumentType.DATE_TIME);
        }
        return Optional.empty();
    }

    private ITestStepGroup provideReturnGroup(Class<?> returnClass, Class<?> definedClass) {
        Optional<ArgumentType> argumentType = provideArgumentType(returnClass);
        if (argumentType.isPresent()) {
            return provideCurrentGroup(definedClass);
        } else {
            return provideCurrentGroup(returnClass);
        }
    }

    private ITestStepGroup provideCurrentGroup(Class<?> actionDefinedClass) {
        UUID definedKey = UUIDGenerator.generateUUIDFromString(String.valueOf(actionDefinedClass));
        if (!groupMap.containsKey(definedKey)) {
            Class<?> groupClass = null;
            String groupName = null;
            if (BDDContext.class.isAssignableFrom(actionDefinedClass) ||
                    actionDefinedClass.isAnnotationPresent(Group.class)) {
                groupClass = actionDefinedClass;
                groupName = actionDefinedClass.isAnnotationPresent(Group.class) ?
                        actionDefinedClass.getAnnotation(Group.class).value() :
                        String.valueOf(actionDefinedClass);
            }

            ActionGroup actionGroup = new ActionGroup(groupClass, groupName);
            groupMap.put(UUIDGenerator.generateUUIDFromString(String.valueOf(groupClass)), actionGroup);
            return actionGroup;
        } else {
            return groupMap.get(definedKey);
        }
    }

    private List<Method> getMethods(String packageName, Class<? extends Annotation> annotation) {
        if (packageName != null && !packageName.isEmpty()) {
            Reflections reflections = new Reflections(packageName,
                    new SubTypesScanner(),
                    new TypeAnnotationsScanner(),
                    new FieldAnnotationsScanner(),
                    new MethodAnnotationsScanner());
            return new ArrayList<>(reflections.getMethodsAnnotatedWith(annotation));
        }
        return new ArrayList<>();
    }
}
