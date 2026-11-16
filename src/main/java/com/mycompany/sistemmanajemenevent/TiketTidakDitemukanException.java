package com.mycompany.sistemmanajemenevent;

// Custom Exception: dilempar jika kode tiket tidak terdaftar
public class TiketTidakDitemukanException extends Exception {
    public TiketTidakDitemukanException(String message) {
        super(message);
    }
}