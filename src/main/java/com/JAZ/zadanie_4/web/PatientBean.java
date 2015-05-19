package com.JAZ.zadanie_4.web;

import com.JAZ.zadanie_4.domain.Patient;
import com.JAZ.zadanie_4.service.PatientRepository;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.inject.Inject;

@SessionScoped
@Named("patientBean")
public class PatientBean  implements Serializable {
    
    private Patient patient = new Patient();
    
    private ListDataModel<Patient> patientData = new ListDataModel<Patient>();

    public PatientBean() {
        this.db = new PatientRepository();
    }
        
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public ListDataModel<Patient> getPatientData(){
        patientData.setWrappedData(db.getList());
        return patientData;
    }
    
    @Inject
    PatientRepository db;
    
    public String addNewPatient(){
        db.addPatientToList(patient);              
        return "listPatient";
    }
    
}

