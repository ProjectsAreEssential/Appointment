public class Doctor {
   
   // Instance variables
   private int doctorId;
   private String doctorName;
   private String specialty;
   private int maxAppointments; // Max appointments per day
   private int currentAppointments;
   
   // Constructor
   public Doctor(int Id, String name, String specialty, int max, int current) {
      this.doctorId = Id;
      this.doctorName = name;
      this.specialty = specialty;
      this.maxAppointments = max;
      this.currentAppointments = current;
   }
   
   // Gets the Id
   public int getId() {
      return doctorId;
   }
   
   // Gets the name
   public String getName() {
      return doctorName;
   }
   
   // Gets the specialty
   public String getSpecialty() {
      return specialty;
   }
   
   // Gets the max appointments
   public int getMaxAppointments() {
      return maxAppointments;
   }
   
   // Gets the current appointments
   public int getCurrentAppointments() {
      return currentAppointments;
   }
   
   // Sets the Id
   public void setId(int newId) {
      doctorId = newId;
   }
   
   // Sets the name
   public void setName(String newName) {
      doctorName = newName;
   }
   
   // Sets the specialty
   public void setSpecialty(String newSpecialty) {
      specialty = newSpecialty;
   }
   
   // Sets the max appointments
   public void setMaxAppointments(int newMaxAppointments) {
      maxAppointments = newMaxAppointments;
   }
   
   // Sets the current appointments
   public void setCurrentAppointments(int newCurrentAppointments) {
      currentAppointments = newCurrentAppointments;
   }
   
   // Checks if doctor is fully booked
   public boolean isFullyBooked() {
      return currentAppointments >= maxAppointments;
   }
   
   // Prints a string representation
   public String toString() {
      return "Doctor Id: " + doctorId + "\n"
           + "Doctor Name: " + doctorName + "\n"
           + "Specialty: " + specialty + "\n"
           + "Max Appointments: " + maxAppointments + "\n"
           + "Current Appointments: " + currentAppointments;
   }
}