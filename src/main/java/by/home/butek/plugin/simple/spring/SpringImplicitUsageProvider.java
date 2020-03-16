package by.home.butek.plugin.simple.spring;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

import static com.intellij.codeInsight.AnnotationUtil.isAnnotated;

public class SpringImplicitUsageProvider implements ImplicitUsageProvider {

    @Override
    public boolean isImplicitUsage(@NotNull PsiElement element) {
        if (element instanceof PsiClass) {
            PsiClass clazz = (PsiClass) element;
            return AnnotationUtil.isAnnotated(clazz, SpringElements.BEAN_CLASS_ANNOTATIONS, 0) || AnnotationUtil.isAnnotated(clazz, SpringElements.CONFIGURATION_CLASSES, 0);
        }
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;
            return (method.hasModifierProperty(PsiModifier.PUBLIC)
                    && !method.hasModifierProperty(PsiModifier.STATIC)
                    && AnnotationUtil.isAnnotated(method, SpringElements.METHOD_ANNOTATIONS, 0)
                    && inRestControllerClass(method))

                    || (!method.hasModifierProperty(PsiModifier.PRIVATE)
                    && AnnotationUtil.isAnnotated(method, SpringElements.CONFIGURATION_METHOD_ANNOTATIONS, 0)
                    && inConfigurationClass(method));
        }
        if (element instanceof PsiField) {
            PsiField field = (PsiField) element;
            return AnnotationUtil.isAnnotated(field, SpringElements.BEAN_FIELD_ANNOTATIONS, 0) && inConfigurationClass(field);
        }
        return false;
    }

    @Override
    public boolean isImplicitWrite(@NotNull PsiElement element) {
        if (element instanceof PsiField) {
            PsiField field = (PsiField) element;
            return AnnotationUtil.isAnnotated(field, SpringElements.BEAN_FIELD_ANNOTATIONS, 0) && inBeanClass(field);
        }
        return false;
    }

    @Override
    public boolean isImplicitRead(@NotNull PsiElement element) {
        return false;
    }

    private boolean inRestControllerClass(@NotNull PsiMethod method) {
        PsiClass clazz = method.getContainingClass();
        return existsInClass(clazz, Collections.singletonList(SpringElements.REST_CONTROLLER_CLASS));
    }

    private boolean inConfigurationClass(@NotNull PsiField field) {
        PsiClass clazz = field.getContainingClass();
        return existsInClass(clazz, SpringElements.CONFIGURATION_CLASSES);
    }

    private boolean inConfigurationClass(@NotNull PsiMethod method) {
        PsiClass clazz = method.getContainingClass();
        return existsInClass(clazz, SpringElements.CONFIGURATION_CLASSES);
    }

    private boolean inBeanClass(@NotNull PsiField field) {
        PsiClass clazz = field.getContainingClass();
        return existsInClass(clazz, SpringElements.BEAN_CLASS_ANNOTATIONS);
    }

    private boolean existsInClass(PsiClass clazz, Collection<String> annotations) {
        return clazz != null && clazz.getQualifiedName() != null && isAnnotated(clazz, annotations, 0);
    }
}
