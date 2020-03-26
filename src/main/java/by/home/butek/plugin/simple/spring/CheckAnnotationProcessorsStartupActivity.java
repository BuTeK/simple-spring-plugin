package by.home.butek.plugin.simple.spring;

import com.intellij.compiler.CompilerConfiguration;
import com.intellij.compiler.CompilerConfigurationImpl;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;

public class CheckAnnotationProcessorsStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DumbService.getInstance(project).runWhenSmart(() -> {
            // after initial indexing

            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
            GlobalSearchScope projectScope = GlobalSearchScope.allScope(project);

            if (javaPsiFacade.findClass(SpringElements.APPLICATION_CLASS, projectScope) != null) {
                // Yey! we have SpringBoot in classpath

                CompilerConfigurationImpl compilerConfiguration = getCompilerConfiguration(project);
                if (!compilerConfiguration.getDefaultProcessorProfile().isEnabled()) {
                    suggestEnableAnnotations(project);
                }
            }
        });
    }

    private CompilerConfigurationImpl getCompilerConfiguration(Project project) {
        return (CompilerConfigurationImpl) CompilerConfiguration.getInstance(project);
    }

    private void suggestEnableAnnotations(Project project) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(
                        "Do you want to enable annotation processors for Spring Frameworks compilation? <a href=\"enable\">Enable</a>",
                        MessageType.WARNING, e -> {
                            if (HyperlinkEvent.EventType.ACTIVATED == e.getEventType()) {
                                enableAnnotations(project);
                            }
                        })
                .setHideOnLinkClick(true)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
    }

    private void enableAnnotations(Project project) {
        getCompilerConfiguration(project).getDefaultProcessorProfile().setEnabled(true);

        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("Java annotation processing has been enabled", MessageType.INFO, null)
                .setFadeoutTime(3000)
                .createBalloon()
                .show(RelativePoint.getNorthEastOf(statusBar.getComponent()), Balloon.Position.atRight);
    }
}
