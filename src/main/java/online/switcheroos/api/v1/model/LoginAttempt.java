package online.switcheroos.accounts.api.v1.model;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAttempt {

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Type(type = "online.switcheroos.accounts.core.PostgreSQLInetType")
    @Column(name = "ip_address")
    private Inet ipAddress;

    private String country;

    private String userAgent;

    private boolean successful;
}
