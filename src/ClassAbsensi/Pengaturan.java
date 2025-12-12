package ClassAbsensi;

public class Pengaturan {
    private int id;
    private String namaSekolah;
    private String alamatSekolah;
    private String tahunAjaranAktif;
    private String kepalaSekolah;
    
    // Constructor kosong
    public Pengaturan() {
    }
    
    // Constructor lengkap
    public Pengaturan(int id, String namaSekolah, String alamatSekolah, 
                      String tahunAjaranAktif, String kepalaSekolah) {
        this.id = id;
        this.namaSekolah = namaSekolah;
        this.alamatSekolah = alamatSekolah;
        this.tahunAjaranAktif = tahunAjaranAktif;
        this.kepalaSekolah = kepalaSekolah;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNamaSekolah() {
        return namaSekolah;
    }
    
    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }
    
    public String getAlamatSekolah() {
        return alamatSekolah;
    }
    
    public void setAlamatSekolah(String alamatSekolah) {
        this.alamatSekolah = alamatSekolah;
    }
    
    public String getTahunAjaranAktif() {
        return tahunAjaranAktif;
    }
    
    public void setTahunAjaranAktif(String tahunAjaranAktif) {
        this.tahunAjaranAktif = tahunAjaranAktif;
    }
    
    public String getKepalaSekolah() {
        return kepalaSekolah;
    }
    
    public void setKepalaSekolah(String kepalaSekolah) {
        this.kepalaSekolah = kepalaSekolah;
    }
    
    @Override
    public String toString() {
        return "Pengaturan{" +
                "id=" + id +
                ", namaSekolah='" + namaSekolah + '\'' +
                ", alamatSekolah='" + alamatSekolah + '\'' +
                ", tahunAjaranAktif='" + tahunAjaranAktif + '\'' +
                ", kepalaSekolah='" + kepalaSekolah + '\'' +
                '}';
    }
}