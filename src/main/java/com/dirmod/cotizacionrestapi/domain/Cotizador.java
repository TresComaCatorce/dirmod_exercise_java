package com.dirmod.cotizacionrestapi.domain;

public class Cotizador
{
	private String currencyName;
	private Double price;

	public void setPrice(Double _price)
	{
		this.price = _price;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setCurrencyName(String _currencyName)
	{
		this.currencyName = _currencyName;
	}

	public String getCurrencyName()
	{
		return currencyName;
	}
}