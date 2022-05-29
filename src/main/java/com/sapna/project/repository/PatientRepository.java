package com.sapna.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapna.project.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	public List<Patient> findByStatusEquals(boolean status);
}
