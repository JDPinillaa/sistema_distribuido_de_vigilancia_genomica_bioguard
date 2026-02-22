package enums;

public enum Gender {
    Masculino('M'),
    Femenino('F');

    Gender(char sigla) {
        this.sigla = sigla;
    }

    private char sigla;

    public char getSigla() {
        return sigla;
    }
}
