package com.JAZ.zadanie_4.web;

import com.JAZ.zadanie_4.domain.Patient;
import com.JAZ.zadanie_4.service.PatientRepository;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
@Named("patientBean")
public class PatientBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
    
    public void uniquePesel(FacesContext context, UIComponent component, Object value) {       
        String pesel = (String)value;
        System.out.println(pesel);
        
        for (Patient patient : db.getList()) {           
            if (patient.getPesel().equalsIgnoreCase(pesel)) {
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie można dublować numerów PESEL.", null));
            }
        }        
    }
    
}
        
    


