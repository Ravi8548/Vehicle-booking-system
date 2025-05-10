
## Overview

The **Vehicle Booking System** is a Java-based console application that allows users to register, log in, and book rides (standard or premium). The system supports functionalities like viewing ride history, rating rides, marking rides as reached, and exporting ride details to a PDF file.

---

## Features

- **User Registration** with input validation (email, phone, password)
- **User Login** with authentication
- **Ride Booking** with fare calculation (based on type and distance)
- **Ride History View** showing all rides of the user
- **Mark Ride as Reached**
- **Rate Ride** (1 to 5 stars)
- **Export Ride History as PDF** using iText PDF library
- **Data Persistence** using text files
- **Modular Code Structure** using OOP (User, Ride classes)

---

## Technologies Used

- Java SE
- File I/O (`BufferedReader`, `BufferedWriter`, `File`)
- Serialization (for objects)
- Console Input (`Scanner`)
- iText PDF Library (for PDF export)

---

## Folder Structure

```
VehicleBookingSystem/
│
├── NewClass2.java              # Main driver program
├── users_data.txt              # Stores user data
├── Ride.java                   # (Integrated in main code)
├── User.java                   # (Integrated in main code)
└── RideHistory.pdf             # Generated PDF (example output)
```

---

## Setup Instructions

### 1. Clone or Download the Project

```
git clone https://github.com/Ravi8548/VehicleBookingSystem.git
```

### 2. Add iText Library

To generate PDF reports, this project uses the [iText PDF](https://mvnrepository.com/artifact/com.itextpdf/itextpdf) library. You must include it in your classpath.

If using **command line**, download the jar and compile with:

```
javac -cp ".;itextpdf-5.5.13.2.jar" NewClass2.java
java -cp ".;itextpdf-5.5.13.2.jar" NewClass2
```

If using **Eclipse/IDEA**, import the iText JAR into your project's build path.

---

## Usage Instructions

1. Run the application.
2. Choose:
   - **Register** as a new user
   - **Login** using existing credentials
3. After login, access the **User Menu** to:
   - Book rides
   - View ride history
   - Rate rides
   - Mark rides as reached
   - Export ride history as PDF
4. Logout or exit

---

## Sample Credentials (for testing)

You can create your own users via the registration menu.

---

## Example Output

### Console

```
--- Rapido Bike Booking System ---
1. Register
2. Login
3. Exit
```

### PDF Output

Sample PDF generated will include:
```
Ride History for user: john_doe
-------------------------------------
Ride Type: premium
From: MG Road
To: Airport
Distance: 34.0 km
Fare: 510.0
Rating: 4
-------------------------------------
```

---

## Limitations

- No GUI; this is a **console-based** application.
- Rides are stored in memory during execution; persistence is only for user credentials.
- No encryption of passwords in storage (can be added for better security).
- PDF generation requires external iText library.

---

## Future Enhancements

- Add GUI using JavaFX or Swing
- Use database (like MySQL) for data persistence
- Add driver management and vehicle allocation
- Improve security (password hashing)
- Real-time fare calculation with maps API 

