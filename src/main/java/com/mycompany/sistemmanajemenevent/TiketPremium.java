package com.mycompany.sistemmanajemenevent;

public class TiketPremium extends TiketAcara {
    public TiketPremium(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getJenis() {
        return "Premium";
    }

    // Premium: Rp750.000 per tiket + biaya lounge VIP Rp50.000
    @Override
    public double hitungTotalHarga(int jumlah) {
        return jumlah * 750000 + 50000;
    }
}