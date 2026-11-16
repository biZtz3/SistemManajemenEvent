package com.mycompany.sistemmanajemenevent;

// Custom Exception: dilempar jika ID peserta tidak terdaftar
public class PesertaTidakDitemukanException extends Exception {
    public PesertaTidakDitemukanException(String message) {
        super(message);
    }
}