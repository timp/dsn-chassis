/**
 * 
 */
package spike.users;

import java.util.Set;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.memory.InMemoryDaoImpl;

/**
 * @author aliman
 *
 */
public class ExtendedInMemoryDaoImpl 
	extends InMemoryDaoImpl 
	implements ExtendedUserDetailsService {


	
	
	/**
	 * 
	 */
	public ExtendedInMemoryDaoImpl() {}

	
	
	
	/* (non-Javadoc)
	 * @see spike.users.ExtendedUserDetailsService#loadUsersByAuthority(java.lang.String)
	 */
	public Set<UserDetails> loadUsersByAuthority(String authority) {
		return null;
	}

	
	
	
}
