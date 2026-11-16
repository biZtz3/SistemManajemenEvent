package com.mycompany.sistemmanajemenevent;

import java.io.Serializable;

// Entitas pendukung (Aktor). WAJIB implements Serializable agar bisa disimpan ke file
public class Peserta implements Serializable {
    private static final long serialVersionUID = 1L;

    // Encapsulation: field private, diakses lewat getter
    private String idPeserta;
    private String nama;

    public Peserta(String idPeserta, String nama) {
        this.idPeserta = idPeserta;
        this.nama = nama;
    }

    public String getIdPeserta() { return idPeserta; }
    public String getNama() { return nama; }
}