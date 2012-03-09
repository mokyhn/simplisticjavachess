public class NoMoveException extends Exception {
    String err;	

    public NoMoveException() {         
    }

    
    public NoMoveException(String e) {
            err = e;
	}

}
