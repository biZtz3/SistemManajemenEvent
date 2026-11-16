package com.mycompany.sistemmanajemenevent;

public class TiketEkonomis extends TiketAcara {
    public TiketEkonomis(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getJenis() {
        return "Ekonomis";
    }

    // Ekonomis: Rp50.000 per tiket
    @Override
    public double hitungTotalHarga(int jumlah) {
        return jumlah * 50000;
    }
}