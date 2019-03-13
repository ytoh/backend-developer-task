package cz.databreakers.example.developertask.domain.nearby;

/**
 * Exception indicating a problem when calling the here.com API.
 *
 * @author hvizdos
 */
public class HereException extends RuntimeException {

    public HereException(String message) {
        super(message);
    }

    public HereException(String message, Throwable cause) {
        super(message, cause);
    }
}
