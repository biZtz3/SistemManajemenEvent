package com.mycompany.sistemmanajemenevent;

// ASSOCIATION: class layanan. Peserta dan TiketAcara hanya "bertemu" lewat parameter method
public class LayananPembelian {
    public static void prosesPembelian(Peserta peserta, TiketAcara tiket, int jumlah) throws KuotaTidakCukupException {
        if (jumlah > tiket.getKuota()) {
            throw new KuotaTidakCukupException("Kuota " + tiket.getNama() + " (" + tiket.getKode()
                    + ") tidak cukup. Sisa: " + tiket.getKuota() + ", diminta: " + jumlah);
        }

        tiket.kurangiKuota(jumlah);

        System.out.println("===== STRUK PEMBELIAN TIKET =====");
        System.out.println("Peserta : " + peserta.getNama() + " (" + peserta.getIdPeserta() + ")");
        System.out.println("Tiket   : " + tiket.getNama() + " x" + jumlah);
        // Polymorphism: cara hitung berbeda tergantung jenis tiketnya
        System.out.printf("Total   : Rp%,.0f%n", tiket.hitungTotalHarga(jumlah));
        System.out.println("Status  : Berhasil, sisa kuota " + tiket.getKuota());
        System.out.println("=================================");
    }
}
