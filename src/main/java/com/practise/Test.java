package com.practise;

import java.util.*;


public class Test{
    private String name;
    private String sex;

//    @Override
//    public String toString() {
//        return "Test{" +
//                "name='" + name + '\'' +
//                ", sex='" + sex + '\'' +
//                '}';
//    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static void main(String[] args) {

        Collection coll = new ArrayList();
        Test test = new Test();
        test.setName("aaa");
        test.setSex("bbb");
        System.out.println(test);
//        System.out.println(coll.contains(new Test()));
//        Collection collection = new ArrayList();
//        collection.add("123");
//        collection.add(new Sorts());
//        System.out.println(collection.contains(new  Sorts()));
    }
    public static void change(String s,char[] s1){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
