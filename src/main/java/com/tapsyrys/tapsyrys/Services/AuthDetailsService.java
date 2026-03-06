package com.tapsyrys.tapsyrys.Services;

import com.tapsyrys.tapsyrys.Repositories.ShopRepository;
import com.tapsyrys.tapsyrys.Repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {
    private final SupplierRepository supplierRepository;
    private final ShopRepository shopRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        var shop = shopRepository.findByPhone(phone);
        if (shop.isPresent()) return shop.get();

        return supplierRepository.findSupplierByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }


}

