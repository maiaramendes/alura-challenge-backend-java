package br.alura.exception;

public class EntityNotFoundException extends RuntimeException {

    private String userName;

    public EntityNotFoundException() {
        super();
        this.userName = null;
    }

    public EntityNotFoundException(final String userName) {
        super();
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        if (null != userName)  {
            return String.format("Usuário {} não encontrado", userName);
        }

        return "Não encontrado";
    }
}
