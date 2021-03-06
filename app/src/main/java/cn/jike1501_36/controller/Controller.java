package cn.jike1501_36.controller;

import android.content.Context;
import cn.jike1501_36.model.Contact;
import cn.jike1501_36.model.DBAdapter;

/**
 * Created by DoonZhang on 2018/6/19.
 */
public class Controller {
    private static DBAdapter dbAdapter;
    Context context;

    /**
     * 创建构造方法，并打开数据库
     * @param context
     */
    public Controller(Context context) {
        this.context = context;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
    }


    //添加
    public void add(Contact contact) {
        dbAdapter.insert(contact);
    }

    //查询所有联系人
    public Contact[] getAllContact() {
        return dbAdapter.getAll();
    }

    //查找联系人
    public Contact[] getContact(int id) {
        return dbAdapter.getContact(id);
    }

    //删除联系人
    public void delete(int id) {
        dbAdapter.delete(id);
        dbAdapter.close();
    }

    //更新联系人
    public void update(int id, Contact contact) {
        dbAdapter.update(id, contact);
        dbAdapter.close();
    }

}
