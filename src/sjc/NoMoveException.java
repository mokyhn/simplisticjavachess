package sjc;

public class NoMoveException extends Exception {
    public String err;	

    public NoMoveException() {         
    }

    
    public NoMoveException(String e) {
            err = e;
	}

}
