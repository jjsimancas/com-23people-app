package com.people.app.util;

public class Utils {

    public static boolean rutVerifier(int id) {
        String rut = String.valueOf(id);
        boolean validation = false;
        int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
        char dv = rut.charAt(rut.length() - 1);
        int m = 0, s = 1;
        for (; rutAux != 0; rutAux /= 10) {
            s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
        }
        if (dv == (char) (s != 0 ? s + 47 : 75)) {
            validation = true;
        }
        return validation;
    }
}
