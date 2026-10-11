
import java.util.ArrayList;
import java.util.Scanner;

class Library {
    ArrayList<String> books = new ArrayList<String>();

    void addBook(String title) {
        books.add(title);
        System.out.println("Book added successfully.");
        displayBooks();
    }

    void removeBook(String title) {
        if (books.remove(title)) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    void displayBooks() {
        System.out.println("Current Books:");
        for (String book : books) {
            System.out.println(book);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();
        int choice;

        do {
            System.out.println("\n1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    lib.addBook(title);
                    break;

                case 2:
                    System.out.print("Enter book title to remove: ");
                    String removeTitle = sc.nextLine();
                    lib.removeBook(removeTitle);
                    break;

                case 3:
                    lib.displayBooks();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }
}
