package com.people.app.service.api;

import org.springframework.data.domain.Page;

import java.util.List;

public interface PersistenseService {
    Page<?> getAllDataByPage(int tag);
    List<?> ListAllData(int tag);
    Object getDataById(int id, int tag);
    <T> int createRecord(T req) throws Exception;
    <T> void updateRecord(int id, T req) throws Exception;
    void deleteRecord(int id, int tag) throws Exception;
}
