package com.gqueiroz.repository;

import java.sql.Date;

/**
 * Created by gabrielqueiroz on 2/28/16.
 */
public class History {
    private int id;
    private String tipo;
    private String referencia;
    private String item;
    private double valor;
    private double balance;
    private Date data;

    public History(){

    }

    public History(int id, String tipo, String referencia, String item, double valor, double balance, Date data) {
        this.id = id;
        this.tipo = tipo;
        this.referencia = referencia;
        this.item = item;
        this.valor = valor;
        this.balance = balance;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
