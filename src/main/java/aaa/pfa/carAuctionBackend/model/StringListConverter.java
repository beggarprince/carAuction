package aaa.pfa.carAuctionBackend.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class StringListConverter implements AttributeConverter<List<String>, String> {


    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if(strings == null || strings.isEmpty()){
            return "[]";
        }

        try{
            return mapper.writeValueAsString(strings);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException("Could not convert list of strings to JSON");
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {

        if(s == null || s.isBlank()){
            return Collections.emptyList();
        }

        try{
            return mapper.readValue(s, new TypeReference<List<String>>(){});
        }catch(IOException e){
            throw new IllegalArgumentException("Could not convert JSON to list", e);
        }

    }
}
