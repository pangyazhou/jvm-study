package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 符合类加载器
 * Date: 2025/3/24 下午5:01
 **/
public class CompositeCustomEntry extends CustomEntry {
    private List<CustomEntry> customEntries;
    public CompositeCustomEntry(String path) {
        for (String pathElement : path.split(CustomEntry.PATH_LIST_SEPARATOR)) {
            customEntries.add(getEntry(pathElement));
        }
    }

    @Override
    public byte[] readClass(String className) {
        for (CustomEntry entry : customEntries) {
            try {
                byte[] bytes = entry.readClass(className);
                if (bytes != null) {
                    return bytes;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        List<String> strs = new ArrayList<>();
        for (CustomEntry entry : customEntries) {
            strs.add(entry.toString());
        }
        return String.join(PATH_LIST_SEPARATOR, strs);
    }
}
