package com.filesystem;

import com.filesystem.models.Drive;
import com.filesystem.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {

    private final Map<String, Drive> drives = new HashMap<>();
    // for simplicity, we will assume size in bytes, each file size = content.length();
    public static final int HARD_DISK_SIZE = 1000;
    private int spaceLeft = HARD_DISK_SIZE;

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.setUpDrives(fileSystem);
        fileSystem.createFiles(fileSystem);

    }

    private void readFile(String filePath){

    }

    private void createFiles(FileSystem fileSystem) {
        createFile("f1.txt", "c", "dir1", "some text");
        createFile("f2.txt", "c", "dir1");
        createFile("f3.txt", "c", "dir2/first", "some text");
    }

    private void setUpDrives(FileSystem fileSystem) {
        fileSystem.createDrive("c", 500);
        fileSystem.createDrive("d", 200);
        fileSystem.createDrive("e", 500);
        fileSystem.createDrive("e", 300);
    }

    private void createFile(String fileName, String drive, String path) {
        createFile(fileName, drive, path, "");
    }
    private void createFile(String fileName, String drive, String path, String content) {
        drives.get(drive).createFile(fileName, path, content);
    }

    public void createDrive(String driveName, int emptyDriveSize) {
        if (spaceLeft < emptyDriveSize) {
            Log.error("Can't create drive " + driveName + "; space left: " + spaceLeft + ", requested space " + emptyDriveSize);
            return;
        }
        Drive drive = new Drive(emptyDriveSize, driveName);
        drives.put(drive.getDriveName(), drive);
        spaceLeft = spaceLeft - emptyDriveSize;
        Log.value("Created drive " + drive);
    }

    public void deleteDrive(String driveName) {
        Log.warn("Deleting drive " + drives.get(driveName));
        Drive deleted = drives.remove(driveName);
        spaceLeft = spaceLeft + deleted.getSize();
    }

    public void formatDrive(String driveName) {

    }
}
