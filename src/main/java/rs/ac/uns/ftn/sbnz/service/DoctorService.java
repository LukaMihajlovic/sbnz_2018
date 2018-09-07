package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Doctor;
import rs.ac.uns.ftn.sbnz.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Doctor.
 */
@Service
@Transactional
public class DoctorService {

    private final Logger log = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Save a doctor.
     *
     * @param doctor the entity to save
     * @return the persisted entity
     */
    public Doctor save(Doctor doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    /**
     * Get all the doctors.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll();
    }

    /**
     * Get one doctor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Doctor findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findOne(id);
    }

    /**
     * Delete the doctor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.delete(id);
    }
}
