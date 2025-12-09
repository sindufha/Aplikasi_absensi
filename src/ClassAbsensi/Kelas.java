package ClassAbsensi;

/**
 * Class Model untuk tabel kelas
 * Merepresentasikan data kelas di database
 */
public class Kelas {
    
    // Attributes
    private int idKelas;
    private int tingkat;
    private String tahunAjaran;
    
    // Constructor kosong
    public Kelas() {
    }
    
    // Constructor dengan parameter
    public Kelas(int idKelas, int tingkat, String tahunAjaran) {
        this.idKelas = idKelas;
        this.tingkat = tingkat;
        this.tahunAjaran = tahunAjaran;
    }
    
    // Constructor tanpa id (untuk insert data baru)
    public Kelas(int tingkat, String tahunAjaran) {
        this.tingkat = tingkat;
        this.tahunAjaran = tahunAjaran;
    }
    
    // Getter dan Setter
    public int getIdKelas() {
        return idKelas;
    }
    
    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }
    
    public int getTingkat() {
        return tingkat;
    }
    
    public void setTingkat(int tingkat) {
        this.tingkat = tingkat;
    }
    
    public String getTahunAjaran() {
        return tahunAjaran;
    }
    
    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }
    
    // Method toString untuk debugging
    @Override
    public String toString() {
        return "Kelas{" +
                "idKelas=" + idKelas +
                ", tingkat=" + tingkat +
                ", tahunAjaran='" + tahunAjaran + '\'' +
                '}';
    }
    
    
    // Method tambahan untuk mendapatkan nama tingkat
    public String getNamaTingkat() {
        return "Kelas " + tingkat;
    }
    
    // Method untuk validasi tingkat (1-6 untuk SD)
    public boolean isValidTingkat() {
        return tingkat >= 1 && tingkat <= 6;
    }
}