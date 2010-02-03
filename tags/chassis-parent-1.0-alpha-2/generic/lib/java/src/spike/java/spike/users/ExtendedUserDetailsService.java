/**
 * 
 */
package spike.users;

import java.util.Set;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;

/**
 * @author aliman
 *
 */
public interface ExtendedUserDetailsService extends UserDetailsService {

	
	public Set<UserDetails> loadUsersByAuthority(String authority);
	
	
}
