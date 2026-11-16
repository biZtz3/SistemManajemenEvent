package com.mycompany.sistemmanajemenevent;

public class TiketReguler extends TiketAcara {
    public TiketReguler(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getJenis() {
        return "Reguler";
    }

    // Reguler: Rp150.000 per tiket
    @Override
    public double hitungTotalHarga(int jumlah) {
        return jumlah * 150000;
    }
}