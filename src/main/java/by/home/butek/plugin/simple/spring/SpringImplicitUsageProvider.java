package by.home.butek.plugin.simple.spring;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;

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
                    && inConfigurationClass(method))

                    || (AnnotationUtil.isAnnotated(method, SpringElements.SCHEDULED, 0)
                    && inBeanClass(method));
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
            return AnnotationUtil.isAnnotated(field, SpringElements.BEAN_FIELD_ANNOTATIONS, 0)
                    && (inBeanClass(field) || inConfigurationClass(field));
        }
        return false;
    }

    @Override
    public boolean isImplicitRead(@NotNull PsiElement element) {
        return false;
    }

    private boolean findInParentClassAnnotated(PsiClass psiClass, @NotNull Predicate<PsiClass> predicate) {
        if (psiClass == null) {
            return false;
        }
        if (predicate.test(psiClass)) {
            return true;
        }
        return findInParentClassAnnotated(psiClass.getSuperClass(), predicate);
    }

    private boolean inRestControllerClass(@NotNull PsiMethod method) {
        PsiClass clazz = method.getContainingClass();
        return existsInClass(clazz, SpringElements.REST_CONTROLLER_CLASSES);
    }

    private boolean inConfigurationClass(@NotNull PsiJvmMember member) {
        PsiClass clazz = member.getContainingClass();
        return existsInClass(clazz, SpringElements.CONFIGURATION_CLASSES);
    }

    private boolean inBeanClass(@NotNull PsiJvmMember psiJvmMember) {
        PsiClass clazz = psiJvmMember.getContainingClass();
        return existsInClass(clazz, SpringElements.BEAN_CLASS_ANNOTATIONS);
    }

    private boolean existsInClass(PsiClass psiClass, Collection<String> annotations) {
        Predicate<PsiClass> predicate = (clazz) -> clazz.getQualifiedName() != null && isAnnotated(clazz, annotations, 0);
        return findInParentClassAnnotated(psiClass, predicate);
    }
}
