//package com.icfcc.credit.platform.session;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//import com.icfcc.credit.platform.jpa.entity.system.SystemButton;
// 
//public class SerializeUtils {
// 
//    public static byte[] serialize(Object o) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream outo = new ObjectOutputStream(out);
//            outo.writeObject(o);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
// 
//        return out.toByteArray();
//    }
// 
//    public static Object deserialize(byte[] b) {
//        ObjectInputStream oin;
//        try {
//            oin = new ObjectInputStream(new ByteArrayInputStream(b));
//            try {
//                return oin.readObject();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//                return null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
// 
//    }
//    
//    public static void main(String[] args) {
//    	 
//    	SystemButton s =new SystemButton();
//    	s.setButtonCode("asdfsdf");
//    	byte[] a = serialize(s);
//    	System.out.println(a);
//    	Object o = deserialize(a);
//    	if(o instanceof SystemButton) {
//    		System.out.print( o);
//    	}
//    	
//    }
//    
//}
//
