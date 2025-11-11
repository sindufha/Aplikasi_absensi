package ClassAbsensi;

public class Siswa {
    private int idSiswa;
    private String nis;
    private String namaSiswa;
    private int idKelas;
    private String jenisKelamin;
    private String qrCode;

    public Siswa() {
    }

    public Siswa(int idSiswa, String nis, String namaSiswa, int idKelas, String jenisKelamin, String qrCode) {
        this.idSiswa = idSiswa;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.idKelas = idKelas;
        this.jenisKelamin = jenisKelamin;
        this.qrCode = qrCode;
    }

    public Siswa(String nis, String namaSiswa, int idKelas, String jenisKelamin, String qrCode) {
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.idKelas = idKelas;
        this.jenisKelamin = jenisKelamin;
        this.qrCode = qrCode;
    }

    public int getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(int idSiswa) {
        this.idSiswa = idSiswa;
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

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "Siswa{" +
                "idSiswa=" + idSiswa +
                ", nis='" + nis + '\'' +
                ", namaSiswa='" + namaSiswa + '\'' +
                ", idKelas=" + idKelas +
                ", jenisKelamin='" + jenisKelamin + '\'' +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}