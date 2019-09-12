package com.threeox.utillibrary.util.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.threeox.utillibrary.util.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: ContactUtils
 *
 * @Description: TODO(通讯录工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2018/1/23 15:48
 *
 * @version 1.0
 *
 */
public class ContactUtils {

    /** 
     * 获取联系人数据 
     *  
     * @param context 
     * @return 
     */  
    public static List<ContactInfo> getAllContacts(Context context) {
        List<ContactInfo> list = new ArrayList<>();
        // 获取解析者  
        ContentResolver resolver = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动光标
        while (cursor.moveToNext()) {
            try {
                ContactInfo info = new ContactInfo();
                //取得联系人名字
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                String contact = cursor.getString(nameFieldColumnIndex);
                //取得电话号码
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                String phoneNumber = "";
                while (phoneCursor.moveToNext()) {
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //格式化手机号
                    number = number.replace("-", "");
                    number = number.replace(" ", "");
                    phoneNumber += number + ",";
                }
                if (EmptyUtils.isNotEmpty(phoneNumber) && phoneNumber.endsWith(",")) {
                    phoneNumber = phoneNumber.substring(0, phoneNumber.toString().length() - 1);
                }
                info.setId(contactId);
                info.setName(contact);
                info.setPhone(phoneNumber);
                list.add(info);
                if (phoneCursor != null) {
                    phoneCursor.close();
                }
            } catch (Exception e) {
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getSimContact("content://icc/adn", list, context);
        getSimContact("content://sim/adn", list, context);
        return list;  
    }

    /**
     * 得到SIM卡的通讯录
     *
     * @param adn
     * @param list
     * @param context
     */
    private static void getSimContact(String adn, List<ContactInfo> list, Context context) {
        // 读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
        Cursor cursor = null;
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(adn));
            Uri uri = intent.getData();
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ContactInfo info = new ContactInfo();
                    // 取得联系人名字
                    int nameIndex = cursor.getColumnIndex("name");
                    // 取得电话号码
                    int numberIndex = cursor.getColumnIndex("number");
                    info.setName(cursor.getString(nameIndex));
                    info.setPhone(cursor.getString(numberIndex));
                    list.add(info);
                }
                cursor.close();
            }
        } catch (Exception e) {
        }
        if (cursor != null)
            cursor.close();
    }

}  