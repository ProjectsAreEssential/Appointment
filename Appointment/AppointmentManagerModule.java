import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class AppointmentManagerModule {
   
   // Reads doctors.txt and populates a Doctor[] object
   public static Doctor[] readDoctors(String filename) throws FileNotFoundException {
      Scanner input = new Scanner(new File(filename));
      
      Doctor[] doctors = new Doctor[100]; // Max of 100 doctors
      
      int count = 0; // Represents the doctor count
      while (input.hasNextLine()) {
         String line = input.nextLine();
         Scanner lineScanner = new Scanner(line);
         
         // Splits the tokens at a comma now instead of a space
         lineScanner.useDelimiter(",");
         
         int doctorId = lineScanner.nextInt();
         String doctorName = lineScanner.next().trim();
         String specialty = lineScanner.next().trim();
         int maxAppointments = lineScanner.nextInt();
         int currentAppointments = lineScanner.nextInt();
         
         lineScanner.close(); // Close the line scanner
         
         // With each doctor create a new doctor object while assigning it at index count while iterating
         doctors[count++] = new Doctor(doctorId, doctorName, specialty, maxAppointments, currentAppointments);
      }
      
      input.close(); // Close the input scanner
      return doctors; // Return the array with all the doctor objects
   }
   
   // Reads patients.txt and populates a Patient[] object
   public static Patient[] readPatients(String filename) throws FileNotFoundException {
      Scanner input = new Scanner(new File(filename));
      
      Patient[] patients = new Patient[100]; // Max of 100 patients
      
      int count = 0; // Represents the patient count
      while (input.hasNextLine()) {
         String line = input.nextLine();
         Scanner lineScanner = new Scanner(line);
         
         // Splits the tokens at a comma now instead of a space
         lineScanner.useDelimiter(",");
         
         int patientId = lineScanner.nextInt();
         String patientName = lineScanner.next().trim();
         
         // With each patient create a new patient object while assigning it at index count while iterating
         patients[count++] = new Patient(patientId, patientName);
         
         // Read remaining tokens as doctor Id's
         while (lineScanner.hasNextInt()) {
            int doctorId = lineScanner.nextInt();
            
            // Store the appointments into the appointments array
            patients[count - 1].bookAppointment(doctorId);
         }
         lineScanner.close(); // Close the line scanner
      }
      
      input.close(); // Close the input scanner
      return patients; // Return the array with the patient objects
   }

   // Prints a interactive menu and handles its functionality
   public static void menu(Scanner console, Doctor[] doctors, Patient[] patients) {
      while (true) { // Menu showing all the options
         System.out.println("-----Appointments Menu-----");
         
         System.out.println(); // Blank line for formatting
         
         System.out.println("1. View all doctors");
         System.out.println("2. Search for a doctor by specialty");
         System.out.println("3. Book an appointment");
         System.out.println("4. Cancel an appointment");
         System.out.println("5. View all patients and their appointments");
         System.out.println("6. Add a new patient");
         System.out.println("7. Add a new doctor");
         System.out.println("8. Save all changes and Exit"); // Saves changes inside an output file ("out.txt")
         
         System.out.println(); // Blank line for formatting
         
         System.out.print("Enter your choice: "); // Prompts the user to enter a number
         
         int choice = console.nextInt();
         
         console.nextLine(); // Consume new line
         
         switch (choice) {
            case 1:
               viewAllDoctors(doctors);
               break;
            case 2:
               searchDoctorBySpecialty(console, doctors);
               break;
            case 3:
               bookAppointment(console, doctors, patients);
               break;
            case 4:
               cancelAppointment(console, doctors, patients);
               break;
            case 5:
               viewAllPatients(patients);
               break;
            case 6:
               addPatient(console, patients);
               break;
            case 7:
               addDoctor(console, doctors);
               break;
            case 8:
               try {
                  saveAllChanges(doctors, patients);
                  System.out.println("Changes saved successfully.");
               } catch (FileNotFoundException e) {
                  System.out.println("Error saving changes: " + e);
               }
               return;
            default:
               System.out.println("Invalid choice. Try again."); // Handles invalid input
         }
         
         System.out.println(); // Space after each action
      }
   }
   
   // Displays all doctors in the system
   public static void viewAllDoctors(Doctor[] doctors) {
      for (int i = 0; i < doctors.length; i++) {
         if (doctors[i] != null) { // Checks for non-null elements
            System.out.println(doctors[i]);
         }
      }
   }
   
   // Prompts the user to enter a specialty and displays matching doctors
   public static void searchDoctorBySpecialty(Scanner console, Doctor[] doctors) {
      System.out.print("What is the Doctor's specialty? "); // Prompt the user
      String specialty = console.nextLine();
      
      boolean found = false; // Represents if doctors were found
      for (int i = 0; i < doctors.length; i++) {
         if (doctors[i] != null) { // Checks for non-null elements
            if (specialty.equalsIgnoreCase(doctors[i].getSpecialty())) {
               System.out.println(doctors[i]);
               found = true; // Doctor(s) were found
            }
         }
      }
      
      if (!found) { // Checks to see if doctors were found
         System.out.println("No doctors found with that specialty.");
      }
   }
      
   // Allows a patient to book an appointment with a doctor
   public static void bookAppointment(Scanner console, Doctor[] doctors, Patient[] patients) {
      System.out.print("What is your patient Id? "); // Prompt the user
      int patientId = console.nextInt();
      
      for (int i = 0; i < patients.length; i++) {
         if (patients[i] != null) { // Checks for non-null elements
            if (patients[i].getId() == patientId) { // Checks if the patient exists
               System.out.print("What is the doctor's Id? "); // Prompt the user
               int doctorId = console.nextInt();
               
               for (int j = 0; j < doctors.length; j++) {
                  if (doctors[j] != null) { // Checks for non-null elements
                     if (doctors[j].getId() == doctorId) { // Checks if the doctor id exists
                        if (!doctors[j].isFullyBooked()) { // Checks if the doctor is fully booked
                           patients[i].bookAppointment(doctorId); // If not booked the patient with the doctor
                           doctors[j].setCurrentAppointments(doctors[j].getCurrentAppointments() + 1);
                           System.out.println("Appointment successfully booked.");
                           return; // Exit after booking
                        } else {
                           System.out.println("This doctor is fully booked.");
                           return; // Exit after not being able to book
                        }
                     }
                  }
               }
            }
         }
      }
   }
   
   // Allows a patient to cancel a booked appointment
   public static void cancelAppointment(Scanner console, Doctor[] doctors, Patient[] patients) {
      System.out.print("What is your patient Id? "); // Prompt the user
      int patientId = console.nextInt();
      
      console.nextLine(); // Consume new line
      
      for (int i = 0; i < patients.length; i++) {
         if (patients[i] != null) { // Checks for non-null elements
            if (patients[i].getId() == patientId) { // Checks if the patient exists
               
               System.out.print("What is the doctor's Id? "); // Prompt the user
               int doctorId = console.nextInt();

               for (int j = 0; j < doctors.length; j++) {
                  if (doctors[j] != null) { // Checks for non-null elements
                     if (doctors[j].getId() == doctorId) { // Checks if the doctor id exists 

                        // Checks to see if the patient has an appointment with the doctor
                        if (patients[i].hasAppointment(doctorId)) {
                           patients[i].cancelAppointment(doctorId); // Remove the appointment
                           doctors[j].setCurrentAppointments(doctors[j].getCurrentAppointments() - 1);
                           System.out.println("Appointment canceled.");
                        } else {
                           System.out.println("No appointment with that doctor.");
                        }
                     }  
                  }
               }
            }
         }
      }
   }
   
   // Displays all patients along with their booked appointments
   public static void viewAllPatients(Patient[] patients) {
      for (int i = 0; i < patients.length; i++) {
         if (patients[i] != null) { // Checks for non-null elements
            System.out.println(patients[i]);

            // Get patient appointments array
            int[] appointments = patients[i].getAppointments();
            
            // Checks to see if the patient has any appointments
            if (appointments == null || appointments.length == 0) { 
               System.out.println("No appointments booked.");
            } else {
               for (int j = 0; j < appointments.length; j++) { // Gets the appointments
                  System.out.println("Appointments with Doctor Id: " + appointments[j]);
               }
            }
         }
      }
   }
   
   // Adds a new patient to the patient array
   public static void addPatient(Scanner console, Patient[] patients) {
      System.out.print("What is your patient Id? "); // Prompt the user
      int patientId = console.nextInt();
      
      console.nextLine(); // Consume new line
      
      System.out.print("What is your name? "); // Prompt the user
      String name = console.nextLine();
      
      for (int i = 0; i < patients.length; i++) {
         if (patients[i] == null) { // Find the first null spot
            patients[i] = new Patient(patientId, name); // Create a new patient object
            return; // Success adding patient
         } 
      }
      System.out.println("No more patients can be added.");
   }
   
   // Adds a new doctor to the doctor array
   public static void addDoctor(Scanner console, Doctor[] doctors) {
      System.out.print("What is your doctor's Id? "); // Prompt the user
      int doctorId = console.nextInt();
      
      console.nextLine(); // Consume new line
      
      System.out.print("What is your name? "); // Prompt the user
      String name = console.nextLine();
      
      System.out.print("What is your specialty? "); // Prompt the user
      String specialty = console.nextLine();
      
      System.out.print("What is your max appointments? "); // Prompt the user
      int maxAppt = console.nextInt();
      
      console.nextLine(); // Consume new line
      
      System.out.print("What is your current appointments? "); // Prompt the user
      int currAppt = console.nextInt();
      
      console.nextLine(); // Consume new line
      
      for (int i = 0; i < doctors.length; i++) {
         if (doctors[i] == null) { // Find the first null spot
            doctors[i] = new Doctor(doctorId, name, specialty, maxAppt, currAppt); // Create a new doctor object
            return; // Success adding doctor
         } 
      }
      System.out.println("No more doctors can be added.");
   }
   
   // Saves all doctor and patient information to output files cleanly formatted
   public static void saveAllChanges(Doctor[] doctors, Patient[] patients) throws FileNotFoundException {
       PrintStream doctorOut = new PrintStream(new File("doctors_out.txt")); // Create a PrintStream object
       
       for (int i = 0; i < doctors.length; i++) {
           if (doctors[i] != null) {
               // Print doctor info nicely formatted
               doctorOut.printf("%d,%s,%s,%d,%d%n",
                   doctors[i].getId(),
                   doctors[i].getName(),
                   doctors[i].getSpecialty(),
                   doctors[i].getMaxAppointments(),
                   doctors[i].getCurrentAppointments());
           }
       }
       doctorOut.close(); // Close the doctorOut stream
       
       PrintStream patientOut = new PrintStream(new File("patients_out.txt")); // Create a PrintStream object
       for (int i = 0; i < patients.length; i++) {
           if (patients[i] != null) {
               // Print patient info nicely formatted
               patientOut.printf("%d,%s", patients[i].getId(), patients[i].getName());
               
               // Print patient appointments if any
               int[] appointments = patients[i].getAppointments();
               if (appointments != null) {
                   for (int j = 0; j < appointments.length; j++) {
                       patientOut.printf(",%d", appointments[j]);
                   }
               }
               patientOut.println(); // New line after each patient
           }
       }
       patientOut.close(); // Close the patientOut stream
   }
}