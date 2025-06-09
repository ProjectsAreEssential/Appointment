public class Patient {
   
   // Instance variables
   private int patientId;
   private String name;
   private int[] appointments = new int[3]; // Max of 3 appointments per patient
   private int appointmentCount; // Tracks the patients appointment count
   
   // Constructor
   public Patient(int Id, String name) {
      this.patientId = Id;
      this.name = name;
   }
   
   // Gets the Id
   public int getId() {
      return patientId;
   }
   
   // Gets the name
   public String getName() {
      return name;
   }
   
   // Gets the appointments
   public int[] getAppointments() {
      return appointments;
   }
   
   // Gets the appointment count
   public int getAppointmentCount() {
      return appointmentCount;
   }
   
   // Sets the Id
   public void setId(int newId) {
      patientId = newId;
   }
   
   // Sets the name
   public void setName(String newName) {
      name = newName;
   }
   
   // Sets the appointments
   public void setAppointments(int[] newAppointments) {
      appointments = newAppointments;
   }
   
   // Sets the appointment count
   public void setAppointmentCount(int newAppointmentCount) {
      appointmentCount = newAppointmentCount;
   }
   
   // Books an appointment
   public boolean bookAppointment(int doctorId) {
      if (appointmentCount >= 3) return false; // There is a limit of 3 appointments per person
      
      for (int i = 0; i < appointments.length; i++) {
         if (appointments[i] == doctorId) { // Checks for duplicates
            return false; // Already booked
         } else if (appointments[i] == 0) { // Checks for the first available slot
            appointments[i] = doctorId;
            appointmentCount++; // Increment appointment count
            return true; // Success
         }
      }
      return false; // No slot available
   }
   
   // Cancels an appointment
   public boolean cancelAppointment(int doctorId) {
      for (int i = 0; i < appointments.length; i++) {
         if (appointments[i] == doctorId) { // Checks for the appointment
            appointments[i] = 0; // Makes the slot available
            appointmentCount--; // Decrement appointment count
            return true; // Success
         }
      }
      return false; // No appointment found
   }
   
   // Checks if the patient has an appointment with a doctor
   public boolean hasAppointment(int doctorId) {
      for (int i = 0; i < appointments.length; i++) {
         if (appointments[i] == doctorId) {
            return true; // Found the doctorId in the appointments
         }
      }
      return false; // DoctorId not found in appointments
   }
   
   // Prints a string representation
   public String toString() {
      String temp = ""; // Initialize a temp variable
      for (int i = 0; i < appointments.length; i++) {
         if (appointments[i] != 0) { // Checks to see which appointments are booked
            temp += appointments[i] + " ";
         }
      }
      
      return "Patient Id: " + patientId + "\n"
           + "Patient Name: " + name + "\n"
           + "Appointments: " + temp + "\n"
           + "Appointment Count: " + appointmentCount;
   }
}