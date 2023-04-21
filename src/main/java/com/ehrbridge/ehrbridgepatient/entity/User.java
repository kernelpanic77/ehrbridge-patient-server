package com.ehrbridge.ehrbridgepatient.entity;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.ansi.AnsiOutput.Enabled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.validation.OverridesAttribute.List;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User implements UserDetails{
    private String firstName;
    
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    private String gender; 

    private String address;

    @Column(name="phone", unique=true)
    private String phoneString;

    @Id
    @Column(name="ehrbID", nullable=false, unique=true)
    private String ehrbID;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // returns a list of user roles
        return null;
       
    }

    @Override
    public String getUsername() {
       // using emailAddress for username for now
       return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // will be used to check for hospital partnership duration
        return true;
        //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
        //throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
        //throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
    }

    @Override
    public boolean isEnabled() {
        // TODO: use is enabled for the verification step
        return true;
    }

}
