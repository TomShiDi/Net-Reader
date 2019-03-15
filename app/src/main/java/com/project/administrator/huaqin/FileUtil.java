package com.project.administrator.huaqin;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> fileSearch(String path) {
        return search(path);
    }


    public static List<String> search(String path){

        List<String> fileList = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        String[] filesName = file.list();
        if (filesName.length>0){
            for (int i = 0; i < filesName.length; i++) {
//                if (!(files[i].isDirectory())) {
//                    fileList.add(filesName[i]);
//                }
                fileList.add(filesName[i]);
            }
        }
        return fileList;
    }
}
