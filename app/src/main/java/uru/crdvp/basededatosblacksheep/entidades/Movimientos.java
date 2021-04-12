package uru.crdvp.basededatosblacksheep.entidades;

public class Movimientos {

    private Integer idMovimiento;
    private Integer idPerfil;
    private Integer monto;
    private Integer IngEgr; /*Ingreso o Egreso*/
    private Integer idCaja;
    private String descripcion;

    public Movimientos(Integer idMovimiento, Integer idPerfil, Integer monto, Integer ingEgr, Integer idCaja, String descripcion) {
        this.idMovimiento = idMovimiento;
        this.idPerfil = idPerfil;
        this.monto = monto;
        IngEgr = ingEgr;
        this.idCaja = idCaja;
        this.descripcion = descripcion;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Integer getIngEgr() {
        return IngEgr;
    }

    public void setIngEgr(Integer ingEgr) {
        IngEgr = ingEgr;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
