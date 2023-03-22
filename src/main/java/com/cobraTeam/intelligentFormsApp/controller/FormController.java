package com.cobraTeam.intelligentFormsApp.controller;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import com.cobraTeam.intelligentFormsApp.entity.Form;
import com.cobraTeam.intelligentFormsApp.repository.FormRepository;
import com.cobraTeam.intelligentFormsApp.service.FormRecognizerService;
import com.cobraTeam.intelligentFormsApp.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/forms")
@CrossOrigin(origins = "http://localhost:4200")
public class FormController {

    @Autowired
    private FormService formService;

    @Autowired
    private FormRecognizerService formRecognizerService;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private CosmosTemplate cosmosTemplate;

    @PostMapping("/create-form")
    public Mono<ResponseEntity<Form>> saveForm(@RequestBody Form form) {
        return formService.saveForm(form)
                .map(savedForm -> ResponseEntity.ok().body(savedForm))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/getAllForms")
    public ResponseEntity<List<Form>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    @PostMapping("/identity-documents")
    public ResponseEntity<AnalyzeResult> recognizeIdentityDocument(@RequestParam("documentUrl") String documentUrl) {
        AnalyzeResult documentFields = formRecognizerService.readDocument(documentUrl);
        return ResponseEntity.ok(documentFields);
    }

    @GetMapping("/findById")
    public ResponseEntity<Form> findById(@RequestParam String id) throws Exception {
        return ResponseEntity.ok(formService.findById(id).orElseThrow(() -> new Exception("Product with id <" + id + "> not found")));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mono<Form>> updateForm(
            @RequestBody Form form,
            @PathVariable("id") String id) {

        Optional<Form> formData = formService.findById(id);

        if (formData.isPresent()) {
            Form.DynamicFields dynamicFieldsToBeChanged = new Form.DynamicFields();
            Form formToBeChanged = formData.get();
            formToBeChanged.setTitle(form.getTitle());
            dynamicFieldsToBeChanged.setLabel(dynamicFieldsToBeChanged.getLabel());
            dynamicFieldsToBeChanged.setPlaceholder(dynamicFieldsToBeChanged.getPlaceholder());
            dynamicFieldsToBeChanged.setMandatory(dynamicFieldsToBeChanged.isMandatory());
            dynamicFieldsToBeChanged.setFieldType(dynamicFieldsToBeChanged.getFieldType());
            return new ResponseEntity<>(formService.saveForm(formToBeChanged), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Form> delete(@RequestParam String id) {
        try {
            formService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
