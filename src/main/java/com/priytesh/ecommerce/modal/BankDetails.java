package com.priytesh.ecommerce.modal;


import lombok.Data;

@Data //create getter, setter, allArgs, and no args
public class BankDetails {
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}
