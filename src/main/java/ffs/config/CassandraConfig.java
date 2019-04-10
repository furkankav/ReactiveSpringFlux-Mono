package ffs.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = "ffs")
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
	private static final String USERNAME = "cassandra";
	private static final String PASSWORD = "cassandra";
	private static final String NODES = "127.0.0.1"; // comma seperated nodes

	@Value("${cassandra.keyspace}")
	private String keySpace;

	@Bean
	@Override
	public CassandraCqlClusterFactoryBean cluster() {
		CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
		bean.setJmxReportingEnabled(false);
		bean.setKeyspaceCreations(getKeyspaceCreations());
		bean.setContactPoints(NODES);
		bean.setUsername(USERNAME);
		bean.setPassword(PASSWORD);
		return bean;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected String getKeyspaceName() {
		return keySpace;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "ffs" };
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(keySpace).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication());
	}

}
