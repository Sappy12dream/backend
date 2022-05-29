package com.sapna.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sapna.project.repository.PatientRepository;
import com.sapna.project.exception.ResourceNotFound;
import com.sapna.project.model.Patient;

@RestController
@RequestMapping("/api/v1/")
public class PatientController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping("/patients")
	public List<Patient> getAllPatient(){
		return patientRepository.findAll();
	}
	
	@GetMapping("/patient")
	public List<Patient> getFilteredPatients(@RequestParam boolean status){
		return patientRepository.findByStatusEquals(status);
	}
	
	@PostMapping("/patient/new")
	public Patient createPatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}
	
	@GetMapping("/patient/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable long id) {
		Patient patient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Patient with id: "+id+" not found"));
		return 	ResponseEntity.ok(patient);
	}
	
	@PutMapping("/patient/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient patientDetails) {
		Patient patient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Patient with id: "+id+" not found"));
		
		patient.setStatus(patientDetails.getStatus());
		Patient updatedPatient = patientRepository.save(patient);
		return 	ResponseEntity.ok(updatedPatient);
	}
	
	@DeleteMapping("/patient/{id}")
		public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable long id){
			Patient patient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Patient with id: "+id+" not found"));
			patientRepository.delete(patient);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
	
}
