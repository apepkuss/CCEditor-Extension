import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * Created by huangca on 2015/4/14.
 */
public class CcTestAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Application application = ApplicationManager.getApplication();

        MyComponent myComponent = application.getComponent(MyComponent.class);

        myComponent.createToolWindowContent(e.getProject(), ToolWindowManager.getInstance(e.getProject()).registerToolWindow("CC Editor ",true, ToolWindowAnchor.RIGHT));//regesite  tool windows




    }

}
