package mazerace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class HighscoreTableModel implements TableModel
{
    private ArrayList<Map.Entry<String, Integer>> list;

    public HighscoreTableModel(ArrayList<Map.Entry<String, Integer>> list)
    {
        this.list = list;
        if (list != null)
        {
            list.sort(Comparator.comparing(Map.Entry::getValue));
        }
    }

    @Override
    public int getRowCount()
    {
        if (list != null)
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount()
    {
        return 2;
    }

    @Override
    public String getColumnName(int i)
    {
        switch (i)
        {
            case 0:
                return "Name";
            case 1:
                return "Züge";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int i)
    {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        switch (column)
        {
            case 0:
                return list.get(row).getKey();
            case 1:
                return String.format("%,d",list.get(row).getValue());
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1)
    {
    }

    @Override
    public void addTableModelListener(TableModelListener tl)
    {
    }

    @Override
    public void removeTableModelListener(TableModelListener tl)
    {
    }
}
