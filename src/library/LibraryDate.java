/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;
    
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LibraryDate {
    public static String convertTimeStamp(Timestamp datetime){
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String date=String.valueOf(sdf.format(datetime));
            return date;
    }
    
    public static Timestamp convertTimeStamp(String dateTime){
        Timestamp timeS=Timestamp.valueOf(dateTime);
        return timeS;
    }
    
}
