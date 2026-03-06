package com.tapsyrys.tapsyrys.Interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface Authorizable extends UserDetails {
    String getPhone();
}
