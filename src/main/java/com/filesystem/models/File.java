package com.filesystem.models;

import com.filesystem.util.Log;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class File {

    private StringBuilder content;
    private String fileName;

    public File(String fileName) {
        this.fileName = fileName;
        content = new StringBuilder();
    }

    public File(String content, String fileName) {
        this.content = new StringBuilder(content);
        this.fileName = fileName;
    }

    public void updateContent(String data){
        content.append(" -added- ").append(data);
    }
    public void printContent(){
        Log.info("<File> " + fileName + " <content> " + content);
    }
    public int getFileSize(){
        return content.length();
    }
}
