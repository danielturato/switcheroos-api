package online.switcheroos.core;

import com.vladmihalcea.hibernate.type.ImmutableType;
import com.vladmihalcea.hibernate.type.util.ReflectionUtils;
import online.switcheroos.api.v1.model.Inet;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLInetType extends ImmutableType<Inet> {

    public PostgreSQLInetType() {
        super(Inet.class);
    }

    @Override
    protected Inet get(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String ip = rs.getString(names[0]);
        return (ip != null) ? new Inet(ip) : null;
    }

    @Override
    protected void set(PreparedStatement st, Inet value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            Object holder = ReflectionUtils.newInstance(
                    "org.postgresql.util.PGobject"
            );
            ReflectionUtils.invokeSetter(holder, "type", "inet");
            ReflectionUtils.invokeSetter(holder, "value", value.getAddress());

            st.setObject(index, holder);
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[] {
            Types.OTHER
        };
    }
}
