package com.android.ejsoft.video_upload_000web.dbManage;

/**
 * Created by E J on 9/25/2017.
 */

public class VS_ReturnValues {
    private  int id;
    private String fileName;
    private Boolean flag;

    public VS_ReturnValues(int id, String fileName, Boolean flag){
        this.id = id;
        this.fileName = fileName;
        this.flag = flag;
    }
//
//    public void setFileName(String newFileName){
//        fileName = newFileName;
//    }
//    public void setFlag(Boolean newFlag){
//        flag = newFlag;
//    }


    public int getId(){return id;}
    public String getFileName(){
        return fileName;
    }
    public Boolean getFlag(){
         return flag;
    }


}
