package com.cardosotv.quizai.model.services;


import com.cardosotv.quizai.error.NotFoundException;
import com.cardosotv.quizai.model.entities.Subject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cardosotv.quizai.model.repositories.SubjectRepository;


@Service
public class SubjectService {

    @Autowired    
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects(int page, int size) {
        Page<Subject> subjectPage = this.subjectRepository.findAll(PageRequest.of(page, size));
        return subjectPage.getContent();
    }

    public List<Subject> getSubjectsIsActive(){
        return this.subjectRepository.findActivatedSubjects();
    }

    public List<Subject> getSubjectsByName(String name) {
        return this.subjectRepository.findByNameContainingIgnoreCase(name);
    }


    @SuppressWarnings("null")
    public Subject getSubjectByID (UUID subjectID) {
    
        Subject result = subjectRepository.findById(subjectID).orElse(null);
        
        if (Objects.isNull(result)){
            throw new NotFoundException("Subject", subjectID);
        }

        return result;
    }


    public void createSubject(Subject subject){
        subject.setisActive(true);
        subjectRepository.save(subject);
    }  
    
    @SuppressWarnings("null")
    public Subject updateSubject(Subject subject){
         // looking for the subject give as param on database
        Subject subjectDB = this.subjectRepository.findById(subject.getId()).orElse(null);

        // if not found throw the NOT_FOUND exception
        if(Objects.isNull(subjectDB)){
            throw new NotFoundException("Subject", subject);
        }

        // if found, update that register with the subject param informations 
        subjectDB.updateObject(subject);

        // return the updated object with 200 http status code as response
        this.subjectRepository.save(subjectDB);

        return subjectDB;
    }

    @SuppressWarnings("null")    
    public void deleteSubject(UUID subjectID){
        try { 
            // looking for the subject give as param on database
            Subject subjectDB = this.subjectRepository.findById(subjectID).orElse(null);

            // if not found throw the NOT_FOUND exception
            if(Objects.isNull(subjectDB)){
                throw new NotFoundException("Subject", subjectID);
            }
            // execute the delete on database 
            subjectRepository.deleteById(subjectID);
       } catch (Exception e) {
            // return the exception to controller
            throw e;
       }
    }    
}
