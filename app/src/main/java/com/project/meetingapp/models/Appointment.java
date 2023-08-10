package com.project.meetingapp.models;

public class Appointment {
    private String patient_name,doctor_name,problem, apoinment_id;

    public Appointment(String patient_name, String doctor_name, String problem,String appoint_id) {
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.problem = problem;
        this.apoinment_id = appoint_id;
    }

    public String getApoinment_id() {
        return apoinment_id;
    }

    public void setApoinment_id(String apoinment_id) {
        this.apoinment_id = apoinment_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
