package com.dirmod.cotizacionrestapi.service;

import com.dirmod.cotizacionrestapi.domain.Cotizador;

public interface CotizadorService
{
	Cotizador getCotizacion( Double price );
}