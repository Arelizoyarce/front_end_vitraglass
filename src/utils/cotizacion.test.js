import test from 'node:test';
import assert from 'node:assert/strict';
import { calcularArea, calcularTotal } from './cotizacion.js';

test('calcula el área en m² de un vidrio válido', () => {
  assert.equal(calcularArea(100, 200), 2);
});

test('rechaza medidas inválidas', () => {
  assert.equal(calcularArea(-5, 100), 0);
});

test('calcula el total de una cotización', () => {
  const items = [{ anchoCm: 100, altoCm: 200, cantidad: 3, precioM2: 50 }];
  assert.equal(calcularTotal(items), 300);
});