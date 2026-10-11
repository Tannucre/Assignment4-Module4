import java.awt.*;
import java.awt.event.*;

// Custom Exception for non-numeric or invalid age inputs
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class StudentRegistrationForm extends Frame implements ActionListener {
    private TextField tfName, tfFatherName, tfAge;
    private CheckboxGroup cbgGender;
    private Checkbox cbMale, cbFemale;
    private Choice choiceCourse;
    private Checkbox cbDrawing, cbSinging, cbMusic, cbOthers;
    private TextArea taAddress;
    private Button btnSave, btnClear;

    // Cyan/Teal color scheme matching the document illustration
    private final Color primaryTeal = new Color(0, 150, 165);
    private final Color darkTeal = new Color(0, 120, 135);
    private final Color softBg = new Color(240, 248, 250);

    public StudentRegistrationForm() {
        super("User Registration Form");
        setSize(460, 680);
        setLayout(new BorderLayout());
        setBackground(softBg);

        // Header Title
        Panel headerPanel = new Panel();
        headerPanel.setBackground(primaryTeal);
        Label lblHeader = new Label("Registration Form");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);
        add(headerPanel, BorderLayout.NORTH);

        // Form Fields Container
        Panel formPanel = new Panel();
        formPanel.setLayout(new GridLayout(8, 1, 6, 6));
        formPanel.setBackground(softBg);

        // 1. Name
        tfName = new TextField(22);
        formPanel.add(createFieldRow("Name", tfName));

        // 2. Father Name
        tfFatherName = new TextField(22);
        formPanel.add(createFieldRow("Father Name", tfFatherName));

        // 3. Age
        tfAge = new TextField(22);
        formPanel.add(createFieldRow("Age", tfAge));

        // 4. Gender
        Panel genderPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        genderPanel.setBackground(softBg);
        cbgGender = new CheckboxGroup();
        cbMale = new Checkbox("Male", cbgGender, true);
        cbFemale = new Checkbox("Female", cbgGender, false);
        genderPanel.add(cbMale);
        genderPanel.add(cbFemale);
        formPanel.add(createFieldRow("Gender", genderPanel));

        // 5. Course
        choiceCourse = new Choice();
        choiceCourse.add("-- Select Course --");
        choiceCourse.add("Java");
        choiceCourse.add("Python");
        choiceCourse.add("C++");
        choiceCourse.add("Data Structures");
        formPanel.add(createFieldRow("Course", choiceCourse));

        // 6. Hobbies
        Panel hobbiesPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        hobbiesPanel.setBackground(softBg);
        cbDrawing = new Checkbox("Drawing");
        cbSinging = new Checkbox("Singing");
        cbMusic = new Checkbox("Music");
        cbOthers = new Checkbox("Others");
        hobbiesPanel.add(cbDrawing);
        hobbiesPanel.add(cbSinging);
        hobbiesPanel.add(cbMusic);
        hobbiesPanel.add(cbOthers);
        formPanel.add(createFieldRow("Hobbies", hobbiesPanel));

        // 7. Address
        taAddress = new TextArea(3, 22);
        formPanel.add(createFieldRow("Address", taAddress));

        // 8. Action Buttons
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setBackground(softBg);
        btnSave = new Button("Save Details");
        btnClear = new Button("Clear All");

        btnSave.setBackground(darkTeal);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 13));

        btnClear.setBackground(new Color(180, 50, 50));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Arial", Font.BOLD, 13));

        btnSave.addActionListener(this);
        btnClear.addActionListener(this);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.CENTER);

        // Window Closing Handler
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Panel createFieldRow(String labelText, Component comp) {
        Panel row = new Panel(new FlowLayout(FlowLayout.LEFT, 15, 6));
        row.setBackground(softBg);
        Label label = new Label(labelText);
        label.setPreferredSize(new Dimension(100, 22));
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(30, 40, 50));
        row.add(label);
        row.add(comp);
        return row;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            String name = tfName.getText().trim();
            String fatherName = tfFatherName.getText().trim();
            String ageStr = tfAge.getText().trim();
            String address = taAddress.getText().trim();
            String selectedCourse = choiceCourse.getSelectedItem();

            // Validation: Empty text fields
            if (name.isEmpty() || fatherName.isEmpty() || ageStr.isEmpty() || address.isEmpty()) {
                showPopUp("Validation Error", "All fields are required. Please fill every option.", true);
                return;
            }

            // Exception Handling: Age must be strictly digits/numbers
            int age;
            try {
                // Throws NumberFormatException if letters, decimals, or special characters are entered
                age = Integer.parseInt(ageStr);

                // Throws custom InvalidAgeException if the number is out of reasonable range
                if (age <= 0 || age > 120) {
                    throw new InvalidAgeException("Age must be a valid number between 1 and 120.");
                }
            } catch (NumberFormatException ex) {
                showPopUp("Invalid Age Exception", "Exception: Age should only contain numbers/digits!\nAlphabets and symbols are not allowed.", true);
                tfAge.requestFocus();
                return;
            } catch (InvalidAgeException ex) {
                showPopUp("Invalid Age Exception", "Exception: " + ex.getMessage(), true);
                tfAge.requestFocus();
                return;
            }

            // Validation: Course dropdown selection
            if (choiceCourse.getSelectedIndex() == 0) {
                showPopUp("Validation Error", "Please select a valid Course from the list.", true);
                return;
            }

            // Collect selected hobbies
            StringBuilder hobbies = new StringBuilder();
            if (cbDrawing.getState()) hobbies.append("Drawing ");
            if (cbSinging.getState()) hobbies.append("Singing ");
            if (cbMusic.getState()) hobbies.append("Music ");
            if (cbOthers.getState()) hobbies.append("Others ");

            if (hobbies.length() == 0) {
                hobbies.append("None");
            }

            // Registration details display dialog
            String successMessage = "Student Registration Successful!\n\n"
                    + "Name: " + name + "\n"
                    + "Father Name: " + fatherName + "\n"
                    + "Age: " + age + "\n"
                    + "Gender: " + cbgGender.getSelectedCheckbox().getLabel() + "\n"
                    + "Course: " + selectedCourse + "\n"
                    + "Hobbies: " + hobbies.toString().trim() + "\n"
                    + "Address: " + address;

            showPopUp("Success", successMessage, false);

        } else if (e.getSource() == btnClear) {
            tfName.setText("");
            tfFatherName.setText("");
            tfAge.setText("");
            taAddress.setText("");
            choiceCourse.select(0);
            cbgGender.setSelectedCheckbox(cbMale);
            cbDrawing.setState(false);
            cbSinging.setState(false);
            cbMusic.setState(false);
            cbOthers.setState(false);
        }
    }

    private void showPopUp(String title, String message, boolean isError) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(380, 270);
        dialog.setBackground(softBg);
        dialog.setLocationRelativeTo(this);

        TextArea msgArea = new TextArea(message, 8, 36, TextArea.SCROLLBARS_VERTICAL_ONLY);
        msgArea.setEditable(false);
        msgArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        if (isError) {
            msgArea.setForeground(new Color(180, 0, 0));
        }

        Button okBtn = new Button("OK");
        okBtn.setBackground(primaryTeal);
        okBtn.setForeground(Color.WHITE);
        okBtn.setFont(new Font("Arial", Font.BOLD, 12));
        okBtn.addActionListener(ev -> dialog.dispose());

        Panel bottom = new Panel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(softBg);
        bottom.add(okBtn);

        dialog.add(msgArea, BorderLayout.CENTER);
        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new StudentRegistrationForm();
    }
}