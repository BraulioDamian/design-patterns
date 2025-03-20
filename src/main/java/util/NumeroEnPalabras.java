package util;

public class NumeroEnPalabras {
    public static String convertir(int numero) {
        if (numero == 0) {
            return "cero";
        }
        if (numero < 0) {
            return "menos " + convertir(-numero);
        }
        String palabras = "";
        if ((numero / 1000000) > 0) {
            palabras += convertir(numero / 1000000) + " millón ";
            numero %= 1000000;
        }
        if ((numero / 1000) > 0) {
            palabras += convertir(numero / 1000) + " mil ";
            numero %= 1000;
        }
        if ((numero / 100) > 0) {
            palabras += convertir(numero / 100) + " cien ";
            numero %= 100;
        }
        if (numero > 0) {
            if (palabras != "") {
                palabras += " ";
            }
            String[] unidades = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
            String[] decenas = {"", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
            String[] especiales = {"diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
            if (numero < 10) {
                palabras += unidades[numero];
            } else if (numero < 20) {
                palabras += especiales[numero - 10];
            } else {
                palabras += decenas[numero / 10];
                if ((numero % 10) > 0) {
                    palabras += " y " + unidades[numero % 10];
                }
            }
        }
        return palabras;
    }
}
