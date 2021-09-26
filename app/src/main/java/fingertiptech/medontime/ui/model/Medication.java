package fingertiptech.medontime.ui.model;

import java.util.Calendar;
import java.util.List;

public class Medication {
    String id;
    String caretakerID;
    int patientID;
    String medicationName;
    String methodOfTaking;
    String medicationImage;
    String dosage;
    String medicationType;
    int quantity;
    String firstDoseTime;
    int hoursBetween;
    String frequency;
    String shape;
    List<Calendar> Times;

    public Medication(String id, String caretakerID, int patientID, String medicationName, String methodOfTaking, String medicationImage, String dosage, String medicationType, int quantity, String firstDoseTime, int hoursBetween, String frequency, String shape, List<Calendar> times) {
        this.id = id;
        this.caretakerID = caretakerID;
        this.patientID = patientID;
        this.medicationName = medicationName;
        this.methodOfTaking = methodOfTaking;
        this.medicationImage = medicationImage;
        this.dosage = dosage;
        this.medicationType = medicationType;
        this.quantity = quantity;
        this.firstDoseTime = firstDoseTime;
        this.hoursBetween = hoursBetween;
        this.frequency = frequency;
        this.shape = shape;
        Times = times;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaretakerID() {
        return caretakerID;
    }

    public void setCaretakerID(String caretakerID) {
        this.caretakerID = caretakerID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMethodOfTaking() {
        return methodOfTaking;
    }

    public void setMethodOfTaking(String methodOfTaking) {
        this.methodOfTaking = methodOfTaking;
    }

    public String getMedicationImage() {
        return medicationImage;
    }

    public void setMedicationImage(String medicationImage) {
        this.medicationImage = medicationImage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFirstDoseTime() {
        return firstDoseTime;
    }

    public void setFirstDoseTime(String firstDoseTime) {
        this.firstDoseTime = firstDoseTime;
    }

    public int getHoursBetween() {
        return hoursBetween;
    }

    public void setHoursBetween(int hoursBetween) {
        this.hoursBetween = hoursBetween;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public List<Calendar> getTimes() {
        return Times;
    }

    public void setTimes(List<Calendar> times) {
        Times = times;
    }
}
