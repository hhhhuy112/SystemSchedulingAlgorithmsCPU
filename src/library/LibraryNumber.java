/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author Hi
 */
public class LibraryNumber {
    public static int  checkNumber(String num ){
        int a=1;
        try{
            Double.parseDouble(num);
            return a;
        }catch(NumberFormatException ex){
             a=0;
            return a;
        }
        
    }
}
