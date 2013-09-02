package heartbeat.project.frontend.beans.ui;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.Map;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 9/2/13
 */
public class ViewScope implements Scope
{
    public static final String VIEW_SCOPE_CALLBACKS = "VIEW_SCOPE_CALLBACKS";

    @Override
    public Object get(String name, ObjectFactory objectFactory)
    {
        Map viewMap = getViewMap();
        Object instance = viewMap.get(name);
        if(instance == null)
        {
            instance = objectFactory.getObject();
            viewMap.put(name, instance);
        }
        return instance;
    }

    @Override
    public Object remove(String name)
    {
        Object instance = getViewMap().remove(name);
        if(instance != null)
        {
            Map<String,Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
            if(callbacks != null)
            {
                callbacks.remove(name);
            }
        }
        return instance;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable)
    {
        //TODO:  there's an issue in JSF 2.1.X causing the destruction callbacks not to be called
        //TODO:  will be fixed in JSF 2.2
        Map<String,Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
        if(callbacks != null)
        {
            callbacks.put(name,runnable);
        }
    }

    @Override
    public Object resolveContextualObject(String name)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.resolveReference(name);
    }

    @Override
    public String getConversationId()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
    }

    private Map<String,Object> getViewMap()
    {
        return Collections.synchronizedMap(FacesContext.getCurrentInstance().getViewRoot().getViewMap());
    }
}

