package com.cardosotv.quizai.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


import com.cardosotv.quizai.error.*;
import com.cardosotv.quizai.model.entities.Subject;
import com.cardosotv.quizai.services.SubjectService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {
  
    private final SubjectService subjectService;

    
    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects(@RequestParam(defaultValue = "0") int page){
        List<Subject> subjects = this.subjectService.getAllSubjects(page, 10);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/isactive")
    public ResponseEntity<List<Subject>> getSubjectsIsActive(){
        List<Subject> subjects = this.subjectService.getSubjectsIsActive();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Subject>> getSubjectsByName(@PathVariable String name){
        List<Subject> subjects = this.subjectService.getSubjectsByName(name);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subject> postSubject(@RequestBody @Valid Subject subject){
    //public ResponseEntity<Subject> postSubject(@RequestBody @Valid Subject subject, BindingResult bindingResult){
    
        // if (bindingResult.hasErrors()) {
        //     throw new BadRequestException("Validation error", subject);
        // }
        
        try {
            this.subjectService.createSubject(subject);
            return new ResponseEntity<>(subject, HttpStatus.CREATED);
        } catch (Throwable t) {
            throw HandleException.handleException(t, subject, "Subjects");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubjectByID(@PathVariable UUID id){
        Subject subject;
        try {
            subject = this.subjectService.getSubjectByID(id);
        } catch (Throwable t){
            throw HandleException.handleException(t, id, "Subject");
        }
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping
    public void deleteSubject(@RequestBody Subject subject){
        try {
            this.subjectService.deleteSubject(subject.getId());
        } catch (Throwable t) {
            throw HandleException.handleException(t, subject, "Subject");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateSubject(@RequestBody @Valid Subject subject){

        Subject subjectDB;
        try {
            subjectDB = this.subjectService.updateSubject(subject);
        } catch (Throwable t) {
            throw HandleException.handleException(t, subject, "Subject");
        }
       return ResponseEntity.ok(subjectDB);
    }


}
