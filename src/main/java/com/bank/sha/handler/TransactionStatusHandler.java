package com.bank.sha.handler;

public class TransactionStatusHandler {

    public static String transactionStatusHandling(String transactionStatus, String fraudStatus) {
        String status = null;
        // TransactionStatus handling logic
        if (transactionStatus.equals("capture")){
            if (fraudStatus.equals("accept")){
                // TODO set transaction status on your database to 'success'
                status = "success";
            }
        } else if (transactionStatus.equals("settlement")){
            // TODO set transaction status on your database to 'success'
            status = "success";
        } else if (transactionStatus.equals("cancel") ||
                transactionStatus.equals("deny") ||
                transactionStatus.equals("expire")){
            // TODO set transaction status on your database to 'failure'
            status = "failure";
        } else if (transactionStatus.equals("pending")){
            // TODO set transaction status on your database to 'pending' / waiting payment
            status = "pending";
        }
        return status;
    }
}
