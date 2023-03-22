package com.cobraTeam.intelligentFormsApp.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.*;

import javax.persistence.Id;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Container(containerName = "Form", ru = "400")
public class Form {

    @Id
    @GeneratedValue
    private String id;

    @PartitionKey
    private String title;

    private List<DynamicFields> fields;


    @Setter
    @Getter
    public static class DynamicFields {
        private String label;

        private String placeholder;

        private boolean isMandatory;

        private String fieldType;

    }

//    public enum FieldType {
//        text, singleChoice
//    }
}
