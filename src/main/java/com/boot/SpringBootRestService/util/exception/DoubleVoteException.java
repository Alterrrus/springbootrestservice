package com.boot.SpringBootRestService.util.exception;

public class DoubleVoteException extends ApplicationException{
    public static final String DOUBLE_VOTE_EXCEPTION = "exception.common.doubleVote";

    //  http://stackoverflow.com/a/22358422/548473
    public DoubleVoteException(String arg) {
        super(ErrorType.DATA_ERROR, DOUBLE_VOTE_EXCEPTION, arg);
    }
}
