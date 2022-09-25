package com.filesystem.models;

import com.filesystem.util.Log;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Drive {

    private final Map<String, Directory> directories = new HashMap<>();
    private int size;
    private int spaceLeft;
    private String driveName;

    public Drive(int size, String driveName) {
        this.size = size;
        this.driveName = driveName;
    }

    public void createFile(String fileName, String path, String content) {
        StringBuilder currPath = new StringBuilder();
        String[] pathsToMove = path.split("/");
        currPath.append("/").append(pathsToMove[0]);
        Directory destinationDir = directories.get(pathsToMove[0]);
        if (destinationDir == null) {
            destinationDir = createDriveRootDirectory(pathsToMove[0]);
            directories.put(pathsToMove[0], destinationDir);
        }
        System.out.println(Arrays.toString(pathsToMove));
        for (int i = 1; i < pathsToMove.length; i++) { // starting from 1 since [0] is covered in rootDir
            // keep moving in till we have the final dir
            // if path is not present, create path
            Log.info("going to " + pathsToMove[i]);
            destinationDir = destinationDir.getOrCreateInnerDirectory(pathsToMove[i]);
            currPath.append("/").append(pathsToMove[i]);
        }

        createFile(fileName, content, destinationDir);
    }

    private void createFile(String fileName, String content, Directory destinationDir) {
        if(spaceLeft < content.length()){
            Log.error("Space left: " + spaceLeft + " ; file size: " + content.length());
        }
        File file = destinationDir.createFile(fileName, content);
        spaceLeft = spaceLeft - file.getContent().length();
    }

    public void deleteFile() {
        //spaceLeft = spaceLeft + file.getContent().length();
    }

    public Directory createDriveRootDirectory(String dir) {
        Directory directory = new Directory(dir, "/" + driveName);
        Log.info("Creating root directory " + dir + " in drive " + driveName);
        return directory;
    }

    public void deleteDirectory() {}

    @Override
    public String toString() {
        return "{" + "size=" + size + ", driveName='" + driveName + '\'' + '}';
    }
}
