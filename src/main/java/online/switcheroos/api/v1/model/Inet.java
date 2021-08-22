package online.switcheroos.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inet {

    @Column(name = "ip_address")
    private String address;

    public InetAddress toInetAddress() {
        try {
            String host = address.replaceAll("\\/.*$", "");

            return Inet4Address.getByName(host);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
