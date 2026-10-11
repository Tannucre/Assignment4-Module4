
import java.util.HashSet;
import java.util.Scanner;

class Attendance {
    HashSet<String> students = new HashSet<String>();

    void markAttendance(String name) {
        if (students.add(name)) {
            System.out.println("Attendance marked for " + name);
        } else {
            System.out.println(name + " is already marked present");
        }
    }

    void displayAttendance() {
        System.out.println("Present Students: " + students);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Attendance attendance = new Attendance();

        System.out.print("Enter name to mark attendance: ");
        String name1 = sc.nextLine();

        System.out.print("Enter name to mark attendance: ");
        String name2 = sc.nextLine();

        attendance.markAttendance(name1);
        attendance.markAttendance(name2);

        sc.close();
    }
}
