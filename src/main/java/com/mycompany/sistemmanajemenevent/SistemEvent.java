package com.mycompany.sistemmanajemenevent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SistemEvent {
    private String namaEvent;

    // Collection Framework: HashMap untuk peserta, ArrayList untuk tiket dan transaksi
    private HashMap<String, Peserta> dataPeserta;
    private ArrayList<TiketAcara> daftarTiket;
    private ArrayList<Transaksi> daftarTransaksi;

    public SistemEvent(String namaEvent) {
        this.namaEvent = namaEvent;
        this.dataPeserta = new HashMap<>();
        this.daftarTiket = new ArrayList<>();
        this.daftarTransaksi = new ArrayList<>();
        System.out.println("=== SISTEM " + namaEvent + " DIBUKA ===");
    }

    public void tambahPeserta(String id, String nama) {
        dataPeserta.put(id, new Peserta(id, nama));
        System.out.println("[PESERTA] " + id + " - " + nama + " terdaftar.");
    }

    public void tambahTiketAcara(String kode, String nama, int kuota) {
        TiketAcara tiket;
        // Jenis tiket ditentukan dari kata di dalam nama (Inheritance)
        if (nama.toLowerCase().contains("premium")) {
            tiket = new TiketPremium(kode, nama, kuota);
        } else if (nama.toLowerCase().contains("reguler")) {
            tiket = new TiketReguler(kode, nama, kuota);
        } else {
            tiket = new TiketEkonomis(kode, nama, kuota);
        }
        daftarTiket.add(tiket);
        tiket.tampilkanInfo();
    }

    // Mencari peserta di HashMap, melempar custom exception jika tidak ada
    private Peserta cariPeserta(String id) throws PesertaTidakDitemukanException {
        if (!dataPeserta.containsKey(id)) {
            throw new PesertaTidakDitemukanException("Peserta dengan ID " + id + " tidak terdaftar.");
        }
        return dataPeserta.get(id);
    }

    // Mencari tiket di ArrayList, melempar custom exception jika tidak ada
    private TiketAcara cariTiket(String kode) throws TiketTidakDitemukanException {
        for (TiketAcara t : daftarTiket) {
            if (t.getKode().equals(kode)) {
                return t;
            }
        }
        throw new TiketTidakDitemukanException("Tiket dengan kode " + kode + " tidak ditemukan.");
    }

    // Mengambil nilai setelah tanda '>' pada potongan seperti "ID>ACT-01"
    private String ambilNilai(String potongan) {
        String[] pasangan = potongan.split(">");
        return pasangan[1];
    }

    // Format baris (Varian 0): ID>ACT-01.KD>OBJ-A.JML>3.
    public void jalankanTransaksiDariFile(String namaFile) throws FileNotFoundException {
        File fileInput = new File(namaFile);
        Scanner mesinBaca = new Scanner(fileInput);

        int nomor = 0;
        int berhasil = 0;
        int gagal = 0;

        while (mesinBaca.hasNextLine()) {
            String baris = mesinBaca.nextLine().trim();
            // Baris kosong tidak diproses
            if (!baris.isEmpty()) {
                nomor++;
                System.out.println("\nTransaksi ke-" + nomor + ": " + baris);

                try {
                    // Pecah baris menjadi 3 bagian dengan pemisah titik: ID, KD, JML
                    String[] bagian = baris.split("\\.");
                    String idPeserta = ambilNilai(bagian[0]);
                    String kodeTiket = ambilNilai(bagian[1]);
                    int jumlah = Integer.parseInt(ambilNilai(bagian[2]));

                    if (jumlah <= 0) {
                        throw new Exception("Jumlah harus lebih dari 0");
                    }

                    Peserta peserta = cariPeserta(idPeserta);
                    TiketAcara tiket = cariTiket(kodeTiket);

                    LayananPembelian.prosesPembelian(peserta, tiket, jumlah);

                    // Transaksi berhasil, dicatat ke ArrayList
                    daftarTransaksi.add(new Transaksi(idPeserta, kodeTiket, jumlah, tiket.hitungTotalHarga(jumlah)));
                    berhasil++;

                } catch (PesertaTidakDitemukanException e) {
                    System.out.println("[TRANSAKSI GAGAL] " + e.getMessage());
                    gagal++;
                } catch (TiketTidakDitemukanException e) {
                    System.out.println("[TRANSAKSI GAGAL] " + e.getMessage());
                    gagal++;
                } catch (KuotaTidakCukupException e) {
                    System.out.println("[TRANSAKSI GAGAL] " + e.getMessage());
                    gagal++;
                } catch (Exception e) {
                    System.out.println("[FORMAT ERROR] Baris tidak dapat diproses: " + e.getMessage());
                    gagal++;
                }
            }
        }
        mesinBaca.close();

        System.out.println("\n--- PEMBACAAN FILE SELESAI: " + berhasil + " berhasil, " + gagal + " gagal ---");
    }

    public void simpanLaporanAkhir() {
        System.out.println("\n--- MENYIMPAN LAPORAN AKHIR ---");

        // 1. OBJECT SERIALIZATION ke dump_sys.dat (digit ketiga = 6)
        try {
            FileOutputStream fileKeluar = new FileOutputStream("dump_sys.dat");
            ObjectOutputStream mesinPembeku = new ObjectOutputStream(fileKeluar);

            mesinPembeku.writeObject(this.dataPeserta);
            mesinPembeku.writeObject(this.daftarTiket);
            mesinPembeku.writeObject(this.daftarTransaksi);

            mesinPembeku.close();
            System.out.println("[SERIALIZATION] Status memori berhasil disimpan ke dump_sys.dat");
        } catch (Exception e) {
            System.out.println("[SERIALIZATION ERROR] Gagal menyimpan data: " + e.getMessage());
        }

        // 2. FILE I/O TEKS ke laporan.txt, format tag ala XML
        try {
            FileWriter pipaTulis = new FileWriter("laporan.txt");

            System.out.println("[LAPORAN] Isi laporan.txt (Laporan Berformat Tag):");
            for (int i = 0; i < daftarTransaksi.size(); i++) {
                Transaksi t = daftarTransaksi.get(i);
                String baris = "<aktor>" + t.getIdPeserta() + "</aktor><qty>" + t.getQty() + "</qty>";

                pipaTulis.write(baris + "\n");
                System.out.println("  " + baris);
            }

            pipaTulis.close();
            System.out.println("[LAPORAN] laporan.txt berhasil ditulis (" + daftarTransaksi.size() + " baris).");
        } catch (IOException e) {
            System.out.println("[LAPORAN ERROR] Gagal menulis laporan.txt: " + e.getMessage());
        }
    }
}