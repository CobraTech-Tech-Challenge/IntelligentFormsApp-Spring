package com.cobraTeam.intelligentFormsApp.service;

import com.cobraTeam.intelligentFormsApp.entity.Form;
import com.cobraTeam.intelligentFormsApp.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public Mono<Form> saveForm(Form form) {
        return formRepository.save(form);
    }

    public List<Form> getAllForms() {
        return (List<Form>) formRepository.findAll().collectList().block();
    }

    public Optional<Form> findById(String id) {
        return formRepository.findById(id).blockOptional();
    }

    public void delete(String id) throws Exception {
        Optional<Form> form = formRepository.findById(id).blockOptional();

        if (form.isPresent()) {
            formRepository.deleteById(id);
        } else {
            throw new Exception("Product with id " + id + " not found.");
        }
    }


}