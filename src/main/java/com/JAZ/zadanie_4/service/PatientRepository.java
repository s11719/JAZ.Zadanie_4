package com.JAZ.zadanie_4.service;

import com.JAZ.zadanie_4.domain.Patient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository {
    
    List<Patient> patientRepository = new ArrayList<Patient>();    
    
    public void addPatientToList(Patient patient) {
        Patient newPatient = new Patient();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date = new Date();
       
        newPatient.setDateAdded(dateFormat.format(date));
        newPatient.setName(patient.getName());
        newPatient.setSurname(patient.getSurname());
        newPatient.setPesel(patient.getPesel());
        newPatient.setBirthDate(patient.getBirthDate());
        newPatient.setAddress(patient.getAddress());
        newPatient.setPhoneNumber(patient.getPhoneNumber());
        newPatient.setWeight(patient.getWeight());
        newPatient.setHeight(patient.getHeight());
                
        patientRepository.add(newPatient);
    }
    
    public List<Patient> getList(){
        return patientRepository;
    }
    
}
