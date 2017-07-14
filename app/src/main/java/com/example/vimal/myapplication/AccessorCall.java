package com.example.vimal.myapplication;
import java.lang.reflect.Method;


/**
 * Created by Vimal on 2/21/2017.
 */

public class AccessorCall {


        public void getAccessor(String AccessorName) throws Exception
    {

        String Package="Actors.";
        Class myClass = Class.forName(Package+AccessorName);
        Method[] declaredMethods = myClass.getDeclaredMethods();

        myClass.getClass();
    }
}
