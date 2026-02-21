package enums;

public enum Genero {
    Masculino('M'),
    Femenino('F');

    Genero(char sigla) {
        this.sigla = sigla;
    }

    private char sigla;

    public char getSigla() {
        return sigla;
    }
}
