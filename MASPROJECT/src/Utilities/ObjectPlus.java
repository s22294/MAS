package Utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class ObjectPlus implements Serializable {

    /**
     * Utility class responsible for persistence and extent handling.
     * */
    private static Map<Class, List<ObjectPlus>> allExtents = new HashMap<>();

    public ObjectPlus() {
        List<ObjectPlus> extent;
        Class CLASS = this.getClass();
        if (allExtents.containsKey(CLASS))
            extent = allExtents.get(CLASS);
        else {
            extent = new ArrayList<>();
            allExtents.put(CLASS, extent);
        }
        extent.add(this);
    }
    public void removeFromExtent(){
        if(!allExtents.containsKey(this.getClass()))
            throw new IllegalArgumentException("No such extent!");
        List<ObjectPlus> extent = allExtents.get(this.getClass());
        if(!extent.contains(this))
            return;
        extent.remove(this);
    }

    public static void saveExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    public static void loadExtents(ObjectInputStream stream) throws Exception {
        allExtents = (HashMap<Class, List<ObjectPlus>>) stream.readObject();
    }

    public static List<ObjectPlus> getExtent(Class type) throws ClassNotFoundException {
        if (!allExtents.containsKey(type))
            throw new ClassNotFoundException("Extent not found!");
        return Collections.unmodifiableList(allExtents.get(type));
    }

    public static void printExtent(Class CLASS) throws Exception {
        List<ObjectPlus> extent;
        if (!allExtents.containsKey(CLASS))
            throw new Exception("Class not found!");
        extent = allExtents.get(CLASS);
        extent.forEach(obj -> System.out.println(obj));
    }
    public static Map<Class, List<ObjectPlus>> getAllExtents(){
        return Collections.unmodifiableMap(allExtents);
    }
}
