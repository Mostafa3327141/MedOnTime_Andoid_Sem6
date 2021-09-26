package fingertiptech.medontime.ui.model;

import java.util.List;
import org.bson.types.ObjectId;

public class Patient {
    String id;
    int patientID;
    int caretakerID;
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNum;
    int age;
    List<Shape> unSelectedShapes;
    List<String> medicationIDs;
    List<String> prescriptionIDs;

    public Patient(String id, int patientID, int caretakerID, String firstName, String lastName, String email, String password, String phoneNum, int age, List<Shape> unSelectedShapes, List<String> medicationIDs, List<String> prescriptionIDs) {
        this.id = id;
        this.patientID = patientID;
        this.caretakerID = caretakerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.age = age;
        this.unSelectedShapes = unSelectedShapes;
        this.medicationIDs = medicationIDs;
        this.prescriptionIDs = prescriptionIDs;
    }


    // for Add patient no need _id

    public Patient(int patientID, int caretakerID, String firstName, String lastName, String email, String password, String phoneNum, int age, List<Shape> unSelectedShapes, List<String> medicationIDs, List<String> prescriptionIDs) {
        this.patientID = patientID;
        this.caretakerID = caretakerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.age = age;
        this.unSelectedShapes = unSelectedShapes;
        this.medicationIDs = medicationIDs;
        this.prescriptionIDs = prescriptionIDs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getCaretakerID() {
        return caretakerID;
    }

    public void setCaretakerID(int caretakerID) {
        this.caretakerID = caretakerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Shape> getUnSelectedShapes() {
        return unSelectedShapes;
    }

    public void setUnSelectedShapes(List<Shape> unSelectedShapes) {
        this.unSelectedShapes = unSelectedShapes;
    }

    public List<String> getMedicationIDs() {
        return medicationIDs;
    }

    public void setMedicationIDs(List<String> medicationIDs) {
        this.medicationIDs = medicationIDs;
    }

    public List<String> getPrescriptionIDs() {
        return prescriptionIDs;
    }

    public void setPrescriptionIDs(List<String> prescriptionIDs) {
        this.prescriptionIDs = prescriptionIDs;
    }
}