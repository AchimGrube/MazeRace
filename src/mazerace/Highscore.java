package mazerace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

public final class Highscore implements Serializable
{
    private static final long serialVersionUID = 1;
    static final String FILENAME = "highscore.dat";

    private static Highscore instance = null;

    private HashMap<String, ArrayList<Entry<String, Integer>>> scores;

    public Highscore()
    {
        scores = new HashMap<>();
    }

    static Highscore getInstance()
    {
        if (instance == null)
        {
            instance = new Highscore();
        }
        return instance;
    }
    
    boolean isHighscore(String type, int steps)
    {
        ArrayList<Entry<String, Integer>> checkList = scores.get(type);
        if (checkList != null)
        {
            return (steps < findWorst(checkList) || checkList.size() < 10);
        }
        return true;
    }

    private int findWorst(ArrayList<Entry<String, Integer>> checkList)
    {
        int worst = 0;
        for (Entry<String, Integer> entry : checkList)
        {
            if (entry.getValue() > worst)
            {
                worst = entry.getValue();
            }
        }
        return worst;
    }

    void add(String type, int steps, String name)
    {
        ArrayList<Entry<String, Integer>> addList = scores.get(type);
        if (addList == null)
        {
            addList = new ArrayList<>();
        }
        if (addList.size() >= 10)
        {
            addList.sort(Comparator.comparing(Entry::getValue));
            addList.remove(addList.size() - 1);
        }
        addList.add(new SimpleEntry<>(name, steps));
        scores.put(type, addList);
    }

    void save()
    {
        try (FileOutputStream fos = new FileOutputStream(FILENAME); ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(this);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public Highscore load()
    {
        try (FileInputStream fis = new FileInputStream(FILENAME); ObjectInputStream ois = new ObjectInputStream(fis))
        {
            return (Highscore) ois.readObject();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<Entry<String, Integer>> get(String type)
    {
        return scores.get(type);
    }
    
    public void delete()
    {
        File file = new File(FILENAME);
        if (file.exists())
        {
            file.delete();
        }
        scores = new HashMap<>();
    }
}
