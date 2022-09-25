package com.filesystem.models;

import com.filesystem.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private String name;
    private String parent;
    private final Map<String, Directory> directories = new HashMap<>();
    private final Map<String, File> files = new HashMap<>();

    public Directory(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public Directory createInnerDirectory(String dirName) {
        directories.put(dirName, new Directory(dirName, parent + "/" + name));
        Log.info("Creating directory " + dirName + " <parent>: " + directories.get(dirName).parent);
        return directories.get(dirName);
    }

    public File createFile(String fileName, String content) {
        File file = new File(fileName, content);
        files.put(fileName, file);
        Log.info("Created file " + fileName);
        return file;
    }

    public File getFile(String name){
        return files.get(name);
    }

    public Directory getOrCreateInnerDirectory(String dirName) {
        Log.info("Creating directory " + dirName + " <parent>: " + parent);
        return directories.get(dirName) == null ? createInnerDirectory(dirName) : directories.get(dirName);
    }
}
