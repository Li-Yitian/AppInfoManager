package cn.sp.appinfo.tool;


import java.util.UUID;

public class UUIDTool {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
