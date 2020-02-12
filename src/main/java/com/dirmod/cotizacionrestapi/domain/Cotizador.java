package com.dirmod.cotizacionrestapi.domain;

public class Cotizador
{
	private String moneda;
	private Long precio;

	public Cotizador( String _moneda, Long _precio )
	{
		this.moneda = _moneda;
		this.precio = _precio;
	}

	public void setPrecio(Long _precio)
	{
		this.precio = _precio;
	}

	public Long getPrecio()
	{
		return precio;
	}

	public void setMoneda(String _moneda)
	{
		this.moneda = _moneda;
	}

	public String getMoneda()
	{
		return moneda;
	}
}