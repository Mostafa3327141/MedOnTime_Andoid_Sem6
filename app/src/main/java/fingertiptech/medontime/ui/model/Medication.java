package fingertiptech.medontime.ui.model;

import java.util.List;

public class Medication {
    String id;
    String caretakerID;
    int patientID;
    String medicationName;
    String medicationImage;
    String unit;
    int quantity;
    String condition;
    String firstDoseTime;
    int hoursBetween;
    String frequency;
    String shape;
    List<String> times;

    // this constructor is for retrive from db
    public Medication(String id, String caretakerID, int patientID, String medicationName, String medicationImage, String unit, int quantity, String condition, String firstDoseTime, int hoursBetween, String frequency, String shape, List<String> times) {
        this.id = id;
        this.caretakerID = caretakerID;
        this.patientID = patientID;
        this.medicationName = medicationName;
        this.medicationImage = medicationImage;
        this.unit = unit;
        this.quantity = quantity;
        this.condition = condition;
        this.firstDoseTime = firstDoseTime;
        this.hoursBetween = hoursBetween;
        this.frequency = frequency;
        this.shape = shape;
        this.times = times;
    }
    // this constructor is for add to db
    public Medication(String caretakerID, int patientID, String medicationName, String medicationImage, String unit, int quantity, String condition, String firstDoseTime, int hoursBetween, String frequency, String shape, List<String> times) {
        this.caretakerID = caretakerID;
        this.patientID = patientID;
        this.medicationName = medicationName;
        this.medicationImage = medicationImage;
        this.unit = unit;
        this.quantity = quantity;
        this.condition = condition;
        this.firstDoseTime = firstDoseTime;
        this.hoursBetween = hoursBetween;
        this.frequency = frequency;
        this.shape = shape;
        this.times = times;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    public String getMedicationImage() {
        return medicationImage;
    }

    public void setMedicationImage(String medicationImage) {
        this.medicationImage = medicationImage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}

