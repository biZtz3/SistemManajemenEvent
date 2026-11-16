package com.mycompany.sistemmanajemenevent;

// Custom Exception: dilempar jika jumlah tiket yang dibeli melebihi sisa kuota
public class KuotaTidakCukupException extends Exception {
    public KuotaTidakCukupException(String message) {
        super(message);
    }
}