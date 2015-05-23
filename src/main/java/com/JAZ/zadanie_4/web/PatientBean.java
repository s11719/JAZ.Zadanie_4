package com.JAZ.zadanie_4.web;

import com.JAZ.zadanie_4.domain.Patient;
import com.JAZ.zadanie_4.service.PatientRepository;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.inject.Inject;

@SessionScoped
@Named("patientBean")
public class PatientBean implements Serializable {   
    
    private Patient patient = new Patient();
    
    private List<Patient> patientData;

    private List<Patient> filteredData;

    public PatientBean() {
        this.db = new PatientRepository();
    }
        
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public List<Patient> getPatientData(){        
        return db.getList();
    }

    public List<Patient> getFilteredData() {
        return filteredData;
    }

    public void setFilteredData(List<Patient> filteredData) {
        this.filteredData = filteredData;
    }
    
    @Inject
    PatientRepository db;
    
    public String addNewPatient(){
        db.addPatientToList(patient);              
        return "registeredPatient";
    }
    
    public void uniquePesel(FacesContext context, UIComponent component, Object value) {       
        String pesel = (String)value;
        
        for (Patient patient : db.getList()) {           
            if (patient.getPesel().equalsIgnoreCase(pesel)) {
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Nie można dublować numerów PESEL.", null));
            }
        }        
    }
    
    public void dateValidator(FacesContext context, UIComponent component, Object value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        
        Date date = (Date)value;        
        Date today = new Date();
        
        if (date.after(today) || dateFormat.format(date).equals(dateFormat.format(today))){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Podaj właściwą datę urodzenia.", null));
        }        
    }
    
}
        
    


