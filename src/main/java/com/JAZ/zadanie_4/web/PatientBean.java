package com.JAZ.zadanie_4.web;

import com.JAZ.zadanie_4.domain.Patient;
import com.JAZ.zadanie_4.service.PatientRepository;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.inject.Inject;

@SessionScoped
@Named("patientBean")
public class PatientBean implements Serializable {  
        
    private Patient patient = new Patient();
        
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
    
    int weightFrom;
    int weightTo;    

    public int getWeightFrom() {
        return weightFrom;
    }
    
    public void setWeightFrom(ValueChangeEvent event) {
        String tempWeight = (String) event.getNewValue();        
        this.weightFrom = Integer.parseInt(tempWeight);
        
        System.out.println(weightFrom);
    }

    public int getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(ValueChangeEvent event) {
        String tempWeight = (String) event.getNewValue();        
        this.weightTo = Integer.parseInt(tempWeight);
        
        System.out.println(weightTo);
    } 
    
    public void uniquePesel(FacesContext context, UIComponent component, Object value) {       
        String pesel = (String)value;
        
        for (Patient newPatient : db.getList()) {           
            if (newPatient.getPesel().equalsIgnoreCase(pesel)) {
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
    
    public void compareBirthdatePesel(ComponentSystemEvent event) {
        try {        
            UIForm form = (UIForm) event.getComponent();
            UIInput UIbirthDate = (UIInput) form.findComponent("birthdate");
            UIInput UIpesel = (UIInput) form.findComponent("pesel");

            String birthdate = UIbirthDate.getValue().toString().substring(27, 29);
            String pesel = UIpesel.getValue().toString().substring(0, 2);

            if (!birthdate.equals(pesel)) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(form.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Data urodzenia jest niezgodna z numerem PESEL.", null));
                context.renderResponse();         
            }
        }
        catch (NullPointerException exception) {
        }
    }
}