import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

//
//Create a class named Employees with these variables:
//String name
//int age
//String favoriteFood

//Your goal: 
//Write an application that lets users add need new employees with the associated fields. 
//Users should also be able to see a list of all employees and retrieve data regarding their 
//age and/or favorite food if the employee is selected from the list. 

public class CodeChallenge {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Employees> staff = new ArrayList<Employees>();
		String letter = "y";

		createDirectory("Reference");
		createFile("Reference", "Employees.txt");
		writeToFile("Reference", "Employees.txt");
		staff = readFromFile("Reference", "Employees.txt");
		
//		System.out.println(staff);
		int i = 0;
		while (letter.equalsIgnoreCase("y")) {
			for (i = 0; i < staff.size(); i++) {
				Employees person = staff.get(i);
				System.out.println(i + 1 + ". " + person.getName());
			}
			System.out.println("Which employee would you like to learn more about?:");
			int choice = scan.nextInt();
			System.out.println("Name: " + staff.get(choice - 1).getName());
			System.out.println("Age:" + staff.get(choice - 1).getAge());
			System.out.println("Favorite Food: "+ staff.get(choice - 1).getFavoriteFood());
			letter = Validator.getString(scan, "Would you like to learn about another staff member?(Y/N)");
		}
		System.out.println("Thank you and goodbye.");
		
			
		
		
		
		

	}// end of main

	public static void createDirectory(String dirString) {
		Path dirPath = Paths.get(dirString);

		if (Files.notExists(dirPath)) {
			try {
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out
						.println("Im not sure what happened. Contact customer service.");
			}
		}
	}

	public static void createFile(String dirString, String fileString) {
		// if you don't want this to go into a folder just use the get method
		// taking in one parameter
		// for the file name
		// use the overloaded get method if you want to add a file inside of a
		// folder
		Path filePath = Paths.get(dirString, fileString);

		if (Files.notExists(filePath)) {
			try {
				Files.createFile(filePath);
				System.out.println("Your file was created successfully");
			} catch (IOException e) {
				System.out.println("Somthing went wrong with file creation");
				e.printStackTrace();
			}

		}

	}

	public static void writeToFile(String dirString, String fileString) {
		// Employees jordan = new Employees("jordan", 25, "Pizza");
		// Employees vince = new Employees("vince",26,"Tacos");
		Employees newEmploy;

		int yesNo;

		Path writeFile = Paths.get(dirString, fileString);

		File file = writeFile.toFile();// the toFile() converts the path to a
										// file object

		try {
			PrintWriter printOut = new PrintWriter(new FileOutputStream(file,
					true));// this will appended the files each time


			do {
				Scanner scan = new Scanner(System.in);
				System.out.println("Enter new Employee? Yes(1) or No(2)");
				yesNo = scan.nextInt();
				if (yesNo == 1) {
					System.out.println("Enter name:");
					String name = scan.next();
					System.out.println("Enter age:");
					int age = scan.nextInt();
					System.out.println("Enter Favorite food:");
					String favFood = scan.next();
					newEmploy = new Employees(name, age, favFood);
					printOut.println(newEmploy);
					System.out.println("Enter new Employee? Yes(1) or No(2)");
					yesNo = scan.nextInt();
				}
			} while (yesNo == 1);
			printOut.close();// closing flushes out data and closes the
								// object(PrintWriter)

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	public static ArrayList<Employees> readFromFile(String dirString, String filePath) {
		Path readFile = Paths.get("Reference/Employees.txt");// the hard coded changed to use the method parameters
		ArrayList<Employees> staff = new ArrayList<Employees>();														// value can
		

		File file = readFile.toFile();

		try {
			FileReader fr = new FileReader(file);
			// the benefit of using the buffer is to help us store a block of
			// memory that we can go back to a read from later
			// this is more efficient than the scanner
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			while (line != null) {
				//System.out.println(line);
				String split[] = line.split(",");
				staff.add(new Employees(split[0], Integer.parseInt(split[1]), split[2]));
				line = reader.readLine();
				
			}
			reader.close();// this flushes the buffer and closes it

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Somthing went wrong with this");
			e.printStackTrace();
		}
		return staff; 
	}

}// end