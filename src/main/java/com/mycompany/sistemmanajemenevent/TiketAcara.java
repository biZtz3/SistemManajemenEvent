package com.mycompany.sistemmanajemenevent;

import java.io.Serializable;

// Superclass abstrak. Subclass: TiketPremium, TiketReguler, TiketEkonomis
// Implements Serializable di sini, jadi semua subclass otomatis ikut bisa disimpan
public abstract class TiketAcara implements Serializable {
    private static final long serialVersionUID = 1L;

    // Encapsulation
    private String kode;
    private String nama;
    private int kuota;

    public TiketAcara(String kode, String nama, int kuota) {
        this.kode = kode;
        this.nama = nama;
        this.kuota = kuota;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public int getKuota() { return kuota; }

    public void kurangiKuota(int jumlah) {
        this.kuota = this.kuota - jumlah;
    }

    public void tampilkanInfo() {
        System.out.println("[" + getJenis() + "] " + kode + " | " + nama + " | Sisa kuota: " + kuota);
    }

    // Abstract method: wajib di-override oleh setiap subclass (Polymorphism)
    public abstract String getJenis();
    public abstract double hitungTotalHarga(int jumlah);
}