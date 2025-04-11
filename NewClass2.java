import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

class User implements Serializable {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private ArrayList<Ride> rideHistory = new ArrayList<>();

    public User(String username, String password, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Ride> getRideHistory() {
        return rideHistory;
    }

    public void addRide(Ride ride) {
        rideHistory.add(ride);
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Phone: " + phoneNumber + ", Email: " + email + "\nRide History: "
                + rideHistory;
    }
}

class Ride implements Serializable {
    private String rideType;
    private String pickupLocation;
    private String dropLocation;
    private double distance;
    private boolean isReached;
    private int rating;
    private double fare;

    public Ride(String rideType, String pickupLocation, String dropLocation, double distance) {
        this.rideType = rideType;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.isReached = false;
        this.rating = -1; // No rating initially
        calculateFare(); // Calculate price based on distance
    }

    public void markAsReached() {
        isReached = true;
    }

    public void rateRide(int rating) {
        if (this.rating != -1) {
            System.out.println("This ride has already been rated.");
        } else if (rating >= 1 && rating <= 5) {
            this.rating = rating;
            System.out.println("Thank you for rating the ride with " + rating + " stars!");
        } else {
            System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
        }
    }

    private void calculateFare() {
        if (rideType.equalsIgnoreCase("standard")) {
            fare = distance * 10; // Rs. 10 per km for standard
        } else if (rideType.equalsIgnoreCase("premium")) {
            fare = distance * 15; // Rs. 15 per km for premium
        } else {
            fare = 0;
        }
    }

    public String getRideDetails() {
        return String.format("Type: %s, From: %s, To: %s, Distance: %.2f km, Price: %.2f, Rating: %d",
                rideType, pickupLocation, dropLocation, distance, fare, rating);
    }

    @Override
    public String toString() {
        return "Ride Type: " + rideType + ", From: " + pickupLocation + " to " + dropLocation +
                ", Distance: " + distance + " km, Price: " + fare + ", Rating: " + rating;
    }

    public boolean isReached() {
        return isReached;
    }

    public double getPrice() {
        return fare;
    }
}

public class NewClass2 {
    private static final String FILE_NAME = "users_data.txt"; // File name for storing user data
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        loadData(); // Load user data at the start of the program

        while (true) {
            System.out.println("\n--- Rapido Bike Booking System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    saveData(); // Save data before exiting
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create an empty file
                System.out.println("File not found. A new file has been created.");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(";");
                    if (userData.length >= 4) {
                        String username = userData[0];
                        String password = userData[1];
                        String phoneNumber = userData[2];
                        String email = userData[3];
                        User user = new User(username, password, phoneNumber, email);
                        users.add(user);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        }
    }

    private static void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getEmail()
                        + user.getUsername() + ";" + user.getPassword() + ";" + user.getPhone() + ";");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("User already registered with this username. Please choose a different username.");
                return;
            }
        }

        String password = getValidPassword();
        String phoneNumber = getValidPhoneNumber();
        String email = getValidEmail();

        User newUser = new User(username, password, phoneNumber, email);
        users.add(newUser);
        saveData();
        System.out.println("Registration successful!");
    }

    private static String getValidPhoneNumber() {
        String phoneNumber;
        while (true) {
            System.out.print("Enter phone number: ");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches("\\d{10}")) {
                return phoneNumber;
            } else {
                System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");
            }
        }
    }

    private static String getValidEmail() {
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            String emailPattern = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (email.matches(emailPattern)) {
                return email;
            } else {
                System.out.println("Invalid email. Please enter a valid email address.");
            }
        }
    }

    private static String getValidPassword() {
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        loggedInUser = null;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser != null) {
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Book a Ride");
            System.out.println("2. View Ride History");
            System.out.println("3. Mark Ride as Reached");
            System.out.println("4. Rate a Ride");
            System.out.println("5. Generate Ride History PDF");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookRide();
                    break;
                case 2:
                    viewRideHistory();
                    break;
                case 3:
                    markRideAsReached();
                    break;
                case 4:
                    rateRide();
                    break;
                case 5:
                    generatePdf(); // Generate PDF
                    break;
                case 6:
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void bookRide() {
        System.out.print("Enter ride type(standarad/premium): ");
        String rideType = scanner.nextLine();
        System.out.print("Enter pickup location: ");
        String pickupLocation = scanner.nextLine();
        System.out.print("Enter drop location: ");
        String dropLocation = scanner.nextLine();
        System.out.print("Enter distance (in km): ");
        double distance = scanner.nextDouble();
        scanner.nextLine();

        Ride newRide = new Ride(rideType, pickupLocation, dropLocation, distance);
        loggedInUser.addRide(newRide);

        System.out.println("Ride booked successfully! Price: " + newRide.getPrice());
    }

    private static void viewRideHistory() {
        if (loggedInUser.getRideHistory().isEmpty()) {
            System.out.println("No rides yet.");
        } else {
            for (Ride ride : loggedInUser.getRideHistory()) {
                System.out.println(ride.getRideDetails());
            }
        }
    }

    private static void markRideAsReached() {
        System.out.print("Enter ride index to mark as reached: ");
        int rideIndex = scanner.nextInt();
        scanner.nextLine();

        if (rideIndex >= 0 && rideIndex < loggedInUser.getRideHistory().size()) {
            Ride ride = loggedInUser.getRideHistory().get(rideIndex);
            if (!ride.isReached()) {
                ride.markAsReached();
                System.out.println("Ride marked as reached.");
            } else {
                System.out.println("This ride is already marked as reached.");
            }
        } else {
            System.out.println("Invalid ride index.");
        }
    }

    private static void rateRide() {
        System.out.print("Enter ride index to rate: ");
        int rideIndex = scanner.nextInt();
        scanner.nextLine();

        if (rideIndex >= 0 && rideIndex < loggedInUser.getRideHistory().size()) {
            System.out.print("Enter rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            loggedInUser.getRideHistory().get(rideIndex).rateRide(rating);
        } else {
            System.out.println("Invalid ride index.");
        }
    }

    // Method to generate PDF
    private static void generatePdf() {
        try {
            // Create a new document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("RideHistory.pdf"));

            document.open();
            document.add(new Paragraph("User Ride History\n"));
            document.add(new Paragraph("=====================================\n"));

            // Add user ride details to PDF
            if (loggedInUser.getRideHistory().isEmpty()) {
                document.add(new Paragraph("No ride history available."));
            } else {
                for (Ride ride : loggedInUser.getRideHistory()) {
                    document.add(new Paragraph(ride.getRideDetails()));
                }
            }

            document.close();
            System.out.println("PDF generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewFileData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}