package commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseTest {

	protected final Log log;

	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
}
