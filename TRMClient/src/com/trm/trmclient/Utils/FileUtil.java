package com.trm.trmclient.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

import com.trm.trmclient.MyApplication;

/**
 * Used for save object to file and read it again
 * @author hieu.t.vo
 *
 */

public class FileUtil {
	/**
	 * Save object o to file with fileName
	 * @param o
	 * @param fileName
	 * @return
	 */
	public static boolean saveObject(Object o, String fileName) {
		//TODO: Save object to internal memory
		Context context = MyApplication.getMyApplicationContext();
		try {
			FileOutputStream fileOutputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(o);
		} catch(IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Read object specify by fileName
	 * @param fileName
	 * @return
	 */
	public static Object readObject(String fileName) {
		//TODO: Save object to internal memory
		Context context = MyApplication.getMyApplicationContext();
		try {
			FileInputStream fileInputStream = context.openFileInput(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch(IOException e) {			
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
