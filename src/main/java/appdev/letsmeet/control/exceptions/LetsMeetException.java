/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.exceptions;

/**
 *
 * @author HA999
 */
public class LetsMeetException extends Exception {

    /**
     * Creates a new instance of <code>LetsMeetException</code> without detail
     * message.
     */
    public LetsMeetException() {
    }

    /**
     * Constructs an instance of <code>LetsMeetException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LetsMeetException(String msg) {
        super(msg);
    }
}
