export function calcularArea(anchoCm, altoCm) {
  if (!(anchoCm > 0) || !(altoCm > 0)) return 0;
  return (anchoCm / 100) * (altoCm / 100);
}

export function calcularTotal(items) {
  return items.reduce(
    (total, { anchoCm, altoCm, cantidad, precioM2 }) =>
      total + calcularArea(anchoCm, altoCm) * cantidad * precioM2,
    0
  );
}