package heartbeat.project.frontend.beans.ui.dataTables;

import java.util.Collection;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/31/13
 */
public abstract class AbstractEagerTableDataProvider<T> extends AbstractTableDataProvider
{
    protected Collection<T> dataModel;

    public Collection<T> getDataModel()
    {
        return dataModel;
    }
}

