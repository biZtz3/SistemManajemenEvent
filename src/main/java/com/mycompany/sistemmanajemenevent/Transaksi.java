package com.mycompany.sistemmanajemenevent;

import java.io.Serializable;

// Catatan satu transaksi yang BERHASIL. Disimpan di ArrayList dan ikut diserialisasi
public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idPeserta;
    private String kodeTiket;
    private int qty;
    private double totalHarga;

    public Transaksi(String idPeserta, String kodeTiket, int qty, double totalHarga) {
        this.idPeserta = idPeserta;
        this.kodeTiket = kodeTiket;
        this.qty = qty;
        this.totalHarga = totalHarga;
    }

    public String getIdPeserta() { return idPeserta; }
    public String getKodeTiket() { return kodeTiket; }
    public int getQty() { return qty; }
    public double getTotalHarga() { return totalHarga; }
}
