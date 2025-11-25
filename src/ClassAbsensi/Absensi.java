package ClassAbsensi;

import java.sql.Date;
import java.sql.Time;

public class Absensi {
    private int idAbsensi;
    private int idSiswa;
    private Date tanggal;
    private Time waktuMasuk;
    private Time waktuPulang;
    private String status; // Hadir, Telat, Izin, Sakit, Alfa
    private String metode; // QR, Manual
    private String keterangan;
    
    // Data tambahan dari join (untuk tampilan)
    private String nis;
    private String namaSiswa;
    private String namaKelas;
    
    // Constructor kosong
    public Absensi() {
    }
    
    // Constructor untuk insert
    public Absensi(int idSiswa, Date tanggal, Time waktuMasuk, String status, String metode) {
        this.idSiswa = idSiswa;
        this.tanggal = tanggal;
        this.waktuMasuk = waktuMasuk;
        this.status = status;
        this.metode = metode;
    }
    
    // Constructor lengkap
    public Absensi(int idAbsensi, int idSiswa, Date tanggal, Time waktuMasuk, 
                   Time waktuPulang, String status, String metode, String keterangan) {
        this.idAbsensi = idAbsensi;
        this.idSiswa = idSiswa;
        this.tanggal = tanggal;
        this.waktuMasuk = waktuMasuk;
        this.waktuPulang = waktuPulang;
        this.status = status;
        this.metode = metode;
        this.keterangan = keterangan;
    }
    
    // Getters dan Setters
    public int getIdAbsensi() {
        return idAbsensi;
    }
    
    public void setIdAbsensi(int idAbsensi) {
        this.idAbsensi = idAbsensi;
    }
    
    public int getIdSiswa() {
        return idSiswa;
    }
    
    public void setIdSiswa(int idSiswa) {
        this.idSiswa = idSiswa;
    }
    
    public Date getTanggal() {
        return tanggal;
    }
    
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
    public Time getWaktuMasuk() {
        return waktuMasuk;
    }
    
    public void setWaktuMasuk(Time waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }
    
    public Time getWaktuPulang() {
        return waktuPulang;
    }
    
    public void setWaktuPulang(Time waktuPulang) {
        this.waktuPulang = waktuPulang;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMetode() {
        return metode;
    }
    
    public void setMetode(String metode) {
        this.metode = metode;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public String getNis() {
        return nis;
    }
    
    public void setNis(String nis) {
        this.nis = nis;
    }
    
    public String getNamaSiswa() {
        return namaSiswa;
    }
    
    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }
    
    public String getNamaKelas() {
        return namaKelas;
    }
    
    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }
    
    @Override
    public String toString() {
        return "Absensi{" +
                "idAbsensi=" + idAbsensi +
                ", idSiswa=" + idSiswa +
                ", tanggal=" + tanggal +
                ", waktuMasuk=" + waktuMasuk +
                ", waktuPulang=" + waktuPulang +
                ", status='" + status + '\'' +
                ", metode='" + metode + '\'' +
                '}';
    }
}