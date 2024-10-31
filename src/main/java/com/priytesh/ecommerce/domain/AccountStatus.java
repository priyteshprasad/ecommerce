package com.priytesh.ecommerce.domain;

public enum AccountStatus {

    PENDING_VERIFICATION, //
    ACTIVE,                 //Account is active
    SUSPENDED,              //Account temporarily suspended, due to violation
    DEACTIVATED,            //Account is deactivated, user may have chosen to deactivate it
    BANNED,                 //Account is permanently banned due to severe violations
    CLOSED                  //Account is permanently closed, possibly at the user request
}
