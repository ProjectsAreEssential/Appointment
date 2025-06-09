import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AppointmentManager {
   
   public static void main(String[] args) {
      try {
         Scanner console = new Scanner(System.in);
         
         // Initiialize arrays using data from file
         Doctor[] doctors = AppointmentManagerModule.readDoctors("doctors.txt");
         Patient[] patients = AppointmentManagerModule.readPatients("patients.txt"); 
         
         // Launch the menu-driven interface
         AppointmentManagerModule.menu(console, doctors, patients); 
         
         console.close(); // Close the console scanner
      } catch (FileNotFoundException e){
         System.out.println("Error reading file: " + e);
      }
   }
}