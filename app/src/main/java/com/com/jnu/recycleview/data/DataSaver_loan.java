package com.com.jnu.recycleview.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import androidx.annotation.NonNull;

public class DataSaver_loan {
    public void Save(Context context, ArrayList<Book> data)//保存数据
    {
        try {
            FileOutputStream dataStream=context.openFileOutput("mydata_loan.dat",Context.MODE_PRIVATE);//指定文件名称，不能包含路径分隔符“/” ，文件不存在会自动创建。
            ObjectOutputStream out = new ObjectOutputStream(dataStream);//序列化
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull//不会返回空
    public ArrayList<Book> Load(Context context)//获取数据
    {
        ArrayList<Book> data=new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("mydata_loan.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);//反序列化
            data = (ArrayList<Book>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
