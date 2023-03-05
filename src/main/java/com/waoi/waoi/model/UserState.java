package com.waoi.waoi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@Document(collection = "UserState")
public class UserState {
    @Id
    String id;
    String prevEvtId;
    String currEvtId;
    HashMap<String,String> data ;
    @Indexed @NonNull
    String mobileNumber;
}
