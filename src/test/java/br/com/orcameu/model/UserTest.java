package br.com.orcameu.model;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserTest {
	
	@Test
	public void shouldFindAnyUser() {
		
		PanacheMock.mock(User.class);
		User user = new User();
		Optional<PanacheEntityBase> userOptional = Optional.of(user);
		Mockito.when(User.findByIdOptional("renanfr1047@gmail.com")).thenReturn(userOptional);
		Assertions.assertSame(user, User.findByIdOptional("renanfr1047@gmail.com").get());
		
	}

}
