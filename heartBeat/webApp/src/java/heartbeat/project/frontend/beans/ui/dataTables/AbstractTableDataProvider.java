package heartbeat.project.frontend.beans.ui.dataTables;

import org.icefaces.ace.model.table.RowStateMap;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/31/13
 */
public abstract class AbstractTableDataProvider
{
    protected RowStateMap rowStateMap = new RowStateMap();
    protected Integer numberOfRows = 20;

    protected abstract void refresh();

    public void clearRowStateMap()
    {
        rowStateMap.clear();
    }

    public RowStateMap getRowStateMap()
    {
        return rowStateMap;
    }

    public void setRowStateMap(RowStateMap rowStateMap)
    {
        this.rowStateMap = rowStateMap;
    }

    public Boolean getNoItemsSelected()
    {
        return rowStateMap.getSelected() == null || rowStateMap.getSelected().isEmpty();
    }

    public Integer getNumberOfRows()
    {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows)
    {
        this.numberOfRows = numberOfRows;
    }
}

